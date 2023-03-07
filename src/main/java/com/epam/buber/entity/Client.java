package com.epam.buber.entity;

import com.epam.buber.entity.types.UserRole;

import java.sql.Date;
import java.util.Objects;

public class Client extends User {
    private double paymentSum;

    public Client() {
        super();
    }

    public Client(int idUser, String email, String password, String phoneNum, String name, String lastName,
                  Date registrationDate, double rate, UserRole role, double paymentSum) {
        super(idUser, email, password, phoneNum, name, lastName, registrationDate, rate, role);
        this.paymentSum = paymentSum;
    }

    public double getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(double paymentSum) {
        this.paymentSum = paymentSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(client.getPaymentSum(), getPaymentSum()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPaymentSum());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("                paymentSum=").append(paymentSum);
        sb.append('}');
        return sb.toString();
    }
}
