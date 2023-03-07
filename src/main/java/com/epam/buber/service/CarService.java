package com.epam.buber.service;

import com.epam.buber.entity.Car;
import com.epam.buber.exception.ServiceException;

import java.util.HashMap;
import java.util.List;

public interface CarService {
    Car findCar(String carId)throws ServiceException;
    List<Car> findAllCars()throws ServiceException;
    boolean insertCar(HashMap<String, String> map) throws ServiceException;
}
