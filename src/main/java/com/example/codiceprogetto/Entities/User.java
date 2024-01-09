package com.example.codiceprogetto.Entities;

public class User {
    private String email;
    private String password;
    public User(String email, String password) {
        this.email = email;
        this.password = password;
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
}
