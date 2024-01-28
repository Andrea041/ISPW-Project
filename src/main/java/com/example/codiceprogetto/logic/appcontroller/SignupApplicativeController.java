package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.dao.CustomerDAO;
import com.example.codiceprogetto.logic.dao.SellerDAO;
import com.example.codiceprogetto.logic.entities.User;
import com.example.codiceprogetto.logic.bean.SignupBean;
import com.example.codiceprogetto.logic.dao.UserDAO;
import com.example.codiceprogetto.logic.exception.AlreadyExistingUserException;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.SessionUser;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignupApplicativeController {
    public int signupUser(SignupBean userBean) throws SQLException, EmptyInputException, AlreadyExistingUserException, AlreadyLoggedUserException {
        User user;
        int result = -1;

        user = new UserDAO().findUser(userBean.getEmail());

        if(user == null) {
            if(!userBean.getName().isEmpty() && !userBean.getName().isEmpty() && !userBean.getEmail().isEmpty() && !userBean.getPassword().isEmpty()) {
                result = new UserDAO().insertUser(userBean.getEmail(), userBean.getPassword(), userBean.getUserType(), userBean.getName(), userBean.getSurname());
                switch(userBean.getUserType()) {
                    case "CUSTOMER":
                        result = new CustomerDAO().insertCustomer(userBean.getEmail(), userBean.getPassword(), userBean.getUserType(), userBean.getName(), userBean.getSurname());
                        break;
                    case "SELLER":
                        result = new SellerDAO().insertSeller(userBean.getEmail(), userBean.getPassword(), userBean.getUserType(), userBean.getName(), userBean.getSurname());
                        break;
                    default:
                        Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
                        break;
                }
            }
            else
                throw new EmptyInputException("There are some empty field, please compile the form correctly");
        } else
            throw new AlreadyExistingUserException("User alredy exist");

        storeSessionData(userBean.getEmail(), userBean.getPassword(), userBean.getUserType(), userBean.getName(), userBean.getSurname());

        return result;
    }

    public void storeSessionData(String email, String password, String userType, String name, String surname) throws AlreadyLoggedUserException {
        SessionUser su = SessionUser.getInstance();
        User thisUser = new User(email, password, userType, name, surname);
        try {
            su.login(thisUser);
        } catch (AlreadyLoggedUserException e) {
            throw new AlreadyLoggedUserException(e.getMessage());
        }
    }
}
