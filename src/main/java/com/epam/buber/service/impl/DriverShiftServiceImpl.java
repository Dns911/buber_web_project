package com.epam.buber.service.impl;

import com.epam.buber.dao.impl.ShiftDaoImpl;
import com.epam.buber.entity.*;
import com.epam.buber.exception.DaoException;
import com.epam.buber.exception.ServiceException;
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
    public void addShiftToBD(DriverShift driverShift) throws ServiceException {

    }

    @Override
    public List<DriverShift> getListShiftFromBD(User user) {
        return null;
    }

    @Override
    public DriverShift startShift(Driver driver, Car car) throws ServiceException {
        DriverShift driverShift = new DriverShift();
        ShiftDaoImpl shiftDao = ShiftDaoImpl.getInstance();
        long currentTime = System.currentTimeMillis();
        Time timeStart = new Time(currentTime);
        Date date = new Date(currentTime);
        driverShift.setDriver(driver);
        driverShift.setCar(car);
        driverShift.setDate(date);
        driverShift.setStartTime(timeStart);
        driverShift.setIncome(START_INCOME);
        driverShift.setLength(START_LENGTH);
        try {
            shiftDao.insert(driverShift);
            long shiftId = shiftDao.getShiftId(driverShift);
            driverShift.setIdShift(shiftId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return driverShift;
    }

    @Override
    public void endShift(DriverShift driverShift) throws ServiceException {
        long currentTime = System.currentTimeMillis();
        Time timeFinish = new Time(currentTime);
        driverShift.setFinishTime(timeFinish);
        ShiftDaoImpl shiftDao = ShiftDaoImpl.getInstance();
        try {
            shiftDao.updateFinish(driverShift);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
