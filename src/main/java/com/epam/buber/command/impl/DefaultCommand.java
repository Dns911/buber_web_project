package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.PagePath;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PagePath.INDEX;
    }
}
