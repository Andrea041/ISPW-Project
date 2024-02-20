package com.example.codiceprogetto.logic.entities;

import com.example.codiceprogetto.logic.enumeration.UserType;

public class Customer extends User{
    private Payment portfolio;
    private DeliveryAddress address;
    public Customer(String email, String password, UserType userType, String name, String surname, DeliveryAddress address, Payment portfolio) {
        super(email, password, userType, name, surname);
        this.address = address;
        this.portfolio = portfolio;
    }

    public void setAddress(DeliveryAddress address) {
        this.address = address;
    }
    public void setPortfolio(Payment portfolio) {
        this.portfolio = portfolio;
    }
    public DeliveryAddress getAddress() {
        return address;
    }
    public Payment getPortfolio() {
        return portfolio;
    }
}

