package com.example.codiceprogetto.logic.entities;

public class User {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String userType;
    public User(String email, String password, String userType, String name, String surname) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.surname = surname;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public String getUserType() {
        return userType;
    }
    public String getSurname() {
        return surname;
    }
    public String getName() {
        return name;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setName(String name) {
        this.name = name;
    }
}
