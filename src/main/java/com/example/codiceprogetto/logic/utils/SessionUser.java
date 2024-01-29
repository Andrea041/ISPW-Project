package com.example.codiceprogetto.logic.utils;

import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.entities.User;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionUser {
    private static SessionUser instance = null;
    protected User thisUser;
    protected SessionUser() {
        thisUser = null;
    }
    public static synchronized SessionUser getInstance() {
        if(SessionUser.instance == null)
            SessionUser.instance = new SessionUser();
        return instance;
    }
    public synchronized void cart() {
        int ret;
        try {
            ret = new CartDAO().createCustomerCart(thisUser.getEmail());
            if(ret == 0)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }
    }
    public synchronized void login(User user) throws AlreadyLoggedUserException {
        if(thisUser != null && thisUser.getEmail().equals(user.getEmail())) {
            throw new AlreadyLoggedUserException("You are already logged");
        } else if(thisUser != null && !thisUser.getEmail().equals(user.getEmail()))  {
            logout();
            thisUser = user;
        }
        else {
            thisUser = user;
        }
    }
    public synchronized void logout() {
            thisUser = null;
    }
    public User getThisUser(){
        return thisUser;
    }
}
