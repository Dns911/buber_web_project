package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.Car;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.ShiftDriver;
import com.epam.buber.entity.ListDriverShift;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.CarService;
import com.epam.buber.service.ShiftService;
import com.epam.buber.service.UserService;
import com.epam.buber.service.impl.CarServiceImpl;
import com.epam.buber.service.impl.ShiftServiceImpl;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class DriverShiftStartCommand implements Command {
    public static final String CAR_ID_NOT_FOUND = "*";
    public static final String CAR_ID_NOT_VALID = "**";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String login = session.getAttribute(SessionAttrName.USER_LOGIN).toString();
        ShiftService shiftService = ShiftServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        CarService carService = CarServiceImpl.getInstance();
        String idCar = request.getParameter(RequestParameterName.CAR_ID);
        String page = PagePath.DRIVER_PAGE;
        Router router = new Router(page, Router.RouterType.FORWARD);
        if (!session.getAttribute(SessionAttrName.DRIVER_WORK_STATUS).equals(AttrValue.STATUS_MSG_REST)) {
            request.setAttribute(RequestParameterName.CAR_ID_ERR, AttrValue.CAR_ID_ERR_MSG_3);
            return router;
        }
        try {
            Driver driver = (Driver) userService.findUser(login, UserRole.DRIVER);
            Car car = carService.findCar(idCar);
            if (session.getAttribute(SessionAttrName.DRIVER_SYSTEM_STATUS).equals(AttrValue.SYS_STATUS_MSG_ACTIVE)) {
                if (car.getId().equals(CAR_ID_NOT_FOUND)) {
                    request.setAttribute(RequestParameterName.CAR_ID_ERR, AttrValue.CAR_ID_ERR_MSG_1);
                    return router;
                } else if ((car.getId().equals(CAR_ID_NOT_VALID))) {
                    request.setAttribute(RequestParameterName.CAR_ID_ERR, AttrValue.CAR_ID_ERR_MSG_2);
                    return router;
                }
                ShiftDriver shiftDriver = shiftService.startShift(driver, car);
                ListDriverShift listShifts = ListDriverShift.getInstance();
                if (listShifts.checkFreeQueue(driver.getId())) {
                    session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_WAIT_ORDER);
                } else if (listShifts.checkOrderedListByDriverId(driver.getId())) {
                    session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_IN_ORDER);
                } else {
                    session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_WAIT_ORDER);
                    listShifts.addQueueShift(shiftDriver);
                }
            } else {
                request.setAttribute(RequestParameterName.STATUS_ERR, AttrValue.STATUS_ERR_MSG);
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
