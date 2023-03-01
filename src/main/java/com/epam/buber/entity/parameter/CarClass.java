package com.epam.buber.entity.parameter;

import com.epam.buber.entity.DriverShift;

import java.util.Arrays;
import java.util.List;

public enum CarClass {
    ECONOMY("economy"),
    STANDARD("standard"),
    BUSINESS("business"),
    MINIVAN("minivan");
    private String carClass;

    CarClass(String carClassStr) {
        this.carClass = carClassStr;
    }

    public String getCarClass() {
        return carClass;
    }

    public static CarClass define(String strClass) {
        List<CarClass> list = Arrays.stream(CarClass.values()).filter(o -> o.getCarClass().equals(strClass)).toList();
        CarClass carClass = list.size() != 0 ? list.get(0) : CarClass.ECONOMY;
        return carClass;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CarClass{");
        sb.append("                carClass='").append(carClass).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
