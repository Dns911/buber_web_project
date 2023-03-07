package com.epam.buber.entity.types;

import java.util.Arrays;
import java.util.List;

public enum DriverStatus{

    ACTIVE("active"),
    APPLICANT("applicant"),
    BANNED("banned");

    private final String value;

    DriverStatus(String active) {
        this.value = active;
    }

    public static DriverStatus define(String strStatus){
        List<DriverStatus> list = Arrays.stream(DriverStatus.values()).filter(o->o.toString().equals(strStatus)).toList();
        return list.get(0);
    }

    @Override
    public String toString() {
        return value;
    }
}
