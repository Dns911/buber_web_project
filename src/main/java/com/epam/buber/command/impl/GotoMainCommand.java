package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class GotoMainCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionAttrName.USER_LOGIN) == null) {
            session.setAttribute(SessionAttrName.USER_LOGIN, AttrValue.GUEST_MSG);
            session.setAttribute(SessionAttrName.USER_ROLE, AttrValue.GUEST_ROLE);
        }
        return new Router(PagePath.MAIN, Router.RouterType.FORWARD);
    }
}
