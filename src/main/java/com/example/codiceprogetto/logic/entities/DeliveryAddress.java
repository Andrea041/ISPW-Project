package com.example.codiceprogetto.logic.entities;

public class DeliveryAddress {
    private String name;
    private String surname;
    private String address;
    private String city;
    private String state;
    private String phoneNumber;

    public DeliveryAddress(String name, String surname, String address, String city, String state, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.state = state;
        this.phoneNumber =  phoneNumber;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getSurname() {
        return surname;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
