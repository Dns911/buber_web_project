package com.epam.buber.service;

import com.epam.buber.entity.Car;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.ShiftDriver;
import com.epam.buber.entity.User;
import com.epam.buber.exception.ServiceException;

import java.util.List;

public interface ShiftService {

    ShiftDriver startShift(Driver driver, Car car) throws ServiceException;

    void endShift(ShiftDriver shiftDriver) throws ServiceException;

}
