package com.epam.buber.entity;

import com.epam.buber.entity.types.CarClass;
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
    private List<ShiftDriver> orderedListDriver = new ArrayList<>();
    private BlockingQueue<ShiftDriver> freeDrivers = new LinkedBlockingQueue<>();

    private ListDriverShift() {
        super();
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
        List<ShiftDriver> list = freeDrivers.stream().filter(o -> o.getCar().getCarClass().equals(carClass)).toList();
        return list.size();
    }

    public void addQueueShift(ShiftDriver shiftDriver) {
        boolean result = freeDrivers.add(shiftDriver);
        if (result) {
            logger.log(Level.INFO, "Driver shift was successfully added");
        } else {
            logger.log(Level.INFO, "Driver shift was NOT added");
        }
    }

    public boolean removeQueueShift(int driverId) {
        List<ShiftDriver> list = freeDrivers.stream().filter(o -> o.getDriver().getId() == driverId).toList();
        ShiftDriver shift = !list.isEmpty() ? list.get(0) : new ShiftDriver();
        return freeDrivers.remove(shift);
    }

    public ShiftDriver getQueueShift(CarClass carClass) {
        List<ShiftDriver> list = freeDrivers.stream().filter(o -> o.getCar().getCarClass().equals(carClass)).toList();
        ShiftDriver shift = !list.isEmpty() ? list.get(0) : new ShiftDriver();
        freeDrivers.remove(shift);
        orderedListDriver.add(shift);
        return shift;
    }

    public boolean checkFreeQueue(int driverId) {
        boolean match;
        match = freeDrivers.stream().anyMatch(s -> s.getDriver().getId() == driverId);
        return match;
    }

    public boolean checkOrderedListByDriverId(int driverId) {
        boolean match;
        match = orderedListDriver.stream().anyMatch(s -> s.getDriver().getId() == driverId);
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
        ShiftDriver shift = getShiftByOrder(orderId);
        orderedListDriver.remove(shift);
        try {
            shift.setCurrentOrder(new Order(-1));
            freeDrivers.put(shift);
        } catch (InterruptedException e) {

            logger.log(Level.ERROR, "Release driver shift exception: {}", e.getMessage());
        }
    }

    private ShiftDriver getShiftByOrder(int orderId) {
        List<ShiftDriver> list = orderedListDriver.stream().filter(o -> o.getCurrentOrder().getIdOrder() == orderId).toList();
        return list.size() != 0 ? list.get(0) : new ShiftDriver();
    }

    public ShiftDriver getShiftQueueByDriverId(int driverId) {
        List<ShiftDriver> list = freeDrivers.stream().filter(o -> o.getDriver().getId() == driverId).toList();
        return list.size() != 0 ? list.get(0) : new ShiftDriver();
    }

    public ShiftDriver getShiftOrderedByDriverId(int driverId) {
        List<ShiftDriver> list = orderedListDriver.stream().filter(o -> o.getDriver().getId() == driverId).toList();
        return list.size() != 0 ? list.get(0) : new ShiftDriver();
    }

    public ShiftDriver getShiftOrderedByClientId(int clientId) {
        List<ShiftDriver> list = orderedListDriver.stream().filter(o -> o.getCurrentOrder().getClient().getId()
                == clientId).toList();
        return list.size() != 0 ? list.get(0) : new ShiftDriver();
    }
}
