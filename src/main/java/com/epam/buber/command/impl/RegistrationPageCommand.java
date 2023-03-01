package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.Router;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrationPageCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserRole role = UserRole.define(request.getParameter(RequestParameterName.USER_ROLE));
        logger.log(Level.INFO, "role: " + role);
        Router router;
        if (role.equals(UserRole.CLIENT)) {
            router = new Router(PagePath.REGISTRATION_CLIENT, Router.RouterType.FORWARD);
        } else {
            router = new Router(PagePath.REGISTRATION_DRIVER, Router.RouterType.FORWARD);
        }
        return router;
    }
}
