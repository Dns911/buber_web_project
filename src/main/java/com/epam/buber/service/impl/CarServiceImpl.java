package com.epam.buber.service.impl;

import com.epam.buber.dao.CarDao;
import com.epam.buber.dao.impl.CarDaoImpl;
import com.epam.buber.entity.Car;
import com.epam.buber.exception.DaoException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.CarService;
import com.epam.buber.validator.StringValidator;
import com.epam.buber.validator.ValidParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class CarServiceImpl implements CarService {

    private static Logger logger = LogManager.getLogger();
    private static final String CAR_ID_NOT_VALID = "**";
    private static CarServiceImpl carServiceImplInstance;
    private CarServiceImpl() {
    }

    public static CarServiceImpl getInstance() {
        if (carServiceImplInstance == null) {
            carServiceImplInstance = new CarServiceImpl();
        }
        return carServiceImplInstance;
    }

    @Override
    public Car getCarFromBD(String carId) throws ServiceException {
        CarDao carDao = CarDaoImpl.getInstance();
        Car car = new Car();
        try {
            if (!carId.isEmpty() || StringValidator.isCarId(carId)){
                car = carDao.getCar(carId);
            } else {
                car.setIdCar(CAR_ID_NOT_VALID);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return car;
    }

    @Override
    public List<Car> getCarsFromBD() throws ServiceException {
        return null;
    }

    @Override
    public boolean insertCar(HashMap<String, String> map) throws ServiceException {
        return false;
    }
}
