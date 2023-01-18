package com.epam.buber.entity.parameter;

public enum CarBody {
    HATCHBACK("hatchback"),
    SEDAN("sedan"),
    STATION_WAGON("station wagon"),
    MINIVAN("minivan");

    private String carBody;

    CarBody(String carBody) {
        this.carBody = carBody;
    }

    public String getCarBody() {
        return carBody;
    }
}
