package com.epam.buber.service;

import com.epam.buber.entity.Car;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.DriverShift;
import com.epam.buber.entity.User;

import java.util.List;

public interface DriverShiftService {
    void addShiftToBD (DriverShift driverShift);
    List getListShiftFromBD(User user);

    DriverShift startShift(Driver driver, Car car);


    void endShift(DriverShift driverShift);

}
