package com.epam.buber.service.impl;

import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.dao.impl.UserDaoImpl;
import com.epam.buber.entity.Client;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.User;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.DaoException;
import com.epam.buber.exception.ServiceException;
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
import java.util.Map;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance = new UserServiceImpl();
    private static final int LENGTH = 8;
    public static final String NEW_PASS_ERROR = "Пользователь не зарегистрирован!";

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    public boolean authenticate(String login, String password, UserRole role) throws ServiceException {
        if (!(StringValidator.isEmail(login) || StringValidator.isPhoneNum(login)) || !StringValidator.isPassword(password)) {
            return false;
        }
        String passwordHex = DigestUtils.md5Hex(password); //crypt password
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try {
            match = userDao.authenticate(login, passwordHex, role);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }

    public boolean insertUser(Map<String, String> map) throws ServiceException {
        if (!MapValidator.userFormValidate(map)) {
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
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

    public User findUser(String login, UserRole role) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        User user = role.equals(UserRole.DRIVER) ? new Driver() : new Client();
        user.setEmail(login);
        user.setRole(role);
        try {
            user = userDao.find(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public List<User> findAllUsersByRole(UserRole role) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        List<User> userList;
        try {
            userList = userDao.findAllByRole(role);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return userList;
    }

    public String restorePassword(String login, UserRole role) throws ServiceException {
        CommonServiceImpl commonService = CommonServiceImpl.getInstance();
        String newPass = commonService.generateRandomPassword(LENGTH);
        String passwordHex = DigestUtils.md5Hex(newPass);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        String result;
        try {
            result = userDao.changePassword(login, passwordHex, role) ? newPass : AttrValue.NEW_PASS_ERROR;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    public boolean setNewPassword(String login, UserRole role, String newPass) throws ServiceException {
        boolean match;
        if (StringValidator.isPassword(newPass)) {
            try {
                String passwordHex = DigestUtils.md5Hex(newPass);
                UserDaoImpl userDao = UserDaoImpl.getInstance();
                userDao.changePassword(login, passwordHex, role);
                match = true;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            match = false;
        }
        return match;
    }
}
