package com.epam.buber.service;

import com.epam.buber.entity.Order;
import com.epam.buber.entity.types.CarClass;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

public interface OrderService {
    String getCost(String distance, CarClass classAuto) throws ServiceException;

    void startOrder(Order order) throws ServiceException;

    void finishOrder(Order order) throws ServiceException;

    void orderInfo(HttpServletRequest request, Order order, UserRole role);

    void setRateFromClient(int driverId, int orderId, int rateFromClient) throws ServiceException;
}
