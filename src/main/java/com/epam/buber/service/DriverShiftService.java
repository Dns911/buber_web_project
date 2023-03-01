package com.epam.buber.service;

import com.epam.buber.entity.Car;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.DriverShift;
import com.epam.buber.entity.User;
import com.epam.buber.exception.ServiceException;

import java.util.List;

public interface DriverShiftService {
    void addShiftToBD (DriverShift driverShift) throws ServiceException;
    List getListShiftFromBD(User user);

    DriverShift startShift(Driver driver, Car car) throws ServiceException;


    void endShift(DriverShift driverShift) throws ServiceException;

}
