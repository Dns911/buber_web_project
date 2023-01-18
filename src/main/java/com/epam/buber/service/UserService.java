package com.epam.buber.service;

import com.epam.buber.exception.ServiceException;

public interface UserService {
    boolean authenticate(String login, String password) throws ServiceException;
    boolean registration(String email, String password, String phone, String name, String lastname) throws ServiceException;
}
