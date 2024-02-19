package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Customer;
import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.entities.Payment;
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
        Payment payment;

        address = new DeliveryAddress(rs.getString("addressName"),
                                      rs.getString("addressSurname"),
                                      rs.getString("address"),
                                      rs.getString("city"),
                                      rs.getString("state"),
                                      rs.getString("phoneNumber"));
        payment = new Payment(rs.getString("name"),
                                        rs.getString("lastName"),
                                        rs.getString("expiration"),
                                        rs.getString("cardNumber"),
                                        rs.getString("cvv"),
                                        rs.getString("zipCode"));

        customer = new Customer(rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("userType"),
                                rs.getString("name"),
                                rs.getString("surname"),
                                address,
                                payment);

        return customer;
    }

    public int insertCustomer(String email, String password, String userType, String name, String surname) throws DAOException {
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
        ResultSet rs = null;
        Customer customer = null;

        String sql = "SELECT Customer.*, Address.*, Payment.* FROM Customer " +
                "LEFT JOIN Address ON Customer.email = Address.email " +
                "LEFT JOIN Payment ON Customer.email = Payment.email " +
                "WHERE Customer.email = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if (!rs.first()) {
                throw new DAOException("Error fetching customer");
            }

            rs.first();

            customer = newCustomer(rs);
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } finally {
            if (rs != null)
                rs.close();
        }

        return customer;
    }
}
