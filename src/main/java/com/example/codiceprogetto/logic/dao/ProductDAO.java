package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.utils.DBsingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {
    protected Product newProduct(ResultSet rs, int selectedUnits, String size) throws SQLException {
        Product prod;
        prod = new Product(rs.getString("name"), rs.getInt("ID"), selectedUnits, size, rs.getDouble("price"));

        return prod;
    }

    public PreparedStatement retrieveQuery(Connection conn, int id) throws SQLException {
        PreparedStatement stmt;

        String sql = "SELECT * FROM Product WHERE " + "ID" + " = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, id);

        return stmt;
    }

    public Product retrieveProduct(int id, int selectedUnits, String size) throws SQLException {
        Connection conn = DBsingleton.getInstance().getConn();
        ResultSet rs;
        Product product;
        PreparedStatement stmt;

        stmt = retrieveQuery(conn, id);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        product = newProduct(rs, selectedUnits, size);

        stmt.close();
        rs.close();

        return product;
    }

    public int updateProductStock(int id, int selectedUnits) throws SQLException {
        Connection conn = DBsingleton.getInstance().getConn();
        PreparedStatement stmt;
        ResultSet rs;
        int result;

        stmt = retrieveQuery(conn, id);

        rs = stmt.executeQuery();

        int stockUpdate = rs.getInt("inStockUnits") - selectedUnits;

        String sql = "UPDATE Product SET inStockUnits = ? WHERE ID = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, stockUpdate);
        stmt.setInt(2, id);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Product stock updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Product stock update failed");
        }

        return result;
    }
}
