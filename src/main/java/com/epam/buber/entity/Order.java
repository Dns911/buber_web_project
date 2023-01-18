package com.epam.buber.entity;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Order extends AbstractEntity{
    private int idOrder;
    private int idUser;
    private int idDriverShift;
    private Date date;
    private Time startTime; //SQL Time
    private Time finishTime; //SQL Time
    private double length; //km
    private double cost; //0.00 BYN
    private double rateFromClient; //5.0
    private double rateFromDriver; //5.0

    public Order() {
    }

    public Order(int idOrder, int idUser, int idDriverShift, Date date, Time startTime, Time finishTime, double length, double cost, double rateFromClient, double rateFromDriver) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.idDriverShift = idDriverShift;
        this.date = date;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.length = length;
        this.cost = cost;
        this.rateFromClient = rateFromClient;
        this.rateFromDriver = rateFromDriver;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDriverShift() {
        return idDriverShift;
    }

    public void setIdDriverShift(int idDriverShift) {
        this.idDriverShift = idDriverShift;
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getRateFromClient() {
        return rateFromClient;
    }

    public void setRateFromClient(double rateFromClient) {
        this.rateFromClient = rateFromClient;
    }

    public double getRateFromDriver() {
        return rateFromDriver;
    }

    public void setRateFromDriver(double rateFromDriver) {
        this.rateFromDriver = rateFromDriver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getIdOrder() == order.getIdOrder() && getIdUser() == order.getIdUser() &&
                getIdDriverShift() == order.getIdDriverShift() && Double.compare(order.getLength(),
                getLength()) == 0 && Double.compare(order.getCost(),
                getCost()) == 0 && Double.compare(order.getRateFromClient(),
                getRateFromClient()) == 0 && Double.compare(order.getRateFromDriver(),
                getRateFromDriver()) == 0 && Objects.equals(getDate(),
                order.getDate()) && Objects.equals(getStartTime(),
                order.getStartTime()) && Objects.equals(getFinishTime(),
                order.getFinishTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrder(), getIdUser(), getIdDriverShift(), getDate(), getStartTime(), getFinishTime(), getLength(), getCost(), getRateFromClient(), getRateFromDriver());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("                idOrder=").append(idOrder);
        sb.append("\n idUser=").append(idUser);
        sb.append("\n idDriverShift=").append(idDriverShift);
        sb.append("\n date=").append(date);
        sb.append("\n startTime=").append(startTime);
        sb.append("\n finishTime=").append(finishTime);
        sb.append("\n length=").append(length);
        sb.append("\n cost=").append(cost);
        sb.append("\n rateFromClient=").append(rateFromClient);
        sb.append("\n rateFromDriver=").append(rateFromDriver);
        sb.append('}');
        return sb.toString();
    }
}
