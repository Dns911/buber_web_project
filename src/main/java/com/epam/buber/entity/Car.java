package com.epam.buber.entity;

import com.epam.buber.entity.parameter.CarBody;

import java.time.Year;
import java.util.Objects;

public class Car extends AbstractEntity{
    private String idCar;
    private String model;
    private CarBody carBody;
    private Year yearIssue;
    private String color;
    private String owner;

    public Car() {
    }

    public Car(String idCar, String model, CarBody carBody, Year yearIssue, String color, String owner) {
        this.idCar = idCar;
        this.model = model;
        this.carBody = carBody;
        this.yearIssue = yearIssue;
        this.color = color;
        this.owner = owner;
    }

    public String getIdCar() {
        return idCar;
    }

    public void setIdCar(String idCar) {
        this.idCar = idCar;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody carBody) {
        this.carBody = carBody;
    }

    public Year getYearIssue() {
        return yearIssue;
    }

    public void setYearIssue(Year yearIssue) {
        this.yearIssue = yearIssue;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return Objects.equals(getIdCar(),
                car.getIdCar()) && Objects.equals(getModel(),
                car.getModel()) && Objects.equals(getCarBody(),
                car.getCarBody()) && Objects.equals(getYearIssue(),
                car.getYearIssue()) && Objects.equals(getColor(),
                car.getColor()) && Objects.equals(getOwner(),
                car.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdCar(), getModel(), getCarBody(), getYearIssue(), getColor(), getOwner());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append(" idCar='").append(idCar).append('\'');
        sb.append("\n model='").append(model).append('\'');
        sb.append("\n carBody='").append(carBody).append('\'');
        sb.append("\n yearIssue=").append(yearIssue);
        sb.append("\n color='").append(color).append('\'');
        sb.append("\n owner='").append(owner).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
