package com.epam.buber.entity.parameter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public enum DriverStatus{

    ACTIVE("active"),
    APPLICANT("applicant"),
    BANNED("banned");
    private static Logger logger = LogManager.getLogger();

    private String value;

    DriverStatus(String active) {
        this.value = active;
    }

    public static DriverStatus define(String strStatus){
        List<DriverStatus> list = Arrays.stream(DriverStatus.values()).filter(o->o.getStringStatus().equals(strStatus)).toList();
        return list.get(0);
    }

    public String getStringStatus() {
        return value;
    }
}
