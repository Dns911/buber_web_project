package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.ShiftDriver;
import com.epam.buber.entity.ListDriverShift;
import com.epam.buber.entity.Order;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class OrderFinishCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        OrderServiceImpl orderService = OrderServiceImpl.getInstance();
        ListDriverShift listShift = ListDriverShift.getInstance();
        UserRole role = UserRole.define((String) session.getAttribute(SessionAttrName.USER_ROLE));
        String page;
        int rateFromUser = Integer.parseInt(request.getParameter(RequestParameterName.RATE));
        try {
            if (role.equals(UserRole.DRIVER)) {
                int driverId = (int) session.getAttribute(SessionAttrName.USER_ID);
                ShiftDriver shift = listShift.getShiftOrderedByDriverId(driverId);
                Order order = shift.getCurrentOrder();
                shift.addIncome(order.getCost());
                shift.addLength(order.getDistance());
                order.setRateFromDriver(rateFromUser);
                orderService.finishOrder(order);
                listShift.releaseQueueShift(order.getIdOrder());
                page = PagePath.DRIVER_PAGE;
            } else {
                int driverId = Integer.parseInt(request.getParameter(RequestParameterName.DR_ID));
                int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ID));
                orderService.setRateFromClient(driverId, orderId, rateFromUser);
                page = PagePath.CLIENT_PAGE;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return new Router(page, Router.RouterType.REDIRECT);
    }
}
