package com.epam.buber.dao;

import com.epam.buber.entity.User;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.DaoException;

import java.util.HashMap;
import java.util.List;

public interface UserDao {
    boolean authenticate(String login, String password, String role) throws DaoException;
    boolean registration(HashMap<String, String> map) throws DaoException;
    User findByEmailRole(String login, String role) throws DaoException;
    List<User> findAllByRole(String role) throws DaoException;
    boolean changePassword(String email, String passHex, UserRole role) throws DaoException;
}
