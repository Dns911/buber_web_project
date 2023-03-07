package com.epam.buber.service;

import com.epam.buber.entity.User;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean authenticate(String login, String password, UserRole role) throws ServiceException;

    boolean insertUser(Map<String, String> map) throws ServiceException;

    User findUser(String login, UserRole role) throws ServiceException;

    List<User> findAllUsersByRole(UserRole role) throws ServiceException;

    String restorePassword(String login, UserRole role) throws ServiceException;

    boolean setNewPassword(String login, UserRole role, String newPass) throws ServiceException;
}
