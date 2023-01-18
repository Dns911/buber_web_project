package com.epam.buber.command;

import com.epam.buber.controller.Router;
import com.epam.buber.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
    default void refresh(){}
}
