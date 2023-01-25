package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.Router;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class AddUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String email = request.getParameter(RequestParameterName.EMAIL);
        String password = request.getParameter(RequestParameterName.PASSWORD);
        String phone = request.getParameter(RequestParameterName.PHONE_NUM);
        String name = request.getParameter(RequestParameterName.USER_NAME);
        String lastname = request.getParameter(RequestParameterName.USER_LASTNAME);
        String page;
        Router router;
        UserServiceImpl userService = UserServiceImpl.getInstance();

        try {
            if (userService.registration(email, password, phone, name, lastname)){
                page = PagePath.REGISTRATION_SUCCESS;
            } else {
                request.getSession().setAttribute(RequestParameterName.REGISTR_MSG, "User couldn't be add!");
//                request.setAttribute(RequestParameterName.REGISTR_MSG, "User couldn't be add!");
                page = PagePath.REGISTRATION_USER;
            }
            router = new Router(page, Router.RouterType.REDIRECT);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        return router;
    }
}
