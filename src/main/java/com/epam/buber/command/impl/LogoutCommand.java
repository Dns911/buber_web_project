package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.PagePath;
import com.epam.buber.controller.RequestParameterName;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(RequestParameterName.LOGOUT_MSG,"User logout from system!");
        request.getSession().invalidate();
        return PagePath.INDEX;
    }
}
