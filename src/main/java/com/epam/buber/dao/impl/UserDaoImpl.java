package com.epam.buber.dao.impl;

import com.epam.buber.connection.ConnectionPool;
import com.epam.buber.dao.BaseDao;
import com.epam.buber.dao.UserDao;
import com.epam.buber.entity.User;
import com.epam.buber.exception.DaoException;
import org.apache.commons.codec.digest.DigestUtils;


import java.sql.*;
import java.util.List;

public class UserDaoImpl implements BaseDao<User>, UserDao {

    private static final String CHECK_PASSWORD_LOGIN = "SELECT password FROM users WHERE email = ?";
    private static UserDaoImpl userDaoImplInstance;

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (userDaoImplInstance == null) {
            userDaoImplInstance = new UserDaoImpl();
        }
        return userDaoImplInstance;
    }

    @Override
    public boolean insert(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean authenticate(String login, String password) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_PASSWORD_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            String passwordHex = DigestUtils.md5Hex(password);
            String passFromDb;
            if (resultSet.next()) {
                passFromDb = resultSet.getString("password");
                match = passwordHex.equals(passFromDb);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }
}
