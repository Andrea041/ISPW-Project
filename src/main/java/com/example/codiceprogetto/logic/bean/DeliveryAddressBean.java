package com.example.codiceprogetto.logic.bean;

public class DeliveryAddressBean {
    private String stateField;
    private String cityField;
    private String numberField;
    private String nameField;
    private String addressField;
    private String countryField;
    private String lastNameField;

    public DeliveryAddressBean(String stateField, String cityField, String numberField, String nameField, String addressField, String countryField, String lastNameField) {
        this.stateField = stateField;
        this.cityField = cityField;
        this.numberField = numberField;
        this.nameField = nameField;
        this.addressField = addressField;
        this.countryField = countryField;
        this.lastNameField = lastNameField;
    }

    public String getAddressField() {
        return addressField;
    }
    public String getCityField() {
        return cityField;
    }
    public String getCountryField() {
        return countryField;
    }
    public String getLastNameField() {
        return lastNameField;
    }
    public String getNameField() {
        return nameField;
    }
    public String getNumberField() {
        return numberField;
    }
    public String getStateField() {
        return stateField;
    }
    public void setAddressField(String addressField) {
        this.addressField = addressField;
    }
    public void setCityField(String cityField) {
        this.cityField = cityField;
    }
    public void setCountryField(String countryField) {
        this.countryField = countryField;
    }
    public void setLastNameField(String lastNameField) {
        this.lastNameField = lastNameField;
    }
    public void setNameField(String nameField) {
        this.nameField = nameField;
    }
    public void setNumberField(String numberField) {
        this.numberField = numberField;
    }
    public void setStateField(String stateField) {
        this.stateField = stateField;
    }
}
