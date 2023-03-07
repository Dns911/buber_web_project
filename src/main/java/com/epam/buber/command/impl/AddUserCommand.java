package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.Router;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.CommonService;
import com.epam.buber.service.EmailService;
import com.epam.buber.service.impl.CommonServiceImpl;
import com.epam.buber.service.impl.EmailServiceImpl;
import com.epam.buber.service.UserService;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public class AddUserCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = UserServiceImpl.getInstance();
        EmailService emailService = EmailServiceImpl.getInstance();
        CommonService commonService = CommonServiceImpl.getInstance();
        UserRole role = UserRole.define(request.getParameter(RequestParameterName.USER_ROLE));
        Map<String, String> map = commonService.createMapFromRequest(request,
                RequestParameterName.USER_ROLE,
                RequestParameterName.EMAIL,
                RequestParameterName.PASSWORD,
                RequestParameterName.PASSWORD_CHECK,
                RequestParameterName.PHONE_NUM,
                RequestParameterName.USER_NAME,
                RequestParameterName.USER_LASTNAME);
        if (role.equals(UserRole.DRIVER)) {
            map.put(RequestParameterName.DRIVER_LIC_NUMBER, request.getParameter(RequestParameterName.DRIVER_LIC_NUMBER));
            map.put(RequestParameterName.DRIVER_LIC_VALID, request.getParameter(RequestParameterName.DRIVER_LIC_VALID));
        }
        String page;
        Router router;
        try {
            if (userService.insertUser(map)) {
                page = PagePath.REGISTRATION_SUCCESS;
                EmailService.EmailType type = role.equals(UserRole.DRIVER) ? EmailService.EmailType.WELCOME_DRIVER_1 : EmailService.EmailType.WELCOME_CLIENT_1;
                emailService.sendEmail(map.get(RequestParameterName.EMAIL), type, map.get(RequestParameterName.USER_NAME));
                router = new Router(page, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(RequestParameterName.REGISTR_MSG, AttrValue.REGISTR_MSG);
                commonService.setRequestValue(request, map,
                        RequestParameterName.EMAIL,
                        RequestParameterName.PHONE_NUM,
                        RequestParameterName.USER_NAME,
                        RequestParameterName.USER_LASTNAME,
                        RequestParameterName.PASSWORD,
                        RequestParameterName.PASSWORD_CHECK);
                if (role.equals(UserRole.DRIVER)) {
                    commonService.setRequestValue(request, map,
                            RequestParameterName.DRIVER_LIC_NUMBER,
                            RequestParameterName.DRIVER_LIC_VALID);
                    page = PagePath.REGISTRATION_DRIVER;
                } else {
                    page = PagePath.REGISTRATION_CLIENT;
                }
                router = new Router(page, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
