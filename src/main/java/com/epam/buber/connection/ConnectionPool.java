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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger();
    private static Lock locker = new ReentrantLock();
    private static ConnectionPool poolInstance;
    private static final int POOL_SIZE = 4;
    private static Properties PROPERTIES = new Properties();
    private static String PROPERTIES_FILE_NAME = "application.properties";
    private static String URL_PROPERTY = "url";
    private BlockingQueue<ProxyConnection> queue = new LinkedBlockingQueue<>(POOL_SIZE);
    private BlockingQueue<ProxyConnection> usedQueue = new LinkedBlockingQueue<>(POOL_SIZE);

    static {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            // Class.forName("com.mysql.cj.jdbc.Driver");
            try (InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
                PROPERTIES.load(inputStream);
            } catch (IOException e) {
                throw new ExceptionInInitializerError(e);
            }
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {
            for (int i = 0; i < POOL_SIZE; i++) {
                queue.add(createConnection());
            }
//            checkPool();
//        destrConnect();
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
            //logger.log(Level.ERROR, "Get connection exception: {}", e.getErrorCode());
            throw new ExceptionInInitializerError(e);
        }
        return proxyConnection;
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
        logger.log(Level.INFO,"POOL in work(get)! free con: {}, used con: {}", queue.size(), usedQueue.size());
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            if (connection instanceof ProxyConnection) {
                ProxyConnection proxy = (ProxyConnection) connection;
                usedQueue.remove(proxy);
                queue.put(proxy);
            } else {
                //todo
            }
            logger.log(Level.INFO,"POOL in work(release)! free con: {}, used con: {}", queue.size(), usedQueue.size());
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Return connection exception: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void checkPool(){
            Timer poolTimer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if (queue.size() + usedQueue.size() == POOL_SIZE){
                        logger.log(Level.INFO,"POOL OK! free con: {}, used con: {}", queue.size(), usedQueue.size());
                    } else {
                        logger.log(Level.INFO,"POOL NOT OK! free con: {}, used con: {}", queue.size(), usedQueue.size());
                        for (int i = 0; i < POOL_SIZE - queue.size(); i++) {
                            queue.add(createConnection());
                        }
                    }
                }
            };
            poolTimer.schedule(task, 0, 10000);
    }

//    private void destrConnect(){
//        Timer destroyer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    ProxyConnection connection = queue.take();
//                    connection.reallyClose();
//                    logger.log(Level.INFO,"DESTROYER in work! free con: {}, used con: {}", queue.size(), usedQueue.size());
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };
//        destroyer.schedule(task, 5000, 20000);
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
