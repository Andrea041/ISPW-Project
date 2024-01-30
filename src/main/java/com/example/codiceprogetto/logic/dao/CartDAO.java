package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Product;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.DBsingleton;

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
        Connection conn = DBsingleton.getInstance().getConn();

        String sql = "INSERT INTO Cart (email, products) VALUES (?, ?)";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        stmt.setString(2, null);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "New row in DB");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Insertion failed");
        }

        stmt.close();

        return result;
    }

    public int updateCart(Product product, String email) throws SQLException, TooManyUnitsExcpetion {
        int result = -1;
        List<Product> productList = new ArrayList<>();
        String listUpdated, prodList;


        PreparedStatement stmt;
        ResultSet rs;
        Connection conn = DBsingleton.getInstance().getConn();

        String sql = "SELECT products FROM Cart WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);

        rs = stmt.executeQuery();

        if(!rs.first()) {
            return result;
        }

        rs.first();

        prodList = rs.getString("products");

        if(prodList != null)
            productList = stringConverter(prodList);

        if(!checkProd(productList, product))
            productList.add(product);

        listUpdated = listConverter(productList);

        rs.close();

        sql = "UPDATE Cart SET products = ? WHERE email = ?";
        stmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, listUpdated);
        stmt.setString(2, email);

        result = stmt.executeUpdate();
        if(result > 0){
            Logger.getAnonymousLogger().log(Level.INFO, "Cart updated");
        } else {
            Logger.getAnonymousLogger().log(Level.INFO, "Cart update failed");
        }

        stmt.close();

        return result;
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

    public String listConverter(List<Product> lista) {
        StringBuilder stringBuilder = new StringBuilder();

        for(Product product : lista) {
            stringBuilder.append(product.getName()).append(",").append(product.getId()).append(",").append(product.getSelectedUnits()).append(",")
                    .append(product.getSize()).append(",").append(product.getPrice()).append(",");
        }
        return stringBuilder.toString();
    }

    public boolean checkProd(List<Product> prodList, Product newProd) throws TooManyUnitsExcpetion {
        for(Product prod : prodList) {
            if(prod.getName().equals(newProd.getName())) {
                prod.setSelectedUnits(newProd.getSelectedUnits() + prod.getSelectedUnits());
                if(prod.getSelectedUnits() > 10)
                    throw new TooManyUnitsExcpetion("Too many units of a single product...new units not added");
            }
        }

        return false;
    }
}
