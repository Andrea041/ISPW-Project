package com.example.codiceprogetto.logic.bean;

import com.example.codiceprogetto.logic.enumeration.UserType;

public class SignupBean {
    private String email;
    private String password;
    private UserType userType;
    private String name;
    private String surname;
    public SignupBean(String mail, String pass, String name, String surname){
        this.email = mail;
        this.password = pass;
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
    public UserType getUserType() {
        return userType;
    }
    public void setUserType(UserType userType) {
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
