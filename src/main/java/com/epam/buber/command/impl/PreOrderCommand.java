package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.core.util.JsonUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

public class PreOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Enumeration<String> e = request.getParameterNames();




        session.setAttribute(RequestParameterName.START_POINT, request.getParameter("comment"));
        session.setAttribute("comment", request.getParameter("comment"));
        session.setAttribute("comment", request.getParameter("comment"));


        return new Router(PagePath.ORDER, Router.RouterType.FORWARD);
    }
}
