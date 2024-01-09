package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.entities.Customer;
import com.example.codiceprogetto.logic.entities.User;
import com.example.codiceprogetto.logic.bean.SignupBean;
import com.example.codiceprogetto.logic.dao.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class SignupApplicativeController {
    public int signupUser(SignupBean userBean) throws SQLException {
        Connection conn;
        User user = null;
        int result = -1;

        user = new UserDAO().findUser(userBean.getEmail());

        if(user == null) {
            result = new UserDAO().insertUser(userBean.getEmail(), userBean.getPassword());
        } else
            return -1;

        return result;
    }
}
