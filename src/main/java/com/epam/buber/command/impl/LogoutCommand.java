package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.Router;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class LogoutCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) {
        long time = System.currentTimeMillis();
        logger.log(Level.INFO,"{} --User logout from system!", new Date(time));
        request.getSession().invalidate();
        Router router = new Router(PagePath.MAIN, Router.RouterType.FORWARD);
        return router;
    }
}
