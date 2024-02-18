package com.example.codiceprogetto.logic.utils;

import com.example.codiceprogetto.logic.dao.CartDAO;
import com.example.codiceprogetto.logic.entities.User;
import com.example.codiceprogetto.logic.enumeration.UserType;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import com.example.codiceprogetto.logic.exception.DAOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionUser {
    private static SessionUser instance = null;
    protected User thisUser;
    protected List<User> listOfUser;

    protected SessionUser() {
        listOfUser = new ArrayList<>();
        thisUser = null;
    }

    public static synchronized SessionUser getInstance() {
        if(SessionUser.instance == null)
            SessionUser.instance = new SessionUser();
        return instance;
    }

    public synchronized void cart() {
        if(thisUser.getUserType().equals(UserType.CUSTOMER.getId().toUpperCase())) {
            try {
                new CartDAO().createCustomerCart(thisUser.getEmail());
            } catch (DAOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            }
        }
    }

    public synchronized void login(User user) throws AlreadyLoggedUserException {
        try {
            for(User us : listOfUser) {
                if (us.getEmail().equals(user.getEmail()))
                    throw new AlreadyLoggedUserException("User already logged");
            }
            listOfUser.add(user);
        } finally {
            thisUser = user;
        }
    }
    public synchronized void logout() {
            listOfUser.remove(thisUser);
            thisUser = null;
    }

    public User getThisUser(){
        return thisUser;
    }

    public List<User> getAllUser() {
        return listOfUser;
    }
}
