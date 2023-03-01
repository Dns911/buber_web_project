package com.epam.buber.service.impl;

import com.epam.buber.controller.info.AttrValue;
import com.epam.buber.controller.info.RequestParameterName;
import com.epam.buber.dao.impl.OrderDaoImpl;
import com.epam.buber.dao.impl.UserDaoImpl;
import com.epam.buber.entity.Client;
import com.epam.buber.entity.Driver;
import com.epam.buber.entity.Order;
import com.epam.buber.entity.User;
import com.epam.buber.entity.parameter.CarClass;
import com.epam.buber.entity.parameter.UserRole;
import com.epam.buber.exception.DaoException;
import com.epam.buber.exception.ServiceException;
import com.epam.buber.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.Properties;

public class OrderServiceImpl implements OrderService {
    private static Logger logger = LogManager.getLogger();
    private static final String PRICE_PROPERTIES = "price/price.properties";
    private static final String DECIMAL_FORMAT = "#.##";
    private static final String MIN_COST_ORDER = "min.cost.order";
    private static OrderServiceImpl orderServiceImplInstance;

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        if (orderServiceImplInstance == null) {
            orderServiceImplInstance = new OrderServiceImpl();
        }
        return orderServiceImplInstance;
    }

    public String getCost(String distance, CarClass classAuto) {
        Double dist = Double.valueOf(distance);
        Properties prop = CommonServiceImpl.getInstance().readProperties(PRICE_PROPERTIES);
        double koeffPrice = Double.valueOf(prop.getProperty(classAuto.getCarClass()));
        double cost = Double.valueOf(prop.getProperty(MIN_COST_ORDER)) + koeffPrice * dist;
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        String result = decimalFormat.format(cost);
        return result;
    }

    public void startOrder(Order order) throws ServiceException {
        CommonServiceImpl commonService = CommonServiceImpl.getInstance();
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        Time startTime = new Time(currentTime);
        order.setDate(date);
        order.setStartTime(startTime);
        OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
        try {
            if (orderDao.addToBD(order)) {
                logger.log(Level.INFO, "order added success");
                int id = orderDao.getIdOrder(order);
                logger.log(Level.INFO, "id = " + id);
                order.setIdOrder(id);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void orderInfo(HttpServletRequest request, Order order, UserRole role) {
        request.setAttribute(RequestParameterName.ID, order.getIdOrder());
        request.setAttribute(RequestParameterName.DATE, order.getDate());
        request.setAttribute(RequestParameterName.START_TIME, order.getStartTime());
        request.setAttribute(RequestParameterName.FINISH_POINT, order.getFinishPoint());
        request.setAttribute(RequestParameterName.COST, order.getCost());
        String status = order.getStatus() ? AttrValue.ORDER_STATUS_1 : AttrValue.ORDER_STATUS_0;
        request.setAttribute(RequestParameterName.STATUS, status);
        if (role.equals(UserRole.CLIENT)) {
            request.setAttribute(RequestParameterName.DR_ID, order.getDriverShift().getDriver().getId());
            request.setAttribute(RequestParameterName.DR_NAME, order.getDriverShift().getDriver().getName());
            request.setAttribute(RequestParameterName.CAR_INFO, order.getDriverShift().getCar().getModel());
        } else {
            request.setAttribute(RequestParameterName.CL_NAME, order.getClient().getName());
            request.setAttribute(RequestParameterName.PHONE_NUM, order.getClient().getPhoneNum());
        }
    }

    public void setFromClientRate(int driverId, int orderId, int rateFromClient) throws ServiceException {
        OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        Order order = new Order();
        order.setIdOrder(orderId);
        order.setRateFromClient(rateFromClient);
        try {
            if (orderDao.setRate(order, UserRole.CLIENT)) {
                Driver driver = new Driver();
                driver.setId(driverId);
                driver.setRole(UserRole.DRIVER);
                orderDao.countUserRate(driver);
                userDao.updateUserRate(driver);
            }
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public void finishOrder(Order order) throws ServiceException {
        OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            if (orderDao.setRate(order, UserRole.DRIVER)) {
                Client client = order.getClient();
                Driver driver = order.getDriverShift().getDriver();
                orderDao.countUserRate(client);
                userDao.updateUserRate(client);
                userDao.updateUserIncome(client, order.getCost());
                userDao.updateUserIncome(driver, order.getCost());
            } else {
                logger.log(Level.INFO, "Order NOT finished!");
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
