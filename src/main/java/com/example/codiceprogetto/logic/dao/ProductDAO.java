package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    Product fetchProduct(String prodID) throws SQLException;

    List<Product> fetchAllProduct() throws SQLException;
}
