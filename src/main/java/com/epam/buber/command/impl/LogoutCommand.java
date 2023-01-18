package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.PagePath;
import com.epam.buber.controller.RequestParameterName;
import com.epam.buber.controller.Router;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().setAttribute(RequestParameterName.LOGOUT_MSG,"User logout from system!");
        request.getSession().invalidate();
        Router router = new Router(PagePath.INDEX, Router.RouterType.FORWARD);
        return router;
    }
}
