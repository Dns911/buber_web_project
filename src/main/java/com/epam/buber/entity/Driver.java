package com.epam.buber.entity;

import com.epam.buber.entity.parameter.DriverStatus;

import java.util.Date;
import java.util.Objects;

public class Driver extends AbstractEntity{
    private int idDriver;
    private String email;
    private String password;
    private String phoneNum;
    private String name;
    private String lastName;
    private Date registrationDate;
    private String licenceNum;
    private Date licenceValidDate;
    private double incomeSum;
    private double rate;
    private DriverStatus status;

    public Driver() {
    }

    public Driver(int idDriver, String email, String password, String phoneNum, String name, String lastName, Date registrationDate, String licenceNum, Date licenceValidDate, double incomeSum, double rate, DriverStatus status) {
        this.idDriver = idDriver;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.name = name;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
        this.licenceNum = licenceNum;
        this.licenceValidDate = licenceValidDate;
        this.incomeSum = incomeSum;
        this.rate = rate;
        this.status = status;
    }

    public int getIdDriver() {
        return idDriver;
    }

    public void setIdDriver(int idDriver) {
        this.idDriver = idDriver;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
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
        Driver driver = (Driver) o;
        return getIdDriver() == driver.getIdDriver() && Double.compare(driver.getIncomeSum(),
                getIncomeSum()) == 0 && Double.compare(driver.getRate(),
                getRate()) == 0 && Objects.equals(getEmail(),
                driver.getEmail()) && Objects.equals(getPassword(),
                driver.getPassword()) && Objects.equals(getPhoneNum(),
                driver.getPhoneNum()) && Objects.equals(getName(),
                driver.getName()) && Objects.equals(getLastName(),
                driver.getLastName()) && Objects.equals(getRegistrationDate(),
                driver.getRegistrationDate()) && Objects.equals(getLicenceNum(),
                driver.getLicenceNum()) && Objects.equals(getLicenceValidDate(),
                driver.getLicenceValidDate()) && getStatus() == driver.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdDriver(), getEmail(), getPassword(), getPhoneNum(), getName(), getLastName(),
                getRegistrationDate(), getLicenceNum(), getLicenceValidDate(), getIncomeSum(), getRate(), getStatus());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Driver{");
        sb.append("\n idDriver=").append(idDriver);
        sb.append("\n email='").append(email).append('\'');
        sb.append("\n password='").append(password).append('\'');
        sb.append("\n phoneNum='").append(phoneNum).append('\'');
        sb.append("\n name='").append(name).append('\'');
        sb.append("\n lastName='").append(lastName).append('\'');
        sb.append("\n registrationDate=").append(registrationDate);
        sb.append("\n licenceNum='").append(licenceNum).append('\'');
        sb.append("\n licenceValidDate=").append(licenceValidDate);
        sb.append("\n incomeSum=").append(incomeSum);
        sb.append("\n rate=").append(rate);
        sb.append("\n status=").append(status);
        sb.append("\n }");
        return sb.toString();
    }
}
