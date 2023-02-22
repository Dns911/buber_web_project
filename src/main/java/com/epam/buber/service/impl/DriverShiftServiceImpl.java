package com.epam.buber.service.impl;

import com.epam.buber.entity.*;
import com.epam.buber.service.DriverShiftService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class DriverShiftServiceImpl implements DriverShiftService {
    private static final double START_INCOME = 0.0;
    private static final double START_LENGTH = 0.0;
    private static DriverShiftServiceImpl driverShiftServiceImplInstance;

    private DriverShiftServiceImpl() {
    }

    public static DriverShiftServiceImpl getInstance() {
        if (driverShiftServiceImplInstance == null) {
            driverShiftServiceImplInstance = new DriverShiftServiceImpl();
        }
        return driverShiftServiceImplInstance;
    }


    @Override
    public void addShiftToBD(DriverShift driverShift) {

    }

    @Override
    public List<DriverShift> getListShiftFromBD(User user) {
        return null;
    }

    @Override
    public DriverShift startShift(Driver driver, Car car) {
        DriverShift driverShift = new DriverShift();
        long currentTime = System.currentTimeMillis();
        Time timeStart = new Time(currentTime);
        Date date = new Date(currentTime);
        driverShift.setIdDriver(driver.getId());
        driverShift.setIdCar(car.getIdCar());
        driverShift.setDate(date);
        driverShift.setStartTime(timeStart);
        driverShift.setIncome(START_INCOME);
        driverShift.setLength(START_LENGTH);

        return driverShift;
    }

    @Override
    public void endShift(DriverShift driverShift) {
        long currentTime = System.currentTimeMillis();
        Time timeFinish = new Time(currentTime);
        driverShift.setFinishTime(timeFinish);
    }
}
