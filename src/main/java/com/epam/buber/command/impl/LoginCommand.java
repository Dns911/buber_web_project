package com.epam.buber.command.impl;

import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.ListDriverShift;
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
        UserRole role = UserRole.define(request.getParameter(RequestParameterName.USER_ROLE));
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        String page;
        Router router;
        try {
            if (userService.authenticate(login, password, role)) {
                User user = userService.getUserFromBD(login, role);
                request.setAttribute(RequestParameterName.USER, login);
                session.setAttribute(SessionAttrName.USER_LOGIN, login);
                session.setAttribute(SessionAttrName.USER_ROLE, role.getStringRole());
                session.setAttribute(SessionAttrName.USER_ID, user.getId());
                if (role.equals(UserRole.DRIVER)) {
                    Driver driver = (Driver) user;
                    session.setAttribute(SessionAttrName.DRIVER_SYSTEM_STATUS, driver.getStatus().getStringStatus());
                    ListDriverShift listDriverShift = ListDriverShift.getInstance();
                    if (listDriverShift.checkFreeQueue(driver.getId())) {
                        session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_WAIT_ORDER);
                    } else if (listDriverShift.checkOrderedListByDriverId(driver.getId())) {
                        session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_IN_ORDER);
                    } else {
                        session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_REST);
                    }
                    page = PagePath.DRIVER_PAGE;
                } else {
                    page = PagePath.CLIENT_PAGE;
                }
            } else {
                request.setAttribute(RequestParameterName.LOGIN_MSG, AttrValue.LOGIN_MSG);
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
