package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.DriverShift;
import com.epam.buber.entity.ListDriverShift;
import com.epam.buber.entity.Order;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class OrderInfoCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        OrderServiceImpl orderService = OrderServiceImpl.getInstance();
        ListDriverShift listShift = ListDriverShift.getInstance();
        int userId = (int) session.getAttribute(SessionAttrName.USER_ID);
        UserRole role = UserRole.define((String) session.getAttribute(SessionAttrName.USER_ROLE));
        String page;
        if (role.equals(UserRole.DRIVER)) {
            if (listShift.checkOrderedListByDriverId(userId)) {
                DriverShift shift = listShift.getShiftOrderedByDriverId(userId);
                Order order = shift.getCurrentOrder();
                orderService.orderInfo(request, order, UserRole.DRIVER);
                page = PagePath.ORDER_INFO_DRIVER;
            } else if (listShift.checkFreeQueue(userId)) {
                page = PagePath.DRIVER_PAGE;
                request.setAttribute(RequestParameterName.CURRENT_ORDER_MSG, AttrValue.CURRENT_ORDER_MSG_1);
            } else {
                page = PagePath.DRIVER_PAGE;
                request.setAttribute(RequestParameterName.CURRENT_ORDER_MSG, AttrValue.CURRENT_ORDER_MSG_2);
            }
        } else {
            if (listShift.checkOrderedByClientId(userId)) {
                DriverShift shift = listShift.getShiftOrderedByClientId(userId);
                Order order = shift.getCurrentOrder();
                if (order.getRateFromClient() == 0) {
                    orderService.orderInfo(request, order, UserRole.CLIENT);
                    page = PagePath.ORDER_INFO_CLIENT;
                } else {
                    request.setAttribute(RequestParameterName.CURRENT_ORDER_MSG, AttrValue.CURRENT_ORDER_MSG_3);
                    page = PagePath.CLIENT_PAGE;
                }
            } else {
                request.setAttribute(RequestParameterName.CURRENT_ORDER_MSG, AttrValue.CURRENT_ORDER_MSG_1);
                page = PagePath.CLIENT_PAGE;
            }
        }
        Router router = new Router(page, Router.RouterType.FORWARD);
        return router;
    }
}
