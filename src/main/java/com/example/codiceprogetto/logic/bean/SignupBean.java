package com.example.codiceprogetto.logic.bean;

public class SignupBean {
    private String email;
    private String password;
    public SignupBean(String mail, String pass){
        this.email = mail;
        this.password = pass;
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
}
