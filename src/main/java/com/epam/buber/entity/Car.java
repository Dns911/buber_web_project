package com.epam.buber.entity;

import com.epam.buber.entity.parameter.CarClass;

import java.time.Year;
import java.util.Objects;

public class Car extends AbstractEntity{
    private String idCar;
    private String model;
    private CarClass carClass;
    private Year yearIssue;

    private String color;
    private String owner;

    public Car() {
    }

    public String getId() {
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

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
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
        return Objects.equals(getId(),
                car.getId()) && Objects.equals(getModel(),
                car.getModel()) && Objects.equals(getCarClass(),
                car.getCarClass()) && Objects.equals(getYearIssue(),
                car.getYearIssue()) && Objects.equals(getColor(),
                car.getColor()) && Objects.equals(getOwner(),
                car.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getModel(), getCarClass(), getYearIssue(), getColor(), getOwner());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append(" idCar='").append(idCar).append('\'');
        sb.append("\n model='").append(model).append('\'');
        sb.append("\n carClass='").append(carClass).append('\'');
        sb.append("\n yearIssue=").append(yearIssue);
        sb.append("\n color='").append(color).append('\'');
        sb.append("\n owner='").append(owner).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
