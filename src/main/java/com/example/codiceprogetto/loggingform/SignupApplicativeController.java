package com.example.codiceprogetto.loggingform;

import com.example.codiceprogetto.entities.User;

public class SignupApplicativeController {
    private SignupBean userBean;
    public SignupApplicativeController(SignupBean userBean) {
        this.userBean = userBean;
    }
    public void signupUser() {
        SignupDAO sign = new SignupDAO();
        User newUser = new User(userBean.getEmail(), userBean.getPassword());

        if(userBean.getUserType().equals("CUSTOMER"))
            sign.insertCustomer(newUser);
        else
            sign.insertSeller(newUser);
    }
}
