package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.ListDriverShift;
import com.epam.buber.entity.parameter.CarClass;
import com.epam.buber.exception.CommandException;
import com.epam.buber.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class PreOrderCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        ListDriverShift listDriverShift = ListDriverShift.getInstance();
        OrderServiceImpl orderService = OrderServiceImpl.getInstance();
        String loginClient = (String) session.getAttribute(SessionAttrName.USER_LOGIN);
        String page;
        String dist = request.getParameter(RequestParameterName.DISTANCE);
        if (loginClient.equals(AttrValue.GUEST_MSG) || loginClient.isEmpty()) {
            page = PagePath.LOGIN;
        } else if (!dist.isEmpty() && !(Double.valueOf(dist) == 0)) {
            CarClass carClass = CarClass.define(request.getParameter(RequestParameterName.CLASS_AUTO));
            String cost = orderService.getCost(dist, carClass);
            int countCars = listDriverShift.getCountFreeDriver(carClass);
            request.setAttribute(RequestParameterName.COST, cost);
            request.setAttribute(RequestParameterName.COUNT_FREE_CARS, countCars);
            page = PagePath.PREORDER;
        } else {
            page = PagePath.MAIN;
        }
        return new Router(page, Router.RouterType.FORWARD);
    }
}
