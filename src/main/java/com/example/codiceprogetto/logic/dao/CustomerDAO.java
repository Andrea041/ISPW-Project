package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Customer;
import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO extends AbsUserDAO {
    private Customer newCustomer(ResultSet rs) throws SQLException {
        Customer customer;
        DeliveryAddress address;

        address = convertStringAddress(rs.getString("address"));

        customer = new Customer(rs.getString("email"), rs.getString("password"), rs.getString("userType"), rs.getString("name"), rs.getString("surname"), address, null);

        return customer;
    }

    public int insertCustomer(String email, String password, String userType, String name, String surname) throws SQLException {
        int result;
        String query = "INSERT INTO Customer (email, password, userType, name, surname) VALUES (?, ?, ?, ?, ?)";

        result = registerUser(email, password, userType, name, surname, query);

        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        return result;
    }

    public Customer findCustomer(String email) throws SQLException, DAOException {
        Connection conn = DBConnectionFactory.getConn();
        ResultSet rs;
        Customer customer;
        PreparedStatement stmt;

        String sql = "SELECT * FROM Customer WHERE " + "email" + " = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            throw new DAOException("Not existing user!");
        }

        rs.first();

        customer = newCustomer(rs);

        stmt.close();
        rs.close();

        return customer;
    }

    public DeliveryAddress fetchAddress(String email) throws SQLException, DAOException {
        Connection conn = DBConnectionFactory.getConn();
        ResultSet rs;
        DeliveryAddress address;
        PreparedStatement stmt;

        String sql = "SELECT address FROM Customer WHERE " + "email" + " = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            throw new DAOException("Not existing user!");
        }

        rs.first();

        address = convertStringAddress(rs.getString("address"));

        stmt.close();
        rs.close();

        return address;
    }

    public void insertAddress(DeliveryAddress address, String email) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        PreparedStatement stmt;
        int result;
        String addressConverted;

        addressConverted = convertAddress(address);

        String query = "UPDATE Customer SET address = ? WHERE email = ?";
        stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, addressConverted);
        stmt.setString(2, email);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }
    }

    private DeliveryAddress convertStringAddress(String addr) {
        if(addr == null)
            return null;

        DeliveryAddress addressNew;
        String[] parts = addr.split(",");

        String name = parts[0];
        String surname = parts[1];
        String address = parts[2];
        String city = parts[3];
        String country = parts[4];
        String state = parts[5];
        String phone = parts[6];

        addressNew = new DeliveryAddress(name, surname, address, city, country, state, phone);

        return addressNew;
    }

    private String convertAddress(DeliveryAddress address) {
        return address.getName() + "," +
                address.getSurname() + "," +
                address.getAddress() + "," +
                address.getCity() + "," +
                address.getCountry() + "," +
                address.getState() + "," +
                address.getPhoneNumber();
    }
}
