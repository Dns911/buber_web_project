package com.epam.buber.entity;

import com.epam.buber.entity.AbstractEntity;

import java.sql.Time;
import java.sql.Date;
import java.util.Objects;

public class DriverShift extends AbstractEntity {
    private int idShiftDriver;
    private int idDriver;
    private String idCar;
    private Date date;
    private Time startTime; //SQL Time
    private Time finishTime; //SQL Time
    private double income; //0.00 BYN
    private double length; //000.0 km

    public DriverShift() {
    }

    public DriverShift(int idShiftDriver, int idDriver, String idCar, Date date, Time startTime, Time finishTime, double income, double length) {
        this.idShiftDriver = idShiftDriver;
        this.idDriver = idDriver;
        this.idCar = idCar;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.income = income;
        this.length = length;
    }

    public int getIdShiftDriver() {
        return idShiftDriver;
    }

    public void setIdShiftDriver(int idShiftDriver) {
        this.idShiftDriver = idShiftDriver;
    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    public String getIdCar() {
        return idCar;
    }

    public void setIdCar(String idCar) {
        this.idCar = idCar;
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DriverShift)) return false;
        DriverShift that = (DriverShift) o;
        return getIdShiftDriver() == that.getIdShiftDriver() && getIdDriver() == that.getIdDriver() && Double.compare(that.getIncome(),
                getIncome()) == 0 && Double.compare(that.getLength(),
                getLength()) == 0 && Objects.equals(getIdCar(),
                that.getIdCar()) && Objects.equals(getDate(),
                that.getDate()) && Objects.equals(getStartTime(),
                that.getStartTime()) && Objects.equals(getFinishTime(),
                that.getFinishTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdShiftDriver(), getIdDriver(), getIdCar(), getDate(), getStartTime(), getFinishTime(), getIncome(), getLength());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DriverShift{");
        sb.append(" idShiftDriver=").append(idShiftDriver);
        sb.append("\n idDriver=").append(idDriver);
        sb.append("\n idCar='").append(idCar).append('\'');
        sb.append("\n date=").append(date);
        sb.append("\n startTime=").append(startTime);
        sb.append("\n finishTime=").append(finishTime);
        sb.append("\n income=").append(income);
        sb.append("\n length=").append(length);
        sb.append('}');
        return sb.toString();
    }
}
