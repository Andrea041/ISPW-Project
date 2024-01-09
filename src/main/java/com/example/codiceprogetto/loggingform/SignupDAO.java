package com.example.codiceprogetto.loggingform;

import com.example.codiceprogetto.entities.User;
import com.example.codiceprogetto.queries.Queries;
import com.example.codiceprogetto.utils.DBsingleton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignupDAO {
    private Connection conn;
    public SignupDAO(){
        this.conn = DBsingleton.getDBSingleton().getConn();
    }

    public void insertCustomer(User user) {
        Statement stmt = null;

        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = Queries.insertUser(stmt, user);

            rs.close();
        } catch(SQLException e){
            e.printStackTrace();
        } finally {
            if(stmt != null)
                stmt.close();
        }
    }
    public void insertSeller(User user) {

    }
}
