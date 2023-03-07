package com.epam.buber.dao;

import com.epam.buber.entity.User;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.DaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserDao extends BaseDao<User> {
    boolean authenticate(String login, String password, UserRole role) throws DaoException;

    boolean registration(Map<String, String> map) throws DaoException;

    List<User> findAllByRole(UserRole role) throws DaoException;

    boolean changePassword(String email, String passHex, UserRole role) throws DaoException;

    void updateUserRate(User user) throws DaoException;

    void updateUserIncome(User user, double income) throws DaoException;
}
