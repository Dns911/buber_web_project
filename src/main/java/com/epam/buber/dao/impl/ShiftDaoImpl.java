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
    private static final String ADD_DRIVER_SHIFT = "INSERT INTO drivers_shift (driver_id, car_id, date, time_start, time_finish, income, length_km) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_SHIFTS_DRIVER_ID = "SELECT * FROM drivers_shift WHERE driver_id = ?";
    @Override
    public boolean insert(AbstractEntity abstractEntity) throws DaoException {
        boolean match = false;
        DriverShift driverShift = (DriverShift) abstractEntity;
//        INSERT INTO drivers_shift (driver_id, car_id, date, time_start, time_finish, income, length_km) VALUES ()
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_DRIVER_SHIFT)) {
            preparedStatement.setInt(1, driverShift.getIdDriver());
            preparedStatement.setString(2, driverShift.getIdCar());
            preparedStatement.setDate(3, driverShift.getDate());
            preparedStatement.setTime(4, driverShift.getStartTime());
            preparedStatement.setTime(5, driverShift.getFinishTime());
            preparedStatement.setDouble(6, driverShift.getIncome());
            preparedStatement.setDouble(7, driverShift.getLength());

            int countRows = preparedStatement.executeUpdate();

            if (countRows == 1){
                match = true;
            } else {
                logger.log(Level.INFO,"Add driver shift. Count changed rows = {}", countRows);
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
//            SELECT * FROM drivers_shift WHERE driver_id = ?
            preparedStatement.setInt(1, driver.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    DriverShift driverShift = new DriverShift();
                    driverShift.setIdShiftDriver(resultSet.getInt(SQLColumnName.ID));
                    driverShift.setIdDriver(resultSet.getInt(SQLColumnName.DRIVER_ID));
                    driverShift.setIdCar(resultSet.getString(SQLColumnName.CAR_ID));
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


}
