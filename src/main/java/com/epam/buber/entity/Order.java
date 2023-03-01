package com.epam.buber.entity;

import java.sql.Time;
import java.sql.Date;
import java.util.Objects;

public class Order extends AbstractEntity{
    public static final int DEFAULT_ID = -1;
    private int idOrder;
    private Client client;
    private DriverShift driverShift;
    private Date date;
    private Time startTime; //SQL Time
    private Time finishTime; //SQL Time
    private String startPoint;
    private String finishPoint;
    private double distance; //km
    private double cost; //0.00 BYN
    private double rateFromClient; //5.0
    private double rateFromDriver; //5.0

    private boolean status;

    public Order() {
    }

    public Order(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public DriverShift getDriverShift() {
        return driverShift;
    }

    public void setDriverShift(DriverShift driverShift) {
        this.driverShift = driverShift;
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

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getFinishPoint() {
        return finishPoint;
    }

    public void setFinishPoint(String finishPoint) {
        this.finishPoint = finishPoint;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getIdOrder() == order.getIdOrder() &&
                Double.compare(order.getDistance(), getDistance()) == 0 &&
                Double.compare(order.getCost(), getCost()) == 0 &&
                Double.compare(order.getRateFromClient(), getRateFromClient()) == 0 &&
                Double.compare(order.getRateFromDriver(), getRateFromDriver()) == 0 &&
                getStatus() == order.getStatus() && Objects.equals(getClient(), order.getClient()) &&
                Objects.equals(getDriverShift(), order.getDriverShift()) &&
                Objects.equals(getDate(), order.getDate()) && Objects.equals(getStartTime(), order.getStartTime()) &&
                Objects.equals(getFinishTime(), order.getFinishTime()) &&
                Objects.equals(getStartPoint(), order.getStartPoint()) &&
                Objects.equals(getFinishPoint(), order.getFinishPoint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdOrder(), getClient(), getDriverShift(), getDate(), getStartTime(), getFinishTime(),
                getStartPoint(), getFinishPoint(), getDistance(), getCost(), getRateFromClient(), getRateFromDriver(),
                getStatus());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append(" idOrder=").append(idOrder);
        sb.append("\nclient=").append(client);
        sb.append("\ndriverShift=").append(driverShift);
        sb.append("\ndate=").append(date);
        sb.append("\nstartTime=").append(startTime);
        sb.append("\nfinishTime=").append(finishTime);
        sb.append("\nstartPoint='").append(startPoint).append('\'');
        sb.append("\nfinishPoint='").append(finishPoint).append('\'');
        sb.append("\ndistance=").append(distance);
        sb.append("\ncost=").append(cost);
        sb.append("\nrateFromClient=").append(rateFromClient);
        sb.append("\nrateFromDriver=").append(rateFromDriver);
        sb.append("\nendOrder=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
