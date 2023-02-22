package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.Router;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.CommonService;
import com.epam.buber.service.EmailService;
import com.epam.buber.service.impl.CommonServiceImpl;
import com.epam.buber.service.impl.EmailServiceImpl;
import com.epam.buber.service.UserService;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class AddUserCommand implements Command {
    private static Logger logger = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.log(Level.INFO, "start exec req name: " + request.toString());
        UserService userService = UserServiceImpl.getInstance();
        EmailService emailService = EmailServiceImpl.getInstance();
        CommonService commonService = CommonServiceImpl.getInstance();
        UserRole role = UserRole.define(request.getParameter(RequestParameterName.USER_ROLE));
        HashMap<String, String> map = commonService.createMapFromRequest(request,
                RequestParameterName.USER_ROLE,
                RequestParameterName.EMAIL,
                RequestParameterName.PASSWORD,
                RequestParameterName.PASSWORD_CHECK,
                RequestParameterName.PHONE_NUM,
                RequestParameterName.USER_NAME,
                RequestParameterName.USER_LASTNAME);

        if (role.equals(UserRole.DRIVER)){
            map.put(RequestParameterName.DRIVER_LIC_NUMBER, request.getParameter(RequestParameterName.DRIVER_LIC_NUMBER));
            map.put(RequestParameterName.DRIVER_LIC_VALID, request.getParameter(RequestParameterName.DRIVER_LIC_VALID));
        }
        String page;
        Router router;
        try {
            if (userService.registration(map)){
                page = PagePath.REGISTRATION_SUCCESS;
                EmailService.EmailType type = role.equals(UserRole.DRIVER) ? EmailService.EmailType.WELCOME_DRIVER : EmailService.EmailType.WELCOME_CLIENT;
                emailService.sendEmail(map.get(RequestParameterName.EMAIL), map.get(RequestParameterName.USER_NAME), type);
                logger.log(Level.INFO, "Message has sent to email: {}", map.get(RequestParameterName.EMAIL));
                router = new Router(page, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(RequestParameterName.REGISTR_MSG, "Введите одинаковые пароли или " +
                        "заполните корректные параметры в пустых ячейках");
//                request.setAttribute(RequestParameterName.REGISTR_MSG, "User couldn't be add!");
                commonService.setRequestValue(request, map,
                        RequestParameterName.EMAIL,
                        RequestParameterName.PHONE_NUM,
                        RequestParameterName.USER_NAME,
                        RequestParameterName.USER_LASTNAME,
                        RequestParameterName.PASSWORD,
                        RequestParameterName.PASSWORD_CHECK);
                if (role.equals(UserRole.DRIVER)){
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
