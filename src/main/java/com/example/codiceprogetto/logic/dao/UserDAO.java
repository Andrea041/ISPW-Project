package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.User;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO extends AbsUserDAO {
    private User newUser(ResultSet rs) throws SQLException {
        User user;
        user = new User(rs.getString("email"), rs.getString("password"), rs.getString("userType"), rs.getString("name"), rs.getString("surname"));

        return user;
    }

    public int insertUser(String email, String password, String userType, String name, String surname) throws SQLException {
        int result;
        String query = "INSERT INTO User (email, password, userType, name, surname) VALUES (?, ?, ?, ?, ?)";

        result = registerUser(email, password, userType, name, surname, query);

        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        return result;
    }

    public User findUser(String email) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        ResultSet rs;
        User user;
        PreparedStatement stmt;

        String sql = "SELECT * FROM User WHERE " + "email" + " = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        user = newUser(rs);

        stmt.close();
        rs.close();

        return user;
    }
}
