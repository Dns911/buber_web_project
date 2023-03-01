package com.epam.buber.dao;

import com.epam.buber.entity.User;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.DaoException;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    boolean authenticate(String login, String password, UserRole role) throws DaoException;
    boolean registration(HashMap<String, Object> map) throws DaoException;
    User findByEmailRole(String login, UserRole role) throws DaoException;
    List<User> findAllByRole(UserRole role) throws DaoException;
    boolean changePassword(String email, String passHex, UserRole role) throws DaoException;
}
