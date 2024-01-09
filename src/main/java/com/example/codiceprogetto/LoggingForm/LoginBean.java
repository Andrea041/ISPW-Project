package com.example.codiceprogetto.LoggingForm;

public class LoginBean {
    private String email;
    private String password;
    public LoginBean(String mail, String pass){
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
