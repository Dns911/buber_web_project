package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class UserPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Router router;
        if (session.getAttribute(SessionAttrName.USER_LOGIN) == null || session.getAttribute(SessionAttrName.USER_LOGIN).equals(AttrValue.GUEST_MSG)) {
            router = new Router(PagePath.LOGIN, Router.RouterType.FORWARD);
        } else if (session.getAttribute(SessionAttrName.USER_ROLE).equals(UserRole.ADMIN.toString())) {
            router = new Router(PagePath.ADMIN_PAGE, Router.RouterType.FORWARD);
        } else if (session.getAttribute(SessionAttrName.USER_ROLE).equals(UserRole.DRIVER.toString())) {
            router = new Router(PagePath.DRIVER_PAGE, Router.RouterType.FORWARD);
        } else {
            router = new Router(PagePath.CLIENT_PAGE, Router.RouterType.FORWARD);
        }
        return router;
    }
}
