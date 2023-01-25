package com.epam.buber.dao.impl;

import com.epam.buber.connection.ConnectionPool;
import com.epam.buber.dao.BaseDao;
import com.epam.buber.dao.UserDao;
import com.epam.buber.entity.User;
import com.epam.buber.exception.DaoException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.List;

public class UserDaoImpl implements BaseDao<User>, UserDao {
    private static Logger logger = LogManager.getLogger();
    private static final String CHECK_PASSWORD_LOGIN = "SELECT password FROM users WHERE email = ?";
    private static final String ADD_USER = "INSERT INTO users (email, password, phone, name, last_name, date_registry) VALUES (?, ?, ?, ?, ?, ?)";
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
    public boolean authenticate(String login, String passwordHex) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_PASSWORD_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                String passFromDb;
                if (resultSet.next()) {
                    passFromDb = resultSet.getString("password");
                    match = passwordHex.equals(passFromDb);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    @Override
    public boolean registration(String email, String password, String phone, String name, String lastname) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {
            logger.log(Level.INFO,"Dao level parameters = {}, {}, {}, {}, {}",email, password, phone, name, lastname );
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, lastname);
            long currentDate = System.currentTimeMillis();
            Date date = new Date(currentDate);          //java.sql.Date
            preparedStatement.setDate(6, date); //Todo set current date
            int countRows = preparedStatement.executeUpdate();

            if (countRows == 1){
                match = true;
            } else {
                logger.log(Level.INFO,"Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }
}
