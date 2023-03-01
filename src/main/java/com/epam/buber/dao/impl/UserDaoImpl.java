package com.epam.buber.dao.impl;

import com.epam.buber.connection.ConnectionPool;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.controller.info.SQLColumnName;
import com.epam.buber.dao.UserDao;
import com.epam.buber.entity.Client;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.User;
import com.epam.buber.entity.parameter.DriverStatus;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String CHECK_PASS_LOG_CLIENT = "SELECT password FROM clients WHERE email = ?";
    private static final String CHECK_PASS_LOG_DRIVER = "SELECT password FROM drivers WHERE email = ?";
    private static final String CHECK_PHONE_CLIENT = "SELECT * FROM clients WHERE phone = ?";
    private static final String CHECK_PHONE_DRIVER = "SELECT * FROM drivers WHERE phone = ?";
    private static final String CHECK_EMAIL_CLIENT = "SELECT * FROM buber_db.clients WHERE email = ?";
    private static final String CHECK_EMAIL_DRIVER = "SELECT * FROM buber_db.drivers WHERE email = ?";
    private static final String GET_ALL_DRIVERS = "SELECT login FROM drivers WHERE role = ? ";
    private static final String GET_ALL_CLIENTS = "SELECT login FROM clients WHERE role = ? ";
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

    public boolean insert(User user) {
        return false;
    }

    public boolean delete(User user) {
        return false;
    }

    public List<User> findAll() {
        return null;
    }

    public boolean update(User user) throws DaoException {
        String queryType = user.getRole().equals(UserRole.DRIVER) ? UPDATE_DRIVER : UPDATE_CLIENT;
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
//            "UPDATE clients SET email = ?1, phone = ?2, name = ?3, last_name = ?4 WHERE id = ?5"
//            "UPDATE drivers SET email = ?1, phone = ?2, name = ?3, last_name = ?4, driver_lic_number = ?5, driver_lic_valid = ?6 status = ?7 WHERE id = ?8"
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPhoneNum());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getLastName());
            if (user.getRole().equals(UserRole.CLIENT)) {
                preparedStatement.setInt(5, (user).getId());
            } else {
                preparedStatement.setString(5, ((Driver) user).getLicenceNum());
                preparedStatement.setDate(6, ((Driver) user).getLicenceValidDate());
                preparedStatement.setString(7, ((Driver) user).getStatus().getStringStatus());
                preparedStatement.setInt(8, (user).getId());
            }
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.INFO, "Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    @Override
    public boolean authenticate(String login, String passwordHex, UserRole role) throws DaoException {
        boolean match = false;
        String loginType;
        if (role.equals(UserRole.CLIENT)) {
            loginType = CHECK_PASS_LOG_CLIENT;
            logger.log(Level.INFO, "table client");
        } else {
            loginType = CHECK_PASS_LOG_DRIVER;
            logger.log(Level.INFO, "table client");
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(loginType)) {
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
    public boolean registration(HashMap<String, Object> map) throws DaoException {
        UserRole role = UserRole.define(map.get(RequestParameterName.USER_ROLE).toString());
        String queryType = ADD_CLIENT;
        if (role.equals(UserRole.DRIVER)) {
            queryType = ADD_DRIVER;
        }
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            boolean checkEmail = checkUserEmailPhone(connection, role, SQLColumnName.EMAIL, map.get(RequestParameterName.EMAIL).toString());
            if (!checkEmail) {
                preparedStatement.setString(1, map.get(RequestParameterName.EMAIL).toString());
            } else {
                map.replace(RequestParameterName.EMAIL, "**");
                return false;
            }
            preparedStatement.setString(2, map.get(RequestParameterName.PASSWORD).toString());
            boolean checkPhone = checkUserEmailPhone(connection, role, SQLColumnName.PHONE, map.get(RequestParameterName.PHONE_NUM).toString());
            if (!checkPhone) {
                preparedStatement.setString(3, map.get(RequestParameterName.PHONE_NUM).toString());
            } else {
                map.replace(RequestParameterName.PHONE_NUM, "**");
                return false;
            }
            preparedStatement.setString(3, map.get(RequestParameterName.PHONE_NUM).toString());
            preparedStatement.setString(4, map.get(RequestParameterName.USER_NAME).toString());
            preparedStatement.setString(5, map.get(RequestParameterName.USER_LASTNAME).toString());
            long currentDate = System.currentTimeMillis();
            Date date = new Date(currentDate);          //java.sql.Date
            preparedStatement.setDate(6, date); //Todo set current date
            if (role.equals(UserRole.DRIVER)) {
                preparedStatement.setString(7, map.get(RequestParameterName.DRIVER_LIC_NUMBER).toString());
                preparedStatement.setString(8, map.get(RequestParameterName.DRIVER_LIC_VALID).toString());
            }
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.INFO, "Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    @Override
    public User findByEmailRole(String email, UserRole role) throws DaoException {
        User user;
        String queryType;
        if (role.equals(UserRole.DRIVER)) {
            user = new Driver();
            queryType = CHECK_EMAIL_DRIVER;
        } else {
            user = new Client();
            queryType = CHECK_EMAIL_CLIENT;
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setString(1, email);
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

    public List<User> findAllByRole(UserRole role) throws DaoException {
        String userType;
        List<User> listUser = new ArrayList<>();
        if (role.equals(UserRole.DRIVER.getStringRole())) {
            userType = GET_ALL_DRIVERS;
        } else {
            userType = GET_ALL_CLIENTS;
        }
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(userType)) {
            preparedStatement.setString(1, role.getStringRole());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    listUser.add(findByEmailRole(resultSet.getString(SQLColumnName.EMAIL), role));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return listUser;
    }

    private boolean checkUserEmailPhone(Connection connection, UserRole role, String nameCol, String valueCol) throws DaoException {
        String queryType;
        if (role.equals(UserRole.DRIVER)) {
            if (nameCol.equals(SQLColumnName.EMAIL)) {
                queryType = CHECK_EMAIL_DRIVER;
                // SELECT * FROM drivers WHERE email = ?
            } else {
                queryType = CHECK_PHONE_DRIVER;
                // SELECT * FROM drivers WHERE phone = ?
            }
        } else {
            if (nameCol.equals(SQLColumnName.EMAIL)) {
                queryType = CHECK_EMAIL_CLIENT;
                // SELECT * FROM clients WHERE email = ?
            } else {
                queryType = CHECK_PHONE_CLIENT;
                // SELECT * FROM clients WHERE phone = ?
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

    public boolean changePassword(String email, String passHex, UserRole role) throws DaoException {
        boolean match = false;
        String queryType = role.equals(UserRole.DRIVER) ? UPDATE_PASSWORD_DRIVER : UPDATE_PASSWORD_CLIENT;
        //UPDATE drivers SET password = ? WHERE email = ?
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setString(1, passHex);
            preparedStatement.setString(2, email);
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.INFO, "Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    public boolean updateUserRate(User user) throws DaoException {
        boolean match = false;
        String queryType = user.getRole().equals(UserRole.DRIVER) ? UPDATE_RATE_DRIVER : UPDATE_RATE_CLIENT;
        int id = user.getId();
        double rate = user.getRate();
        //UPDATE **** SET rate = ? WHERE id = ?
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setDouble(1, rate);
            preparedStatement.setInt(2, id);
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.INFO, "Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    public boolean updateUserIncome(User user, double income) throws DaoException {
        boolean match = false;
        String queryType = user.getRole().equals(UserRole.DRIVER) ? UPDATE_INCOME_DRIVER : UPDATE_INCOME_CLIENT;
        int id = user.getId();
        //"UPDATE clients SET payment_sum = payment_sum + ? WHERE id = ?";
        //"UPDATE drivers SET income_sum = income_sum + ? WHERE id = ?"
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setDouble(1, income);
            preparedStatement.setInt(2, id);
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.INFO, "Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }
}
