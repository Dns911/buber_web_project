package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.DriverShift;
import com.epam.buber.entity.ListDriverShift;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.impl.DriverShiftServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class DriverShiftEndCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        DriverShiftServiceImpl shiftService = DriverShiftServiceImpl.getInstance();
        ListDriverShift listDriverShifts = ListDriverShift.getInstance();
        String page = PagePath.DRIVER_PAGE;
        Router router;
        int driverId = (int) session.getAttribute(SessionAttrName.USER_ID);
        try {
            if (session.getAttribute(SessionAttrName.DRIVER_WORK_STATUS).equals(AttrValue.STATUS_MSG_WAIT_ORDER)) {
                DriverShift shift = listDriverShifts.getShiftQueueByDriverId(driverId);
                if (listDriverShifts.removeQueueShift(driverId)) {
                    shiftService.endShift(shift);
                    session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_REST);
                    request.setAttribute(RequestParameterName.DRIVER_OUT, AttrValue.DRIVER_OUT);
                }
            } else {
                request.setAttribute(RequestParameterName.DRIVER_OUT, AttrValue.DRIVER_OUT_ERR);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        router = new Router(page, Router.RouterType.FORWARD);
        return router;
    }
}
