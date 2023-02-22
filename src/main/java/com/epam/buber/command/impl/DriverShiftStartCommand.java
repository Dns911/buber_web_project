package com.epam.buber.command.impl;

import com.epam.buber.command.Command;
import com.epam.buber.controller.Router;
import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.PagePath;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SessionAttrName;
import com.epam.buber.entity.Car;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.DriverShift;
import com.epam.buber.entity.RepositoryDriverShift;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.CommandException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.DriverShiftService;
import com.epam.buber.service.UserService;
import com.epam.buber.service.impl.DriverShiftServiceImpl;
import com.epam.buber.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class DriverShiftStartCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String login = session.getAttribute(SessionAttrName.USER_LOGIN).toString();
        DriverShiftService shiftService = DriverShiftServiceImpl.getInstance();
        UserService userService = UserServiceImpl.getInstance();
        String idCar = request.getParameter(RequestParameterName.CAR_ID);

        Router router;
        try {
            Driver driver = (Driver) userService.getUserFromBD(login, UserRole.DRIVER.getStringRole());
            Car car = new Car(); //todo
            DriverShift driverShift = shiftService.startShift(driver, car);
            RepositoryDriverShift repository = RepositoryDriverShift.getInstance();
            if (session.getAttribute(SessionAttrName.DRIVER_SYSTEM_STATUS).equals(AttrValue.SYS_STATUS_MSG_ACTIVE)){
                if (repository.checkFreeQueue(driver)){
                    session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_WAIT_ORDER);
                } else if (repository.checkOrderedList(driver)){
                    session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_IN_ORDER);
                } else {
                    session.setAttribute(SessionAttrName.DRIVER_WORK_STATUS, AttrValue.STATUS_MSG_WAIT_ORDER);
                    repository.addDriverShift(driverShift);
                }
            } else {
                request.setAttribute(RequestParameterName.STATUS_ERR, AttrValue.STATUS_ERR_MSG);
            }
            String page = PagePath.DRIVER_PAGE;
            router = new Router(page, Router.RouterType.FORWARD);

        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
