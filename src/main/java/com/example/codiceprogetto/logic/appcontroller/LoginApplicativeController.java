package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.LoginBean;
import com.example.codiceprogetto.logic.dao.UserDAO;
import com.example.codiceprogetto.logic.entities.User;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;

public class LoginApplicativeController {
    public int loginUser(LoginBean logUser) throws SQLException, AlreadyLoggedUserException, EmptyInputException {
        User user;
        int result = -1;

        if(logUser.getEmail().isEmpty() || logUser.getPassword().isEmpty())
            throw new EmptyInputException("There are some empty input field");

        user = new UserDAO().findUser(logUser.getEmail());

        if(user != null) {
            if(!user.getPassword().equals(logUser.getPassword()))
                return result;
            else
                storeSessionData(user.getEmail(), user.getPassword(), user.getUserType(), user.getName(), user.getSurname());
        } else
            return result;

        return 1;
    }
    public void storeSessionData(String email, String password, String userType, String name, String surname) throws AlreadyLoggedUserException {
        SessionUser su = SessionUser.getInstance();
        User thisUser = new User(email, password, userType, name, surname);
        try {
            su.login(thisUser);
        } catch (AlreadyLoggedUserException e) {
            throw new AlreadyLoggedUserException("User already logged in");
        }
    }
}

