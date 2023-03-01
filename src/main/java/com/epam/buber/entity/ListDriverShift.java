package com.epam.buber.entity;

import com.epam.buber.entity.parameter.CarClass;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ListDriverShift {
    private static Logger logger = LogManager.getLogger();
    private static ListDriverShift repository;
    private static Lock locker = new ReentrantLock();

    //    private List<DriverShift> freeListDriver = new ArrayList<>();
    private List<DriverShift> orderedListDriver = new ArrayList<>();
    private Map<String, DriverShift> orderedMapDriver = new HashMap<>();

    private BlockingQueue<DriverShift> freeDrivers = new LinkedBlockingQueue<>();

    private ListDriverShift() {

    }

    public static ListDriverShift getInstance() {
        if (repository == null) {
            try {
                locker.lock();
                if (repository == null) {
                    repository = new ListDriverShift();
                }
            } finally {
                locker.unlock();
            }
        }
        return repository;
    }

    public int getCountFreeDriver(CarClass carClass) {
        List<DriverShift> list = freeDrivers.stream().filter(o -> o.getCar().getCarClass().equals(carClass)).toList();
        return list.size();
    }

    public void addQueueShift(DriverShift driverShift) {
        boolean result = freeDrivers.add(driverShift);
        if (result) {
            logger.log(Level.INFO, "Driver shift was successfully added");
        } else {
            logger.log(Level.INFO, "Driver shift was NOT added");
        }
    }

    public boolean removeQueueShift(int driverId) {
        List<DriverShift> list = freeDrivers.stream().filter(o -> o.getDriver().getId() == driverId).toList();
        DriverShift shift = list.get(0);
        boolean result = freeDrivers.remove(shift);
        return result;
    }

    public DriverShift getQueueShift(CarClass carClass) {
        DriverShift shift;
        List<DriverShift> list = freeDrivers.stream().filter(o -> o.getCar().getCarClass().equals(carClass)).toList();

        shift = list.get(0);
        freeDrivers.remove(shift);
//            shift.setCurrentOrderId(orderId);
        orderedListDriver.add(shift);
//            logger.log(Level.ERROR, "Get driver shift exception: {}", e.getMessage());

        return shift;
    }

    public boolean checkFreeQueue(int driverId) {
        boolean match;
        if (freeDrivers.stream().anyMatch(s -> s.getDriver().getId() == driverId)) {
            match = true;
        } else {
            match = false;
        }
        return match;
    }

    public boolean checkOrderedListByDriverId(int driverId) {
        boolean match;
        if (orderedListDriver.stream().anyMatch(s -> s.getDriver().getId() == driverId)) {
            match = true;
        } else {
            match = false;
        }
        return match;
    }

    public boolean checkOrderedByClientId(int clientId) {
        boolean match;
        if (orderedListDriver.stream().anyMatch(s -> s.getCurrentOrder().getClient().getId() == clientId)) {
            match = true;
        } else {
            match = false;
        }
        return match;
    }


    public void releaseQueueShift(int orderId) {
        DriverShift shift = getShiftByOrder(orderId);
        orderedListDriver.remove(shift);
        try {
            shift.setCurrentOrder(new Order(-1));
            freeDrivers.put(shift);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Release driver shift exception: {}", e.getMessage());
        }
    }

    private DriverShift getShiftByOrder(int orderId) {
        List<DriverShift> list = orderedListDriver.stream().filter(o -> o.getCurrentOrder().getIdOrder() == orderId).toList();
        DriverShift shift = list.size() != 0 ? list.get(0) : new DriverShift();
        return shift;
    }

    public DriverShift getShiftQueueByDriverId(int driverId) {
        List<DriverShift> list = freeDrivers.stream().filter(o -> o.getDriver().getId() == driverId).toList();
        DriverShift shift = list.size() != 0 ? list.get(0) : new DriverShift();
        return shift;
    }

    public DriverShift getShiftOrderedByDriverId(int driverId) {
        List<DriverShift> list = orderedListDriver.stream().filter(o -> o.getDriver().getId() == driverId).toList();
        DriverShift shift = list.size() != 0 ? list.get(0) : new DriverShift();
        return shift;
    }

    public DriverShift getShiftOrderedByClientId(int clientId) {
        List<DriverShift> list = orderedListDriver.stream().filter(o -> o.getCurrentOrder().getClient().getId()
                == clientId).toList();
        DriverShift shift = list.size() != 0 ? list.get(0) : new DriverShift();
        return shift;
    }

}
