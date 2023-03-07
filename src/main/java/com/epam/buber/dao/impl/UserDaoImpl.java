package com.epam.buber.dao.impl;

import com.epam.buber.connection.ConnectionPool;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SQLColumnName;
import com.epam.buber.dao.UserDao;
import com.epam.buber.entity.Client;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.User;
import com.epam.buber.entity.types.DriverStatus;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String INCORRECT_STRING = "**";
    private static final String CHECK_PASS_LOG_CLIENT = "SELECT password FROM clients WHERE email = ?";
    private static final String CHECK_PASS_LOG_DRIVER = "SELECT password FROM drivers WHERE email = ?";
    private static final String CHECK_PHONE_CLIENT = "SELECT * FROM clients WHERE phone = ?";
    private static final String CHECK_PHONE_DRIVER = "SELECT * FROM drivers WHERE phone = ?";
    private static final String CHECK_EMAIL_CLIENT = "SELECT * FROM buber_db.clients WHERE email = ?";
    private static final String CHECK_EMAIL_DRIVER = "SELECT * FROM buber_db.drivers WHERE email = ?";
    private static final String GET_ALL_DRIVERS = "SELECT email FROM drivers WHERE role = ? ";
    private static final String GET_ALL_CLIENTS = "SELECT email FROM clients WHERE role = ? ";
    private static final String ADD_CLIENT = "INSERT INTO clients (email, password, phone, name, last_name, date_registry) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ADD_DRIVER = "INSERT INTO drivers (email, password, phone, name, last_name, date_registry, driver_lic_number, driver_lic_valid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CLIENT = "UPDATE clients SET email = ?, phone = ?, name = ?, last_name = ? WHERE id = ?";
    private static final String UPDATE_DRIVER = "UPDATE drivers SET email = ?, phone = ?, name = ?, last_name = ?, driver_lic_number = ?, driver_lic_valid = ?, status = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD_CLIENT = "UPDATE clients SET password = ? WHERE email = ?";
    private static final String UPDATE_PASSWORD_DRIVER = "UPDATE drivers SET password = ? WHERE email = ?";
    private static final String UPDATE_RATE_CLIENT = "UPDATE clients SET rate = ? WHERE id = ?";
    private static final String UPDATE_RATE_DRIVER = "UPDATE drivers SET rate = ? WHERE id = ?";
    private static final String UPDATE_INCOME_CLIENT = "UPDATE clients SET payment_sum = payment_sum + ? WHERE id = ?";
    private static final String UPDATE_INCOME_DRIVER = "UPDATE drivers SET income_sum = income_sum + ? WHERE id = ?";
    private static UserDaoImpl userDaoImplInstance;

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (userDaoImplInstance == null) {
            userDaoImplInstance = new UserDaoImpl();
        }
        return userDaoImplInstance;
    }

    public boolean authenticate(String login, String passwordHex, UserRole role) throws DaoException {
        boolean match = false;
        String loginType;
        if (role.equals(UserRole.CLIENT)) {
            loginType = CHECK_PASS_LOG_CLIENT;
        } else {
            loginType = CHECK_PASS_LOG_DRIVER;
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(loginType)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                String passFromDb;
                if (resultSet.next()) {
                    passFromDb = resultSet.getString(SQLColumnName.PASS);
                    match = passwordHex.equals(passFromDb);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    public boolean registration(Map<String, String> map) throws DaoException {
        UserRole role = UserRole.define(map.get(RequestParameterName.USER_ROLE));
        String queryType = ADD_CLIENT;
        if (role.equals(UserRole.DRIVER)) {
            queryType = ADD_DRIVER;
        }
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            boolean checkEmail = checkUserEmailPhone(connection, role, SQLColumnName.EMAIL, map.get(RequestParameterName.EMAIL));
            if (!checkEmail) {
                preparedStatement.setString(1, map.get(RequestParameterName.EMAIL));
            } else {
                map.replace(RequestParameterName.EMAIL, INCORRECT_STRING);
                return false;
            }
            preparedStatement.setString(2, map.get(RequestParameterName.PASSWORD));
            boolean checkPhone = checkUserEmailPhone(connection, role, SQLColumnName.PHONE, map.get(RequestParameterName.PHONE_NUM));
            if (!checkPhone) {
                preparedStatement.setString(3, map.get(RequestParameterName.PHONE_NUM));
            } else {
                map.replace(RequestParameterName.PHONE_NUM, INCORRECT_STRING);
                return false;
            }
            preparedStatement.setString(3, map.get(RequestParameterName.PHONE_NUM));
            preparedStatement.setString(4, map.get(RequestParameterName.USER_NAME));
            preparedStatement.setString(5, map.get(RequestParameterName.USER_LASTNAME));
            long currentDate = System.currentTimeMillis();
            Date date = new Date(currentDate);
            preparedStatement.setDate(6, date);
            if (role.equals(UserRole.DRIVER)) {
                preparedStatement.setString(7, map.get(RequestParameterName.DRIVER_LIC_NUMBER));
                preparedStatement.setString(8, map.get(RequestParameterName.DRIVER_LIC_VALID));
            }
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.WARN, "Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    public List<User> findAllByRole(UserRole role) throws DaoException {
        String userType;
        List<User> listUser = new ArrayList<>();
        User user;
        if (role.equals(UserRole.DRIVER)) {
            userType = GET_ALL_DRIVERS;
            user = new Driver();
        } else {
            userType = GET_ALL_CLIENTS;
            user = new Client();
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(userType)) {
            preparedStatement.setString(1, role.toString());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setEmail(resultSet.getString(SQLColumnName.EMAIL));
                    listUser.add(find(user));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return listUser;
    }

    public boolean changePassword(String email, String passHex, UserRole role) throws DaoException {
        boolean match = false;
        String queryType = role.equals(UserRole.DRIVER) ? UPDATE_PASSWORD_DRIVER : UPDATE_PASSWORD_CLIENT;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setString(1, passHex);
            preparedStatement.setString(2, email);
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.WARN, "Update user password count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    public void updateUserRate(User user) throws DaoException {
        String queryType = user.getRole().equals(UserRole.DRIVER) ? UPDATE_RATE_DRIVER : UPDATE_RATE_CLIENT;
        int id = user.getId();
        double rate = user.getRate();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setDouble(1, rate);
            preparedStatement.setInt(2, id);
            int countRows = preparedStatement.executeUpdate();
            if (countRows != 1) {
                logger.log(Level.WARN, "Update user rate count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void updateUserIncome(User user, double income) throws DaoException {
        String queryType = user.getRole().equals(UserRole.DRIVER) ? UPDATE_INCOME_DRIVER : UPDATE_INCOME_CLIENT;
        int id = user.getId();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setDouble(1, income);
            preparedStatement.setInt(2, id);
            int countRows = preparedStatement.executeUpdate();
            if (countRows != 1) {
                logger.log(Level.WARN, "Update user income count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User find(User user) throws DaoException {
        String queryType;
        UserRole role = user.getRole();
        if (role.equals(UserRole.DRIVER)) {
            queryType = CHECK_EMAIL_DRIVER;
        } else {
            queryType = CHECK_EMAIL_CLIENT;
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setString(1, user.getEmail());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(SQLColumnName.ID));
                    user.setEmail(resultSet.getString(SQLColumnName.EMAIL));
                    user.setPhoneNum(resultSet.getString(SQLColumnName.PHONE));
                    user.setName(resultSet.getString(SQLColumnName.NAME));
                    user.setLastName(resultSet.getString(SQLColumnName.LAST_NAME));
                    user.setRegistrationDate(resultSet.getDate(SQLColumnName.DATE_REGISTRY));
                    user.setRate(resultSet.getDouble(SQLColumnName.RATE));
                    user.setRole(UserRole.define(resultSet.getString(SQLColumnName.ROLE)));
                    if (role.equals(UserRole.DRIVER)) {
                        ((Driver) user).setIncomeSum(resultSet.getDouble(SQLColumnName.INCOME_SUM));
                        ((Driver) user).setStatus(DriverStatus.define(resultSet.getString(SQLColumnName.STATUS)));
                        ((Driver) user).setLicenceNum(resultSet.getString(SQLColumnName.DRIVER_LIC_NUMBER));
                        ((Driver) user).setLicenceValidDate(resultSet.getDate(SQLColumnName.DRIVER_LIC_VALID));
                    } else {
                        ((Client) user).setPaymentSum(resultSet.getDouble(SQLColumnName.PAYMENT_SUM));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean update(User user) throws DaoException {
        String queryType = user.getRole().equals(UserRole.DRIVER) ? UPDATE_DRIVER : UPDATE_CLIENT;
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPhoneNum());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getLastName());
            if (user.getRole().equals(UserRole.CLIENT)) {
                preparedStatement.setInt(5, (user).getId());
            } else {
                preparedStatement.setString(5, ((Driver) user).getLicenceNum());
                preparedStatement.setDate(6, ((Driver) user).getLicenceValidDate());
                preparedStatement.setString(7, ((Driver) user).getStatus().toString());
                preparedStatement.setInt(8, (user).getId());
            }
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.WARN, "Update user count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    private boolean checkUserEmailPhone(Connection connection, UserRole role, String nameCol, String valueCol) throws DaoException {
        String queryType;
        if (role.equals(UserRole.DRIVER)) {
            if (nameCol.equals(SQLColumnName.EMAIL)) {
                queryType = CHECK_EMAIL_DRIVER;
            } else {
                queryType = CHECK_PHONE_DRIVER;
            }
        } else {
            if (nameCol.equals(SQLColumnName.EMAIL)) {
                queryType = CHECK_EMAIL_CLIENT;
            } else {
                queryType = CHECK_PHONE_CLIENT;
            }
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setString(1, valueCol);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    resultSet.close();
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return false;
    }
}
