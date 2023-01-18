package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.PagePath;
import com.epam.buber.controller.RequestParameterName;
import com.epam.buber.controller.Router;
import com.epam.buber.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationPageCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String userType = request.getParameter("usertype");
        Router router;
        if (userType.equals(RequestParameterName.REGULAR_USER)){
            router = new Router(PagePath.REGISTRATION_USER, Router.RouterType.FORWARD);
        } else {
            router = new Router(PagePath.REGISTRATION_DRIVER, Router.RouterType.FORWARD);
        }
        return router;
    }
}
