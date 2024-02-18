package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.exception.NotLoggedUserException;
import com.example.codiceprogetto.logic.utils.SessionUser;

public abstract class UserTool {
    public void logoutUser() throws NotLoggedUserException {
        SessionUser sessionUser = SessionUser.getInstance();

        if(sessionUser.getAllUser().contains(sessionUser.getThisUser()))
            sessionUser.logout();
        else
            throw new NotLoggedUserException("You are not logged in!");
    }

    public boolean checkLogin() {
        SessionUser sessionUser = SessionUser.getInstance();

        return sessionUser.getAllUser().contains(sessionUser.getThisUser());
    }
}
