package com.epam.buber.service.impl;

import com.epam.buber.dao.impl.UserDaoImpl;
import com.epam.buber.exception.DaoException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.UserService;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }


    @Override
    public boolean authenticate(String login, String password) throws ServiceException {
        //
        //todo
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try {
            match = userDao.authenticate(login, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }
}
