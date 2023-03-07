package com.epam.buber.entity;

import java.sql.Time;
import java.sql.Date;
import java.util.Objects;

public class ShiftDriver extends AbstractEntity {
    private long idShift;
    private Driver driver;
    private Car car;
    private Date date;
    private Time startTime; //SQL Time
    private Time finishTime; //SQL Time
    private double income; //0.00 BYN
    private double length; //000.0 km
    private Order currentOrder;

    public ShiftDriver() {
        super();
    }

    public long getIdShift() {
        return idShift;
    }

    public void setIdShift(long idShiftDriver) {
        this.idShift = idShiftDriver;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Time finishTime) {
        this.finishTime = finishTime;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public void addIncome(double income) {
        this.income += income;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void addLength(double length) {
        this.length += length;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShiftDriver that)) return false;
        return getIdShift() == that.getIdShift() && Double.compare(that.getIncome(), getIncome()) == 0 &&
                Double.compare(that.getLength(), getLength()) == 0 && Objects.equals(getDriver(), that.getDriver()) &&
                Objects.equals(getCar(), that.getCar()) && Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getStartTime(), that.getStartTime()) &&
                Objects.equals(getFinishTime(), that.getFinishTime()) &&
                Objects.equals(getCurrentOrder(), that.getCurrentOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdShift(), getDriver(), getCar(), getDate(), getStartTime(),
                getFinishTime(), getIncome(), getLength(), getCurrentOrder());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShiftDriver{");
        sb.append(" idShiftDriver=").append(idShift);
        sb.append("\ndriver=").append(driver);
        sb.append("\ncar=").append(car);
        sb.append("\ndate=").append(date);
        sb.append("\nstartTime=").append(startTime);
        sb.append("\nfinishTime=").append(finishTime);
        sb.append("\nincome=").append(income);
        sb.append("\nlength=").append(length);
        sb.append("\ncurrentOrderId=").append(currentOrder);
        sb.append('}');
        return sb.toString();
    }
}
