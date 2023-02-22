package com.epam.buber.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RepositoryDriverShift {
    private static Logger logger = LogManager.getLogger();
    private static RepositoryDriverShift repository;
    private static Lock locker = new ReentrantLock();

//    private List<DriverShift> freeListDriver = new ArrayList<>();
    private List<DriverShift> orderedListDriver = new ArrayList<>();

    private BlockingQueue<DriverShift> freeDrivers = new LinkedBlockingQueue<>();

    private RepositoryDriverShift() {

    }

    public static RepositoryDriverShift getInstance() {
        if (repository == null) {
            try {
                locker.lock();
                if (repository == null) {
                    repository = new RepositoryDriverShift();
                }
            } finally {
                locker.unlock();
            }
        }
        return repository;
    }

    public void addDriverShift(DriverShift driverShift){
        freeDrivers.add(driverShift);
    }

    public boolean removeDriverShift(DriverShift driverShift){
        boolean result = freeDrivers.remove(driverShift);
        return result;
    }

    public DriverShift getDriverShift(){
        DriverShift shift = new DriverShift();
        try {
            shift = freeDrivers.take();
            orderedListDriver.add(shift);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Get driver shift exception: {}", e.getMessage());
        }
        return shift;
    }

    public void releaseDriverShift(DriverShift shift){
        orderedListDriver.remove(shift);
        try {
            freeDrivers.put(shift);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Release driver shift exception: {}", e.getMessage());
        }
    }

    public boolean checkFreeQueue(Driver driver){
        boolean match;
        int driverId = driver.getId();
        if (freeDrivers.stream().anyMatch(s -> s.getIdDriver() == driverId)){
            match = true;
        } else {
            match = false;
        }
        return match;
    }

    public boolean checkOrderedList(Driver driver){
        boolean match;
        int driverId = driver.getId();
        if (orderedListDriver.stream().anyMatch(s -> s.getIdDriver() == driverId)){
            match = true;
        } else {
            match = false;
        }
        return match;
    }


}
