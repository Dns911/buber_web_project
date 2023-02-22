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
        String userRole = request.getParameter(RequestParameterName.USER_ROLE);
        logger.log(Level.INFO,"role: " + userRole);
//        request.getSession().setAttribute(RequestParameterName.REGISTR_MSG,"");
        Router router;
        if (userRole.equals(UserRole.CLIENT.getStringRole())){
            router = new Router(PagePath.REGISTRATION_CLIENT, Router.RouterType.FORWARD);
        } else {
            router = new Router(PagePath.REGISTRATION_DRIVER, Router.RouterType.FORWARD);
        }
        return router;
    }
}
