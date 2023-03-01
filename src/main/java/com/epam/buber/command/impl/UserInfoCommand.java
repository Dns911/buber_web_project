package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.Client;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.User;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.UserService;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserInfoCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.log(Level.INFO, "user info command");
        UserService userService = UserServiceImpl.getInstance();
        User user;
        HttpSession session = request.getSession();
        Router router;
        String page;
        String login = session.getAttribute(SessionAttrName.USER_LOGIN).toString();
        logger.log(Level.INFO, "login " + login);
        UserRole role = UserRole.define((String) session.getAttribute(SessionAttrName.USER_ROLE));
        logger.log(Level.INFO, "role " + role);
        try {
            user = userService.getUserFromBD(login, role);
            UserRole currentRole = user.getRole();
            session.setAttribute(SessionAttrName.USER_ROLE, currentRole.getStringRole());
            request.setAttribute(RequestParameterName.ID, user.getId());
            request.setAttribute(RequestParameterName.PHONE_NUM, user.getPhoneNum());
            request.setAttribute(RequestParameterName.USER_NAME, user.getName());
            request.setAttribute(RequestParameterName.USER_LASTNAME, user.getLastName());
            request.setAttribute(RequestParameterName.DATE_REGISTRY, user.getRegistrationDate());
            request.setAttribute(RequestParameterName.RATE, user.getRate());

            if (currentRole.equals(UserRole.DRIVER)) {
                request.setAttribute(RequestParameterName.DRIVER_LIC_VALID, ((Driver) user).getLicenceValidDate());
                request.setAttribute(RequestParameterName.INCOME_SUM, ((Driver) user).getIncomeSum());
                request.setAttribute(RequestParameterName.STATUS, ((Driver) user).getStatus().getStringStatus());
                page = PagePath.DRIVER_PAGE;
            } else if (currentRole.equals(UserRole.CLIENT)) {
                request.setAttribute(RequestParameterName.PAYMENT_SUM, ((Client) user).getPaymentSum());
                logger.log(Level.INFO, "client");
                page = PagePath.CLIENT_PAGE;
            } else {
                page = PagePath.ADMIN_PAGE;
            }
            router = new Router(page, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
