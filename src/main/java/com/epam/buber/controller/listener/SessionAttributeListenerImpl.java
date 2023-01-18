package com.epam.buber.controller.listener;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionAttributeListenerImpl implements HttpSessionAttributeListener {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
        logger.log(Level.INFO, "++++***Attribute \"user_login\" added:{}", sbe.getSession().getAttribute("user_login"));
        logger.log(Level.INFO, "++++***Attribute \"current_page\" added:{}", sbe.getSession().getAttribute("current_page"));
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
        logger.log(Level.INFO, "*******Attribute \"user_name\" Replaced:{}", sbe.getSession().getAttribute("user_name"));
        logger.log(Level.INFO, "*******Attribute \"current_page\" Replaced:{}", sbe.getSession().getAttribute("current_page"));
    }
}
