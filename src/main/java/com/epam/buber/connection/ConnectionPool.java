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
    private static final Logger logger = LogManager.getLogger();
    private static final Lock locker = new ReentrantLock();
    private static final int POOL_SIZE = 4;
    private static final Properties PROPERTIES = new Properties();
    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private static final String URL_PROPERTY = "url";
    private static ConnectionPool poolInstance;
    private BlockingQueue<Connection> queue = new LinkedBlockingQueue<>(POOL_SIZE);
    private BlockingQueue<Connection> usedQueue = new LinkedBlockingQueue<>(POOL_SIZE);

    static {
        try (InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            PROPERTIES.load(inputStream);
        } catch (SQLException | IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            queue.add(createConnection());
        }
//            checkPool();
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

    private ProxyConnection createConnection() {
        ProxyConnection proxyConnection;
        String url = PROPERTIES.getProperty(URL_PROPERTY);
        try {
            proxyConnection = new ProxyConnection(DriverManager.getConnection(url, PROPERTIES));
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
        return proxyConnection;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = queue.take();
            usedQueue.put(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Get connection exception: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (connection instanceof ProxyConnection) {
                ProxyConnection proxy = (ProxyConnection) connection;
                usedQueue.remove(proxy);
                queue.put(proxy);
            } else {
                logger.log(Level.WARN, "Connection is NOT ProxyConnection");
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Return connection exception: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

//    private void checkPool() {
//        Timer poolTimer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                if (queue.size() + usedQueue.size() == POOL_SIZE) {
//                    logger.log(Level.INFO, "POOL OK! free con: {}, used con: {}", queue.size(), usedQueue.size());
//                } else {
//                    logger.log(Level.INFO, "POOL NOT OK! free con: {}, used con: {}", queue.size(), usedQueue.size());
//                    for (int i = 0; i < POOL_SIZE - queue.size(); i++) {
//                        queue.add(createConnection());
//                    }
//                }
//            }
//        };
//        poolTimer.schedule(task, 0, 10000);
//    }

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
