package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.EmailService;
import com.epam.buber.service.UserService;
import com.epam.buber.service.impl.EmailServiceImpl;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangePassCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        System.out.println("login " + session.getAttribute(SessionAttrName.USER_LOGIN).toString());
        String login = session.getAttribute(SessionAttrName.USER_LOGIN).toString();
        String roleSrt = session.getAttribute(SessionAttrName.USER_ROLE).toString();
        String newPass = request.getParameter(RequestParameterName.PASSWORD);
        String newPassCheck = request.getParameter(RequestParameterName.PASSWORD_CHECK);
        UserRole role = UserRole.define(roleSrt);
        UserService userService = UserServiceImpl.getInstance();
        EmailService emailService = EmailServiceImpl.getInstance();
        Router router;
        String page;
        try {
            String result;
            if (login.equals(AttrValue.GUEST_MSG)){
                result = userService.restorePassword(login, role);
                if (!result.equals(AttrValue.NEW_PASS_ERROR)) {
                    emailService.sendEmail(login, EmailService.EmailType.RESTORE_PASSWORD_1, result);
                    page = PagePath.INDEX;
                } else {
                    request.setAttribute(RequestParameterName.LOGIN_ERR, result);
                    page = PagePath.RESTORE_PASS;
                }
            } else {
                if (newPass.equals(newPassCheck)){
                    if (userService.setNewPassword(login, role, newPass)) {
                        emailService.sendEmail(login, EmailService.EmailType.RESTORE_PASSWORD_1, newPass);
                        page = PagePath.MAIN;
                    } else {
                        request.setAttribute(RequestParameterName.PASSWORD_ERR, AttrValue.VALID_PASS_ERROR);
                        page = PagePath.CHANGE_PASS;
                    }
                } else {
                    request.setAttribute(RequestParameterName.PASSWORD_ERR, AttrValue.SAME_PASS_ERROR);
                    page = PagePath.CHANGE_PASS;
                }
            }
            router = new Router(page, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
