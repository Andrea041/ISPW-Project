package com.example.codiceprogetto.logic.entities;

public class DeliveryAddress {
    private String name;
    private String surname;
    private String address;
    private String city;
    private String country;
    private String state;
    private int phoneNumber;
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }
    public String getCity() {
        return city;
    }
    public String getCountry() {
        return country;
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
    public void setCountry(String country) {
        this.country = country;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
