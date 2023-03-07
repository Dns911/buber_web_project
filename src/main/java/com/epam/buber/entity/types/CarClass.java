package com.epam.buber.entity.types;

import java.util.Arrays;
import java.util.List;

public enum CarClass {
    ECONOMY("economy"),
    STANDARD("standard"),
    BUSINESS("business"),
    MINIVAN("minivan");
    private final String classCar;

    CarClass(String classCar) {
        this.classCar = classCar;
    }

    public String getClassCar() {
        return classCar;
    }

    public static CarClass define(String strClass) {
        List<CarClass> list = Arrays.stream(CarClass.values()).filter(o -> o.getClassCar().equals(strClass)).toList();
        return !list.isEmpty() ? list.get(0) : CarClass.ECONOMY;
    }

    @Override
    public String toString() {
        return this.classCar;
    }
}
