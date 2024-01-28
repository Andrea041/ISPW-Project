package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.entities.User;
import com.example.codiceprogetto.logic.utils.DBsingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO {
    protected Product newProduct(ResultSet rs) throws SQLException {
        Product prod;
        prod = new Product(rs.getString("name"), rs.getInt("ID"), rs.getInt("availableUnits"), rs.getString("size"), rs.getDouble("price"), rs.getString("category"));

        return prod;
    }
    public Product retrieveProduct(int ID) throws SQLException {
        Connection conn = DBsingleton.getInstance().getConn();
        ResultSet rs = null;
        Product product = null;
        PreparedStatement stmt = null;

        String sql = "SELECT * FROM Product WHERE " + "ID" + " = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, ID);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        product = newProduct(rs);

        stmt.close();
        rs.close();

        return product;
    }
}
