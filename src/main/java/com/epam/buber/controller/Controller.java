package com.epam.buber.controller;

import com.epam.buber.command.Command;
import com.epam.buber.command.CommandType;
import com.epam.buber.connection.ConnectionPool;
import com.epam.buber.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/controller")
public class Controller extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            process(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            process(req, resp);
        } catch (ServletException e) {
             e.getCause();
        }
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
        resp.setContentType("text/html");
        String commandStr = req.getParameter(RequestParameterName.COMMAND);
        Command command = CommandType.define(commandStr.toUpperCase());
        String page;

        try {
            page = command.execute(req);
           // req.getRequestDispatcher(page).forward(req, resp);
            resp.sendRedirect(page);
        } catch (CommandException e) {
            // throw new ServletException(e); // 1
            // resp.sendError(500); // 2

            req.setAttribute("error_msg", e.getCause()); //3
            req.getRequestDispatcher(PagePath.ERROR_500).forward(req, resp); //3

        }


    }

    public void destroy() {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.destroyPool();
        pool.deregisterDriver();
    }
}