package com.example.codiceprogetto.logic.bean;

public class PaymentBean {
    private String name;
    private String lastName;
    private String expiration;
    private String cardNumber;
    private String cvv;
    private String zipCode;

    public PaymentBean(String name, String lastName, String expiration, String cardNumber, String cvv, String zipCode) {
        this.name = name;
        this.lastName = lastName;
        this.expiration = expiration;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.zipCode = zipCode;
    }

    public String getExpiration() {
        return expiration;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public String getCvv() {
        return cvv;
    }
    public String getZipCode() {
        return zipCode;
    }
    public String getLastName() {
        return lastName;
    }
    public String getName() {
        return name;
    }
    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

