package com.epam.buber.dao;

import com.epam.buber.entity.AbstractEntity;
import com.epam.buber.exception.DaoException;

import java.util.List;

public interface BaseDao<T extends AbstractEntity> {
    boolean insert(T t) throws DaoException;
    boolean delete(T t) throws DaoException;
    boolean update(T t) throws DaoException;
    T find(T t) throws DaoException;
}
