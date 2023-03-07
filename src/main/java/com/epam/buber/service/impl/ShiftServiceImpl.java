package com.epam.buber.service.impl;

import com.epam.buber.dao.impl.ShiftDaoImpl;
import com.epam.buber.entity.*;
import com.epam.buber.exception.DaoException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.ShiftService;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ShiftServiceImpl implements ShiftService {
    private static final double START_INCOME = 0.0;
    private static final double START_LENGTH = 0.0;
    private static ShiftServiceImpl driverShiftServiceImplInstance;

    private ShiftServiceImpl() {
    }

    public static ShiftServiceImpl getInstance() {
        if (driverShiftServiceImplInstance == null) {
            driverShiftServiceImplInstance = new ShiftServiceImpl();
        }
        return driverShiftServiceImplInstance;
    }

    @Override
    public ShiftDriver startShift(Driver driver, Car car) throws ServiceException {
        ShiftDriver shiftDriver = new ShiftDriver();
        ShiftDaoImpl shiftDao = ShiftDaoImpl.getInstance();
        long currentTime = System.currentTimeMillis();
        Time timeStart = new Time(currentTime);
        Date date = new Date(currentTime);
        shiftDriver.setDriver(driver);
        shiftDriver.setCar(car);
        shiftDriver.setDate(date);
        shiftDriver.setStartTime(timeStart);
        shiftDriver.setIncome(START_INCOME);
        shiftDriver.setLength(START_LENGTH);
        try {
            shiftDao.insert(shiftDriver);
            long shiftId = shiftDao.find(shiftDriver).getIdShift();
            shiftDriver.setIdShift(shiftId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return shiftDriver;
    }

    @Override
    public void endShift(ShiftDriver shiftDriver) throws ServiceException {
        long currentTime = System.currentTimeMillis();
        Time timeFinish = new Time(currentTime);
        shiftDriver.setFinishTime(timeFinish);
        ShiftDaoImpl shiftDao = ShiftDaoImpl.getInstance();
        try {
            shiftDao.update(shiftDriver);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
