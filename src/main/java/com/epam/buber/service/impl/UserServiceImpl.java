package com.epam.buber.service.impl;

import com.epam.buber.dao.impl.UserDaoImpl;
import com.epam.buber.entity.User;
import com.epam.buber.exception.DaoException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.UserService;
import com.epam.buber.validator.ValidString;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger();
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
        if (!(ValidString.isEmail(login) || ValidString.isPhoneNum(login)) || !ValidString.isPassword(password)){
            // TODO: 18-Jan-23  add logger (unsuccessful login, date, time)
            logger.log(Level.INFO,"Validation not completed");
            return false;
        }

        String passwordHex = DigestUtils.md5Hex(password); //crypte password
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try {
            match = userDao.authenticate(login, passwordHex);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }

    @Override
    public boolean registration(String email, String password, String phone, String name, String lastname) throws ServiceException {
        if (!(ValidString.isEmail(email) && ValidString.isPassword(password) && ValidString.isPhoneNum(phone) &&
                ValidString.isNameSurname(name) && ValidString.isNameSurname(lastname))){
            // TODO: 18-Jan-23  add logger (unsuccessful login, date, time)
            logger.log(Level.INFO,"Registration not completed, check parameters");
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        String passwordHex = DigestUtils.md5Hex(password);
        boolean match = false;
        try {
            match = userDao.registration(email, passwordHex, phone, name, lastname);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        return match;
    }
}
