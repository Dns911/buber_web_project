package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class RestorePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(PagePath.RESTORE_PASS, Router.RouterType.FORWARD);
    }
}
