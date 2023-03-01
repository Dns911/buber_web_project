package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.Client;
import com.epam.buber.entity.DriverShift;
import com.epam.buber.entity.ListDriverShift;
import com.epam.buber.entity.Order;
import com.epam.buber.entity.parameter.CarClass;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.CommonService;
import com.epam.buber.service.EmailService;
import com.epam.buber.service.impl.CommonServiceImpl;
import com.epam.buber.service.impl.EmailServiceImpl;
import com.epam.buber.service.impl.OrderServiceImpl;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderStartCommand implements Command {
    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        OrderServiceImpl orderService = OrderServiceImpl.getInstance();
        ListDriverShift listShift = ListDriverShift.getInstance();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        EmailServiceImpl emailService = EmailServiceImpl.getInstance();
        String clientLogin = session.getAttribute(SessionAttrName.USER_LOGIN).toString();
        Order order = new Order(Order.DEFAULT_ID);
        logger.log(Level.INFO, "class: " + request.getParameter(RequestParameterName.CLASS_AUTO));
        CarClass carClass = CarClass.define(request.getParameter(RequestParameterName.CLASS_AUTO));
        String page;
        try {
            if (listShift.getCountFreeDriver(carClass) > 0) {
                Client client = (Client) userService.getUserFromBD(clientLogin, UserRole.CLIENT);
                DriverShift driverShift = listShift.getQueueShift(carClass);
                order.setClient(client);
                order.setDriverShift(driverShift);
                order.setStartPoint(request.getParameter(RequestParameterName.START_POINT));
                order.setFinishPoint(request.getParameter(RequestParameterName.FINISH_POINT));
                order.setDistance(Double.valueOf(request.getParameter(RequestParameterName.DISTANCE)));
                order.setCost(Double.valueOf(request.getParameter(RequestParameterName.COST)));
                orderService.startOrder(order);
                driverShift.setCurrentOrder(order);
                logger.log(Level.INFO, "or1 id " + order.getIdOrder());
                if (order.getIdOrder() != Order.DEFAULT_ID) {
                    logger.log(Level.INFO, "or2 id " + order.getIdOrder());
                    emailService.sendEmail(clientLogin, EmailService.EmailType.ORDER_START_CLIENT_8,
                            order.getClient().getName(),
                            String.valueOf(order.getIdOrder()),
                            order.getStartPoint(),
                            order.getDriverShift().getCar().getModel(),
                            order.getDriverShift().getCar().getColor(),
                            order.getDriverShift().getCar().getId(),
                            order.getDriverShift().getDriver().getName(),
                            order.getDriverShift().getDriver().getPhoneNum());
                    String emailDriver = driverShift.getDriver().getEmail();
                    emailService.sendEmail(emailDriver, EmailService.EmailType.ORDER_START_DRIVER_6,
                            order.getDriverShift().getDriver().getName(),
                            String.valueOf(order.getIdOrder()),
                            order.getStartPoint(),
                            order.getStartTime().toString(),
                            order.getClient().getName(),
                            order.getClient().getPhoneNum());
                }
                orderService.orderInfo(request, order, UserRole.CLIENT);
                page = PagePath.ORDER_INFO_CLIENT;
            } else {
                request.setAttribute(RequestParameterName.PREORDER_MSG, AttrValue.PREORDER_ERR_MSG);
                page = PagePath.MAIN;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(page, Router.RouterType.FORWARD);
    }
}
