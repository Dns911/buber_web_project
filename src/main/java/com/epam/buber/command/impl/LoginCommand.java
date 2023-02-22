package com.epam.buber.command.impl;

import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.User;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.UserService;
import com.epam.buber.service.impl.UserServiceImpl;
import com.epam.buber.command.Command;
import com.epam.buber.controller.info.PagePath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(RequestParameterName.LOGIN);
        String password = request.getParameter(RequestParameterName.PASSWORD);
        String userRole = request.getParameter(RequestParameterName.USER_ROLE);
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page;
        Router router;
        try {
            if (userService.authenticate(login, password, userRole)) {
                User user = userService.getUserFromBD(login, userRole);
                request.setAttribute(RequestParameterName.USER, login);
//                session.setAttribute("main_msg", login);
                session.setAttribute("user_login", login);
                session.setAttribute("user_role", userRole);
                if (userRole.equals(UserRole.DRIVER.getStringRole())){
                    Driver driver = (Driver) user;
                    session.setAttribute(SessionAttrName.DRIVER_SYSTEM_STATUS, driver.getStatus().getStringStatus());
                    session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_REST);
                    page = PagePath.DRIVER_PAGE;
                }
                else {
                    page = PagePath.CLIENT_PAGE;
                }
            } else {
                request.setAttribute(RequestParameterName.LOGIN_MSG, "Incorrect login or password");
                page = PagePath.LOGIN;
            }
            router = new Router(page, Router.RouterType.FORWARD);
            session.setAttribute(SessionAttrName.CURRENT_PAGE, page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
