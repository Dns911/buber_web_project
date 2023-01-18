package com.epam.buber.controller.listener;

import com.epam.buber.connection.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
       // ConnectionPool.getInstance();
       // logger.log(Level.INFO, "+++++++++ context Init:" + sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //logger.log(Level.INFO, "+++++++++ context destroyed:" + sce.getServletContext().getContextPath());
        //ConnectionPool.getInstance().deregisterDriver();
        //ConnectionPool.getInstance().destroyPool();
    }
}
