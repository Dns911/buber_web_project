package com.epam.buber.connection;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger();
    private static Lock locker = new ReentrantLock();
    private static ConnectionPool poolInstance;
    private static final int POOL_SIZE = 8;
    private BlockingQueue<ProxyConnection> queue = new LinkedBlockingQueue<>(POOL_SIZE);
    private BlockingQueue<ProxyConnection> usedQueue = new LinkedBlockingQueue<>(POOL_SIZE);

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            String url = properties.getProperty("url");
            for (int i = 0; i < POOL_SIZE; i++) {
                try {
                    queue.add(new ProxyConnection(DriverManager.getConnection(url, properties)));
                } catch (SQLException e) {
                    //logger.log(Level.ERROR, "Get connection exception: {}", e.getErrorCode());
                    throw new ExceptionInInitializerError(e);
                }
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }


    }

    public static ConnectionPool getInstance() {
        if (poolInstance == null) {
            try {
                locker.lock();
                if (poolInstance == null) {
                    poolInstance = new ConnectionPool();
                }
            } finally {
                locker.unlock();
            }
        }
        return poolInstance;
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = queue.take();
            usedQueue.put(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Get connection exception: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void returnConnection(Connection connection) {
        try {
            if (connection instanceof ProxyConnection) {
                ProxyConnection proxy = (ProxyConnection) connection;
                usedQueue.remove(proxy);
                queue.put(proxy);
            } else {
                //todo
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Return connection exception: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void deregisterDriver() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Deregistry driver exception: {}", e.getErrorCode());
            }
        });
    }

    public void destroyPool() {
        this.queue.forEach(connection -> {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Close connection exception: {}", e.getErrorCode());
            }
        });

    }
}
