package com.epam.buber.dao.impl;

import com.epam.buber.connection.ConnectionPool;
import com.epam.buber.controller.info.SQLColumnName;
import com.epam.buber.dao.OrderDao;
import com.epam.buber.entity.Order;
import com.epam.buber.entity.User;
import com.epam.buber.entity.types.UserRole;
import com.epam.buber.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private static Logger logger = LogManager.getLogger();
    private static OrderDaoImpl orderDaoImplInstance;
    private static final String ADD_ORDER = "INSERT INTO orders (clients_id, drivers_shifts_id, date, start_time, " +
            " start_point, finish_point, distance, cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String ORDER_BY_ID = "SELECT * FROM orders WHERE id = ?";
    private static final String GET_ORDER_ID = "SELECT id FROM orders WHERE drivers_shifts_id = ? AND clients_id = ? AND date = ? AND start_time = ?";
    private static final String GET_DRIVER_RATE = "SELECT rate_client FROM orders WHERE drivers_shifts_id =" +
            " ANY (select drivers_shifts.id from drivers_shifts where driver_id = ?)";
    private static final String GET_CLIENT_RATE = "SELECT rate_driver FROM orders WHERE clients_id = ?";
    private static final String UPDATE_CL_RATE = "UPDATE orders SET rate_client = ? WHERE id = ?";
    private static final String UPDATE_DR_RATE_STATUS = "UPDATE orders SET rate_driver = ?, end_order = ?  WHERE id = ?";

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        if (orderDaoImplInstance == null) {
            orderDaoImplInstance = new OrderDaoImpl();
        }
        return orderDaoImplInstance;
    }

    public int getIdOrder(Order order) throws DaoException {
        int orderId = -1;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_ID)) {
            preparedStatement.setLong(1, order.getDriverShift().getIdShift());
            preparedStatement.setInt(2, order.getClient().getId());
            preparedStatement.setDate(3, order.getDate());
            preparedStatement.setTime(4, order.getStartTime());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderId = resultSet.getInt(SQLColumnName.ID);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return orderId;
    }

    public boolean setRate(Order order, UserRole role) throws DaoException {
        boolean match = false;
        String queryType = role.equals(UserRole.CLIENT) ? UPDATE_CL_RATE : UPDATE_DR_RATE_STATUS;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            if (role.equals(UserRole.CLIENT)) {
                preparedStatement.setDouble(1, order.getRateFromClient());
                preparedStatement.setInt(2, order.getIdOrder());
            } else {
                preparedStatement.setDouble(1, order.getRateFromDriver());
                preparedStatement.setBoolean(2, true);
                preparedStatement.setInt(3, order.getIdOrder());
            }
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.WARN, "Update order: Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }

    public void getCurrentRate(User user) throws DaoException {
        double rate;
        String queryType = user.getRole().equals(UserRole.CLIENT) ? GET_CLIENT_RATE : GET_DRIVER_RATE;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryType)) {
            preparedStatement.setInt(1, user.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                double sumRate = 5.0;
                int count = 1;
                while (resultSet.next()) {
                    if (Double.valueOf(resultSet.getString(1)) > 0) {
                        sumRate = sumRate + Double.valueOf(resultSet.getString(1));
                        count++;
                    }
                }
                rate = sumRate / count;
                user.setRate(rate);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Order find(Order order) throws DaoException {
        int id = order.getIdOrder();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ORDER_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    order.setIdOrder(resultSet.getInt(SQLColumnName.ID));
                    order.setDate(resultSet.getDate(SQLColumnName.DATE));
                    order.setStartTime(resultSet.getTime(SQLColumnName.START_TIME));
                    order.setFinishTime(resultSet.getTime(SQLColumnName.FINISH_TIME));
                    order.setStartPoint(resultSet.getString(SQLColumnName.START_POINT));
                    order.setFinishPoint(resultSet.getString(SQLColumnName.FINISH_POINT));
                    order.setDistance(resultSet.getDouble(SQLColumnName.DISTANCE));
                    order.setCost(resultSet.getDouble(SQLColumnName.COST));
                    order.setRateFromClient(resultSet.getDouble(SQLColumnName.RATE_CLIENT));
                    order.setRateFromDriver(resultSet.getDouble(SQLColumnName.RATE_DRIVER));
                    order.setStatus(resultSet.getBoolean(SQLColumnName.END_ORDER));
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return order;
    }

    @Override
    public boolean delete(Order order) throws DaoException {
        return false;
    }

    @Override
    public boolean insert(Order order) throws DaoException {
        boolean match = false;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER)) {
            preparedStatement.setInt(1, order.getClient().getId());
            preparedStatement.setLong(2, order.getDriverShift().getIdShift());
            preparedStatement.setDate(3, order.getDate());
            preparedStatement.setTime(4, order.getStartTime());
            preparedStatement.setString(5, order.getStartPoint());
            preparedStatement.setString(6, order.getFinishPoint());
            preparedStatement.setDouble(7, order.getDistance());
            preparedStatement.setDouble(8, order.getCost());
            int countRows = preparedStatement.executeUpdate();
            if (countRows == 1) {
                match = true;
            } else {
                logger.log(Level.WARN, "Add order: Count changed rows = {}", countRows);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return match;
    }
    @Override
    public boolean update(Order order) throws DaoException {
        return false;
    }

    @Override
    public List<Order> findAll(User user) throws DaoException {
        return null;
    }
}
