package com.example.codiceprogetto.logic.bean;

public class AddressBean {
    private String state;
    private String city;
    private String country;
    private String phoneNumber;
    private String name;
    private String lastName;
    private String address;

    public AddressBean(String state, String city, String country, String phoneNumber, String name, String lastName, String address) {
        this.state = state;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getState() {
        return state;
    }
    public String getCountry() {
        return country;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

}
