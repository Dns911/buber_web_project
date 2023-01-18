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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void init() {
        ConnectionPool.getInstance();
        logger.log(Level.INFO, "+++++++++ servlet Init:" + this.getServletInfo());
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
        logger.log(Level.INFO, "////////1");
        resp.setContentType("text/html");
        logger.log(Level.INFO, "////////2");
        String commandStr = req.getParameter(RequestParameterName.COMMAND);
        logger.log(Level.INFO, "////////3");
        Command command = CommandType.define(commandStr.toUpperCase());
        try {
            logger.log(Level.INFO, "////////4");
            Router router = command.execute(req);
            logger.log(Level.INFO, "////////" + router.getType().toString());
            switch (router.getType()) {
                case FORWARD:
                    req.getRequestDispatcher(router.getPage()).forward(req, resp);
                    break;
                case REDIRECT:
                    resp.sendRedirect(router.getPage());
                    break;
                default: {
                    resp.sendRedirect(router.getPage());
                    logger.log(Level.ERROR, "-------$$$$$-------Router error, default \"Redirect\"!");
                    break;
                }
            }

        } catch (CommandException e) {
            throw new ServletException(e); // 1
            //resp.sendError(500); // 2
//           req.setAttribute("error_msg", e.getCause()); //3
//           req.getRequestDispatcher(PagePath.ERROR_500).forward(req, resp); //3
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().deregisterDriver();
        ConnectionPool.getInstance().destroyPool();
        logger.log(Level.INFO, "+++++++++ servlet destroyed:" + this.getServletName());
    }
}