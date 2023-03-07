package com.epam.buber.dao.impl;

import com.epam.buber.connection.ConnectionPool;
import com.epam.buber.controller.info.SQLColumnName;
import com.epam.buber.dao.ShiftDao;
import com.epam.buber.entity.*;
import com.epam.buber.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShiftDaoImpl implements ShiftDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String ADD_DRIVER_SHIFT = "INSERT INTO drivers_shifts (driver_id, car_id, date, time_start) VALUES (?, ?, ?, ?)";
    private static final String FIND_SHIFTS_DRIVER_ID = "SELECT * FROM drivers_shifts WHERE driver_id = ?";
    private static final String GET_SHIFT_ID = "SELECT id FROM drivers_shifts WHERE driver_id = ? AND car_id = ? AND date = ? AND time_start = ?";
    private static final String UPDATE_FINISH = "UPDATE drivers_shifts SET time_finish = ?, income = ?, length_km = ? WHERE id = ?";
    private static ShiftDaoImpl shiftDaoImplInstance;

    private ShiftDaoImpl() {
    }

    public static ShiftDaoImpl getInstance() {
        if (shiftDaoImplInstance == null) {
            shiftDaoImplInstance = new ShiftDaoImpl();
        }
        return shiftDaoImplInstance;
    }

    public List<ShiftDriver> findAll(Driver driver) throws DaoException {
        List<ShiftDriver> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SHIFTS_DRIVER_ID)) {
            preparedStatement.setInt(1, driver.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ShiftDriver shiftDriver = new ShiftDriver();
                    shiftDriver.setIdShift(resultSet.getLong(SQLColumnName.ID));
                    shiftDriver.setDate(resultSet.getDate(SQLColumnName.DATE));
                    shiftDriver.setStartTime(resultSet.getTime(SQLColumnName.TIME_START));
                    shiftDriver.setFinishTime(resultSet.getTime(SQLColumnName.TIME_FINISH));
                    shiftDriver.setIncome(resultSet.getDouble(SQLColumnName.INCOME));
                    shiftDriver.setLength(resultSet.getDouble(SQLColumnName.LENGTH_KM));
                    list.add(shiftDriver);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return list;
    }

    @Override
    public boolean insert(ShiftDriver shiftDriver) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_DRIVER_SHIFT)) {
            preparedStatement.setInt(1, shiftDriver.getDriver().getId());
            preparedStatement.setString(2, shiftDriver.getCar().getId());
            preparedStatement.setDate(3, shiftDriver.getDate());
            preparedStatement.setTime(4, shiftDriver.getStartTime());
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.WARN, "Add driver shift. Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    @Override
    public ShiftDriver find(ShiftDriver shift) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SHIFT_ID)) {
//            SELECT id FROM drivers_shifts WHERE driver_id = ? AND car_id = ? AND date = ? AND time_start = ?
            preparedStatement.setInt(1, shift.getDriver().getId());
            preparedStatement.setString(2, shift.getCar().getId());
            preparedStatement.setDate(3, shift.getDate());
            preparedStatement.setTime(4, shift.getStartTime());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long shiftId = resultSet.getLong(SQLColumnName.ID);
                    shift.setIdShift(shiftId);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return shift;
    }

    @Override
    public boolean update(ShiftDriver shiftDriver) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FINISH)) {
            preparedStatement.setTime(1, shiftDriver.getFinishTime());
            preparedStatement.setDouble(2, shiftDriver.getIncome());
            preparedStatement.setDouble(3, shiftDriver.getLength());
            preparedStatement.setLong(4, shiftDriver.getIdShift());
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.WARN, "Update driver shift. Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    @Override
    public boolean delete(ShiftDriver shiftDriver) throws DaoException {
        return false;
    }
}
