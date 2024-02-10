package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartDAO {
    public int createCustomerCart(String email) throws SQLException {
        int result;
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();

        String sql = "INSERT INTO Cart (email, products, total) VALUES (?, ?, ?)";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, null);
        stmt.setDouble(3, 0);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        stmt.close();

        return result;
    }

    public int updateCart(Product product, String email, String op) throws TooManyUnitsExcpetion, DAOException, SQLException {
        int result;
        List<Product> productList;
        String listUpdated;

        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();

        productList = retrieveCartContent(email);

        if(productList.isEmpty())
            productList.add(product);
        else if(op.equals("ADD") && !countUnits(productList, product))
            productList.add(product);
        else if(op.equals("REMOVE"))
            productList.removeIf(prod -> prod.getName().equals(product.getName()));

        listUpdated = listConverter(productList);

        String sql = "UPDATE Cart SET products = ? WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, listUpdated);
        stmt.setString(2, email);

        result = stmt.executeUpdate();
        if(result > 0)
            Logger.getAnonymousLogger().log(Level.INFO, "Cart updated");
        else
            Logger.getAnonymousLogger().log(Level.INFO, "Cart update failed");

        stmt.close();

        return result;
    }

    public List<Product> retrieveCartContent(String email) throws SQLException, DAOException {
        List<Product> productList;
        String prodList;

        PreparedStatement stmt;
        ResultSet rs;
        Connection conn = DBConnectionFactory.getConn();

        String sql = "SELECT products FROM Cart WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first())
            throw new DAOException("Not existing cart");

        rs.first();

        prodList = rs.getString("products");

        if(!prodList.isEmpty())
            productList = stringConverter(prodList);
        else
            return new ArrayList<>();

        rs.close();
        stmt.close();

        return productList;
    }

    public void updateCartTotal(String totalStr, String email) throws SQLException {
        PreparedStatement stmt;
        Connection conn = DBConnectionFactory.getConn();
        int result;

        String sql = "UPDATE Cart SET total = ? WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, totalStr);
        stmt.setString(2, email);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Total amount updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Total amount update failed");
        }

        stmt.close();
    }

    public double retrieveCartTotal(String email) throws SQLException, DAOException {
        double amount;

        PreparedStatement stmt;
        ResultSet rs;
        Connection conn = DBConnectionFactory.getConn();

        String sql = "SELECT total FROM Cart WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            throw new DAOException("Not existing cart!");
        }

        rs.first();

        amount = Double.parseDouble(rs.getString("total"));

        rs.close();
        stmt.close();

        return amount;
    }

    public List<Product> stringConverter(String list) {
        List<Product> listProd = new ArrayList<>();
        String[] elements = list.split(",");

        for(int i = 0; i < elements.length; i += 5) {
            String name = elements[i];
            int id = Integer.parseInt(elements[i + 1]);
            int selectedUnits = Integer.parseInt(elements[i + 2]);
            String size = elements[i + 3];
            double price = Double.parseDouble(elements[i + 4]);

            listProd.add(new Product(name, id, selectedUnits, size, price));
        }
        return listProd;
    }

    public String listConverter(List<Product> list) {
        StringBuilder stringBuilder = new StringBuilder();

        if(list == null)
            return null;

        for(Product product : list) {
            stringBuilder.append(product.getName()).append(",").append(product.getId()).append(",").append(product.getSelectedUnits()).append(",")
                    .append(product.getSize()).append(",").append(product.getPrice()).append(",");
        }
        return stringBuilder.toString();
    }

    public boolean countUnits(List<Product> prodList, Product newProd) throws TooManyUnitsExcpetion {
        for(Product prod : prodList) {
            if(prod.getName().equals(newProd.getName())) {
                prod.setSelectedUnits(newProd.getSelectedUnits() + prod.getSelectedUnits());
                if(prod.getSelectedUnits() <= 10)
                    return true;
                else
                    throw new TooManyUnitsExcpetion("Limit units for each customer reached, the new units aren't added in the cart");
            }
        }
        return false;
    }
}
