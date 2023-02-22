package com.epam.buber.service.impl;

import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.dao.impl.UserDaoImpl;
import com.epam.buber.entity.User;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.DaoException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.CommonService;
import com.epam.buber.service.UserService;
import com.epam.buber.validator.MapValidator;
import com.epam.buber.validator.StringValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger();
    private static UserServiceImpl instance = new UserServiceImpl();
    private static final int LENGTH = 10;
    public static final String NEW_PASS_ERROR = "Пользователя не зарегистрирован!";

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }


    @Override
    public boolean authenticate(String login, String password, String role) throws ServiceException {
        if (!(StringValidator.isEmail(login) || StringValidator.isPhoneNum(login)) || !StringValidator.isPassword(password)){
            // TODO: 18-Jan-23  add logger (unsuccessful login, date, time)
            logger.log(Level.INFO,"Validation not completed");
            return false;
        }

        String passwordHex = DigestUtils.md5Hex(password); //crypte password
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try {
            logger.log(Level.INFO, "service1 role: " + role);
            match = userDao.authenticate(login, passwordHex, role);
            logger.log(Level.INFO, "service2 role: " + role);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }


    public boolean registration(HashMap<String, String> map) throws ServiceException {
        if (!MapValidator.userFormValid(map)){
            logger.log(Level.INFO,"Registration not completed, check parameters");
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        logger.log(Level.INFO,"dao work");
        String passwordHex = DigestUtils.md5Hex(map.get(RequestParameterName.PASSWORD));
        map.put(RequestParameterName.PASSWORD, passwordHex);
        boolean match = false;
        try {
            match = userDao.registration(map);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return match;
    }

    @Override
    public User getUserFromBD(String login, String role) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        User user;
        try {
            user = userDao.findByEmailRole(login, role);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }
    public List<User> getUserListByRole(String role) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        List<User> userList = new ArrayList<>();
        try {
            userList = userDao.findAllByRole(role);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userList;
    }

    public String getNewPassword(String login, UserRole role) throws ServiceException{
        CommonServiceImpl commonService = CommonServiceImpl.getInstance();
        String newPass = commonService.generateRandomPassword(LENGTH);
        String passwordHex = DigestUtils.md5Hex(newPass);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        String result;
        try {
             result = userDao.changePassword(login, passwordHex, role) ? newPass : NEW_PASS_ERROR;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
