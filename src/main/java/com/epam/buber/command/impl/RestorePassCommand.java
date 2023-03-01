package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.CommonService;
import com.epam.buber.service.EmailService;
import com.epam.buber.service.UserService;
import com.epam.buber.service.impl.CommonServiceImpl;
import com.epam.buber.service.impl.EmailServiceImpl;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

public class RestorePassCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(RequestParameterName.LOGIN);
        String roleSrt = request.getParameter(RequestParameterName.USER_ROLE);
        UserRole role = UserRole.define(roleSrt);
        UserService userService = UserServiceImpl.getInstance();
        EmailService emailService = EmailServiceImpl.getInstance();
        Router router;
        String page;
        try {
            String result = userService.getNewPassword(login, role);
            if (!result.equals(UserServiceImpl.NEW_PASS_ERROR)) {
                emailService.sendEmail(login, EmailService.EmailType.RESTORE_PASSWORD_1, result);
                page = PagePath.INDEX;
            } else {
                request.setAttribute(RequestParameterName.LOGIN + "_err", result);
                page = PagePath.RESTORE_PASS;
            }
            router = new Router(page, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
