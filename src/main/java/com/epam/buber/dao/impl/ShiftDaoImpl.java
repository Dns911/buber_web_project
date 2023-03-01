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
    private static Logger logger = LogManager.getLogger();
    private static final String ADD_DRIVER_SHIFT = "INSERT INTO drivers_shifts (driver_id, car_id, date, time_start) VALUES (?, ?, ?, ?)";
    private static final String FIND_SHIFTS_DRIVER_ID = "SELECT * FROM drivers_shifts WHERE driver_id = ?";
    private static final String GET_SHIFTS_DRIVER_ID = "SELECT id FROM drivers_shifts WHERE driver_id = ?";
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

    @Override
    public boolean insert(AbstractEntity abstractEntity) throws DaoException {
        boolean match = false;
        DriverShift driverShift = (DriverShift) abstractEntity;
//        INSERT INTO drivers_shifts (driver_id, car_id, date, time_start) VALUES ()
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_DRIVER_SHIFT)) {
//            preparedStatement.setLong(1, driverShift.getIdShift());
            preparedStatement.setInt(1, driverShift.getDriver().getId());
            preparedStatement.setString(2, driverShift.getCar().getId());
            preparedStatement.setDate(3, driverShift.getDate());
            preparedStatement.setTime(4, driverShift.getStartTime());
//            preparedStatement.setTime(6, driverShift.getFinishTime());
//            preparedStatement.setDouble(7, driverShift.getIncome());
//            preparedStatement.setDouble(8, driverShift.getLength());

            int countRows = preparedStatement.executeUpdate();

            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.INFO, "Add driver shift. Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    @Override
    public boolean delete(AbstractEntity abstractEntity) throws DaoException {
        return false;
    }

    @Override
    public List findAll(AbstractEntity abstractEntity) throws DaoException {
        Driver driver = (Driver) abstractEntity;
        List<DriverShift> list = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_SHIFTS_DRIVER_ID)) {
//            SELECT * FROM drivers_shifts WHERE driver_id = ?
            preparedStatement.setInt(1, driver.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    DriverShift driverShift = new DriverShift();
                    driverShift.setIdShift(resultSet.getLong(SQLColumnName.ID));
//                    driverShift.setDriver(resultSet.getInt(SQLColumnName.DRIVER_ID));
//
//                    driverShift.setCar(resultSet.getString(SQLColumnName.CAR_ID));

                    driverShift.setDate(resultSet.getDate(SQLColumnName.DATE));
                    driverShift.setStartTime(resultSet.getTime(SQLColumnName.TIME_START));
                    driverShift.setFinishTime(resultSet.getTime(SQLColumnName.TIME_FINISH));
                    driverShift.setIncome(resultSet.getDouble(SQLColumnName.INCOME));
                    driverShift.setLength(resultSet.getDouble(SQLColumnName.LENGTH_KM));
                    list.add(driverShift);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return list;
    }

    @Override
    public AbstractEntity update(AbstractEntity abstractEntity) throws DaoException {
        return null;
    }

    public long getShiftId(DriverShift shift) throws DaoException {
        long shiftId = 0L;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_SHIFT_ID)) {
//            SELECT id FROM drivers_shifts WHERE driver_id = ? AND car_id = ? AND date = ? AND time_start = ?
            preparedStatement.setInt(1, shift.getDriver().getId());
            preparedStatement.setString(2, shift.getCar().getId());
            preparedStatement.setDate(3, shift.getDate());
            preparedStatement.setTime(4, shift.getStartTime());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                logger.log(Level.INFO, "get1 id =" + resultSet.getInt(SQLColumnName.ID));
                if (resultSet.next()) {
                    logger.log(Level.INFO, "get2 id =" + resultSet.getLong(SQLColumnName.ID));
                    shiftId = resultSet.getLong(SQLColumnName.ID);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return shiftId;
    }


    public boolean updateFinish(AbstractEntity abstractEntity) throws DaoException {
        boolean match = false;
        DriverShift driverShift = (DriverShift) abstractEntity;
//        UPDATE drivers_shifts SET time_finish = ?, income = ?, length_km = ? WHERE id = ?
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FINISH)) {
            preparedStatement.setTime(1, driverShift.getFinishTime());
            preparedStatement.setDouble(2, driverShift.getIncome());
            preparedStatement.setDouble(3, driverShift.getLength());
            preparedStatement.setLong(4, driverShift.getIdShift());

            int countRows = preparedStatement.executeUpdate();

            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.INFO, "Update driver shift. Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }
}
