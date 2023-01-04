package com.epam.buber.service;

import com.epam.buber.exception.ServiceException;

public interface UserService {
    boolean authenticate(String login, String password) throws ServiceException;
}
