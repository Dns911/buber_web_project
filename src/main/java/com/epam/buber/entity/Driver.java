package com.epam.buber.entity;

import com.epam.buber.entity.types.DriverStatus;


import java.sql.Date;
import java.util.Objects;

public class Driver extends User{
    private String licenceNum;
    private Date licenceValidDate;
    private double incomeSum;
    private DriverStatus status;

    public Driver() {
        super();
    }

    public Driver(String licenceNum, Date licenceValidDate, double incomeSum, DriverStatus status) {
        this.licenceNum = licenceNum;
        this.licenceValidDate = licenceValidDate;
        this.incomeSum = incomeSum;
        this.status = status;
    }

    public String getLicenceNum() {
        return licenceNum;
    }

    public void setLicenceNum(String licenceNum) {
        this.licenceNum = licenceNum;
    }

    public Date getLicenceValidDate() {
        return licenceValidDate;
    }

    public void setLicenceValidDate(Date licenceValidDate) {
        this.licenceValidDate = licenceValidDate;
    }

    public double getIncomeSum() {
        return incomeSum;
    }

    public void setIncomeSum(double incomeSum) {
        this.incomeSum = incomeSum;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driver)) return false;
        if (!super.equals(o)) return false;
        Driver driver = (Driver) o;
        return Double.compare(driver.getIncomeSum(), getIncomeSum()) == 0 &&
                Objects.equals(getLicenceNum(), driver.getLicenceNum()) &&
                Objects.equals(getLicenceValidDate(), driver.getLicenceValidDate()) &&
                getStatus() == driver.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLicenceNum(), getLicenceValidDate(), getIncomeSum(), getStatus());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Driver{");
        sb.append("\n licenceNum='").append(licenceNum).append('\'');
        sb.append("\n licenceValidDate=").append(licenceValidDate);
        sb.append("\n incomeSum=").append(incomeSum);
        sb.append("\n status=").append(status);
        sb.append("\n }");
        return sb.toString();
    }
}
