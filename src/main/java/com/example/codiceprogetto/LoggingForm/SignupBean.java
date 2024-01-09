package com.example.codiceprogetto.LoggingForm;

public class SignupBean {
    private String email;
    private String password;
    private String userType;
    public SignupBean(String mail, String pass){
        this.email = mail;
        this.password = pass;
        this.userType = "";
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getUserType() {
        return userType;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
}
