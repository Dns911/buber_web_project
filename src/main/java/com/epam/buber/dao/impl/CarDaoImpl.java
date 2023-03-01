package com.epam.buber.dao.impl;

import com.epam.buber.connection.ConnectionPool;
import com.epam.buber.controller.info.SQLColumnName;
import com.epam.buber.dao.CarDao;
import com.epam.buber.entity.AbstractEntity;
import com.epam.buber.entity.Car;
import com.epam.buber.entity.parameter.CarClass;
import com.epam.buber.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private static Logger logger = LogManager.getLogger();
    private static final String CAR_NOT_FOUND = "*";
    private static final String ADD_CAR = "INSERT INTO cars () VALUES (?, ?, ?, ?, ?, ?)";
    private static final String FIND_CAR_BY_ID = "SELECT * FROM cars WHERE id = ?";
    private static CarDaoImpl carDaoImplInstance;

    private CarDaoImpl() {
    }

    public static CarDaoImpl getInstance() {
        if (carDaoImplInstance == null) {
            carDaoImplInstance = new CarDaoImpl();
        }
        return carDaoImplInstance;
    }

    public Car getCar(String carId) throws DaoException {
        Car car = new Car();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_CAR_BY_ID)) {
//            SELECT * FROM cars WHERE id = ?
            preparedStatement.setString(1, carId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    car.setIdCar(resultSet.getString(SQLColumnName.ID));
                    car.setModel(resultSet.getString(SQLColumnName.MODEL));
                    car.setCarClass(CarClass.define(resultSet.getString(SQLColumnName.CAR_CLASS)));
                    car.setYearIssue(Year.of(resultSet.getInt(SQLColumnName.YEAR_ISSUE)));
                    car.setColor(resultSet.getString(SQLColumnName.COLOR));
                    car.setOwner(resultSet.getString(SQLColumnName.OWNER));
                    resultSet.close();
                } else {
                    car.setIdCar(CAR_NOT_FOUND);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return car;
    }

    @Override
    public boolean insert(AbstractEntity abstractEntity) throws DaoException {
        boolean match = false;
        Car car = (Car) abstractEntity;
//        INSERT INTO cars () VALUES (?, ?, ?, ?, ?, ?)
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_CAR)) {
            preparedStatement.setString(1, car.getId());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getCarClass().toString());
            preparedStatement.setString(4, car.getYearIssue().toString());
            preparedStatement.setString(5, car.getColor());
            preparedStatement.setString(6, car.getOwner());
            int countRows = preparedStatement.executeUpdate();

            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.INFO, "Add car. Count changed rows = {}", countRows);
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
        return null;
    }

    @Override
    public AbstractEntity update(AbstractEntity abstractEntity) throws DaoException {
        return null;
    }
}
