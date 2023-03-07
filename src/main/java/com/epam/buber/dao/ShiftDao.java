package com.epam.buber.dao;

import com.epam.buber.entity.Driver;
import com.epam.buber.entity.ShiftDriver;
import com.epam.buber.exception.DaoException;

import java.util.List;

public interface ShiftDao extends BaseDao<ShiftDriver> {
    List<ShiftDriver> findAll(Driver driver) throws DaoException;
}
