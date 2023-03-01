package com.epam.buber.service;

import com.epam.buber.entity.User;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.ServiceException;

import java.util.HashMap;
import java.util.List;

public interface UserService {
    boolean authenticate(String login, String password,UserRole role) throws ServiceException;
    boolean addUser(HashMap<String, Object> map) throws ServiceException;
    User getUserFromBD(String login, UserRole role) throws ServiceException;
    List<User> getUserListByRole(UserRole role) throws ServiceException;
    String getNewPassword(String login, UserRole role) throws ServiceException;
}
