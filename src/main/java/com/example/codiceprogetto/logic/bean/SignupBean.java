package com.example.codiceprogetto.logic.bean;

public class SignupBean {
    private String email;
    private String password;
    private String userType;
    private String name;
    private String surname;
    public SignupBean(String mail, String pass, String name, String surname){
        this.email = mail;
        this.password = pass;
        this.userType = "CUSTOMER";
        this.name = name;
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
}
