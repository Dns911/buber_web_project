package com.epam.buber.entity;

import com.epam.buber.entity.parameter.UserRole;

import java.sql.Date;
import java.util.Objects;

public abstract class User extends AbstractEntity {
    private int idUser;
    private String email;
    private String password;
    private String phoneNum;
    private String name;
    private String lastName;
    private Date registrationDate;
    private double rate;
    private UserRole role;

    public User() {
    }

    public User(int idUser, String email, String password, String phoneNum, String name, String lastName,
                Date registrationDate, double rate, UserRole role) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.name = name;
        this.lastName = lastName;
        this.registrationDate = registrationDate;
        this.rate = rate;
        this.role = role;
    }

    public int getId() {
        return idUser;
    }

    public void setId(int idUser) {
        this.idUser = idUser;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() && Double.compare(user.getRate(), getRate()) == 0 && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getPhoneNum(), user.getPhoneNum()) && Objects.equals(getName(), user.getName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getRegistrationDate(), user.getRegistrationDate()) && getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getPassword(), getPhoneNum(), getName(), getLastName(), getRegistrationDate(), getRate(), getRole());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("Id=").append(idUser);
        sb.append("\n email='").append(email).append('\'');
        sb.append("\n password='").append(password).append('\'');
        sb.append("\n phoneNum='").append(phoneNum).append('\'');
        sb.append("\n name='").append(name).append('\'');
        sb.append("\n lastName='").append(lastName).append('\'');
        sb.append("\n registrationDate='").append(registrationDate).append('\'');
        sb.append("\n rate=").append(rate);
        sb.append("\n role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
