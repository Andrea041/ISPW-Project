package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAOJdbc implements ProductDAO {
    protected Product newProduct(ResultSet rs) throws SQLException {
        Product prod;
        prod = new Product(rs.getString("name"), rs.getString("ID"), rs.getDouble("price"), rs.getString("prodImage"));

        return prod;
    }

    protected List<Product> newProductList(ResultSet rs) throws SQLException {
        List<Product> productList = new ArrayList<>();
        Product prod;

         do {
            prod = newProduct(rs);

            productList.add(prod);
         } while(rs.next());

        return productList;
    }

    public PreparedStatement retrieveQuery(Connection conn, String id) throws SQLException {
        PreparedStatement stmt;

        String sql = "SELECT * FROM Product WHERE " + "ID" + " = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, id);

        return stmt;
    }

    public Product fetchProduct(String prodID) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        ResultSet rs;
        Product product;
        PreparedStatement stmt;

        stmt = retrieveQuery(conn, prodID);

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

    public List<Product> fetchAllProduct() throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        ResultSet rs;
        List<Product> productList;
        PreparedStatement stmt;

        String sql = "SELECT * FROM Product";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return null;
        }

        rs.first();

        productList = newProductList(rs);

        stmt.close();
        rs.close();

        return productList;
    }

    /*public int updateProductStock(String id, int selectedUnits) throws SQLException {
        Connection conn = DBConnectionFactory.getConn();
        PreparedStatement stmt;
        ResultSet rs;
        int result = -1;

        stmt = retrieveQuery(conn, id);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return result;
        }

        rs.first();

        int stockUpdate = rs.getInt("inStockUnits") - selectedUnits;

        rs.close();

        String sql = "UPDATE Product SET inStockUnits = ? WHERE ID = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, stockUpdate);
        stmt.setString(2, id);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Product stock updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Product stock update failed");
        }

        stmt.close();

        return result;
    }*/
}
