package com.example.codiceprogetto.logic.utils;

import com.example.codiceprogetto.logic.entities.User;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import javafx.scene.input.MouseEvent;

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
    public synchronized void setPage(MouseEvent mouseEvent) {
        switch(SessionUser.getInstance().getThisUser().getUserType()) {
            case "CUSTOMER":
                GraphicTool.navigateTo(mouseEvent, "HOME");
                break;
            case "SELLER":
                //GraphicTool.navigateTo(mouseEvent, "INBOX");
                break;
        }
    }
    public synchronized void login(User user) throws AlreadyLoggedUserException {
        if(thisUser != null && thisUser.getEmail().equals(user.getEmail())) {
            throw new AlreadyLoggedUserException("You are alredy logged");
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
