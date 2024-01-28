package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.utils.DBsingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartDAO {
    public int createCustomerCart(String email) throws SQLException {
        int result;
        PreparedStatement stmt;
        Connection conn = DBsingleton.getInstance().getConn();

        String sql = "INSERT INTO Cart (email, products) VALUES (?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        stmt.close();

        return result;
    }

    public int updateCart(Product product) throws SQLException {
        int result;
        PreparedStatement stmt;
        Connection conn = DBsingleton.getInstance().getConn();


        String sql = "";
    }
}
