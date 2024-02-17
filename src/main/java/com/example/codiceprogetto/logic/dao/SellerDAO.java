package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.exception.DAOException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SellerDAO extends AbsUserDAO {
    public int insertSeller(String email, String password, String userType, String name, String surname) throws DAOException {
        int result;
        String sql = "INSERT INTO Seller (email, password, userType, name, surname) VALUES (?, ?, ?, ?, ?)";

        result = registerUser(email, password, userType, name, surname, sql);

        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        return result;
    }
}
