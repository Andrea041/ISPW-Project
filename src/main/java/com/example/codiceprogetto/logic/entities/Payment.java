package com.example.codiceprogetto.logic.entities;

public class Payment {
    private String name;
    private String lastName;
    private String expiration;
    private String cardNumber;
    private String CVV;
    private String zipCode;

    public Payment(String name, String lastName, String expiration, String cardNumber, String CVV, String zipCode) {
        this.name = name;
        this.lastName = lastName;
        this.expiration = expiration;
        this.cardNumber = cardNumber;
        this.CVV = CVV;
        this.zipCode = zipCode;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public void setCVV(String CVV) {
        this.CVV = CVV;
    }
    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public String getLastName() {
        return lastName;
    }
    public String getName() {
        return name;
    }
    public String getExpiration() {
        return expiration;
    }
    public String getCVV() {
        return CVV;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public String getZipCode() {
        return zipCode;
    }
}
