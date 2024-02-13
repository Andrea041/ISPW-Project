package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OrderDAO {
    public void createOrder(String email, double total, DeliveryAddress address, List<Product> productList) throws SQLException {
        int result;
        OrderStatus orderStatus = OrderStatus.NEW;
        String deliveryAddress;
        String prodList;

        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();

        deliveryAddress = convertAddress(address);
        prodList = listConverter(productList);

        String sql = "INSERT INTO Progetto.Order (email, total, status, address, products) VALUES (?, ?, ?, ?, ?)";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setDouble(2, total);
        stmt.setString(3, orderStatus.getId());
        stmt.setString(4, deliveryAddress);
        stmt.setString(5, prodList);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        stmt.close();
    }

    public String checkOrderStatus(String email) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        String status;
        ResultSet rs;

        String sql = "SELECT status FROM Progetto.Order WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        status = rs.getString("status");

        return status;
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

    private String listConverter(List<Product> list) {
        StringBuilder stringBuilder = new StringBuilder();

        if(list == null)
            return null;

        for(Product product : list) {
            stringBuilder.append(product.getName()).append(",").append(product.getId()).append(",").append(product.getSelectedUnits()).append(",")
                    .append(product.getSize()).append(",").append(product.getPrice()).append(",");
        }
        return stringBuilder.toString();
    }
}
