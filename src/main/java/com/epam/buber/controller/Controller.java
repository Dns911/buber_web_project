package com.epam.buber.controller;

import com.epam.buber.command.Command;
import com.epam.buber.command.CommandType;
import com.epam.buber.connection.ConnectionPool;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {

    @Override
    public void init() {
        ConnectionPool.getInstance();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String commandStr = req.getParameter(RequestParameterName.COMMAND);
        Command command = CommandType.define(commandStr.toUpperCase());
        try {
            Router router = command.execute(req);
            switch (router.getType()) {
                case FORWARD:
                    req.getRequestDispatcher(router.getPage()).forward(req, resp);
                    break;
                case REDIRECT:
                    resp.sendRedirect(router.getPage());
                    break;
                default: {
                    resp.sendRedirect(router.getPage());
                    break;
                }
            }
        } catch (CommandException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().deregisterDriver();
        ConnectionPool.getInstance().destroyPool();
    }
}