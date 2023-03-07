package com.epam.buber.dao;

import com.epam.buber.entity.Order;
import com.epam.buber.entity.User;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.DaoException;

import java.util.List;

public interface OrderDao extends BaseDao<Order> {
    int getIdOrder(Order order) throws DaoException;

    void getCurrentRate(User user) throws DaoException;

    boolean setRate(Order order, UserRole role) throws DaoException;

    List<Order> findAll(User user) throws DaoException;
}
