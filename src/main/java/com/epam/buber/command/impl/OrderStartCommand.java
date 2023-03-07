package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.Client;
import com.epam.buber.entity.ShiftDriver;
import com.epam.buber.entity.ListDriverShift;
import com.epam.buber.entity.Order;
import com.epam.buber.entity.types.CarClass;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.EmailService;
import com.epam.buber.service.impl.EmailServiceImpl;
import com.epam.buber.service.impl.OrderServiceImpl;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class OrderStartCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        OrderServiceImpl orderService = OrderServiceImpl.getInstance();
        ListDriverShift listShift = ListDriverShift.getInstance();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        EmailServiceImpl emailService = EmailServiceImpl.getInstance();
        String clientLogin = session.getAttribute(SessionAttrName.USER_LOGIN).toString();
        Order order = new Order(Order.DEFAULT_ID);
        CarClass carClass = CarClass.define(request.getParameter(RequestParameterName.CLASS_AUTO));
        String page;
        try {
            if (listShift.getCountFreeDriver(carClass) > 0) {
                Client client = (Client) userService.findUser(clientLogin, UserRole.CLIENT);
                ShiftDriver shiftDriver = listShift.getQueueShift(carClass);
                order.setClient(client);
                order.setDriverShift(shiftDriver);
                order.setStartPoint(request.getParameter(RequestParameterName.START_POINT));
                order.setFinishPoint(request.getParameter(RequestParameterName.FINISH_POINT));
                order.setDistance(Double.valueOf(request.getParameter(RequestParameterName.DISTANCE)));
                order.setCost(Double.valueOf(request.getParameter(RequestParameterName.COST)));
                orderService.startOrder(order);
                shiftDriver.setCurrentOrder(order);
                if (order.getIdOrder() != Order.DEFAULT_ID) {
                    emailService.sendEmail(clientLogin, EmailService.EmailType.ORDER_START_CLIENT_8,
                            order.getClient().getName(),
                            String.valueOf(order.getIdOrder()),
                            order.getStartPoint(),
                            order.getDriverShift().getCar().getModel(),
                            order.getDriverShift().getCar().getColor(),
                            order.getDriverShift().getCar().getId(),
                            order.getDriverShift().getDriver().getName(),
                            order.getDriverShift().getDriver().getPhoneNum());
                    String emailDriver = shiftDriver.getDriver().getEmail();
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
