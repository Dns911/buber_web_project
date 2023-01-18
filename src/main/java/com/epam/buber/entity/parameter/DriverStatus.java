package com.epam.buber.entity.parameter;

public enum DriverStatus{
    ACTIVE("active"),
    APPLICANT("applicant"),
    BANNED("banned");

    private String value;

    DriverStatus(String active) {
        this.value = active;
    }

    public String getValue() {
        return value;
    }
}
