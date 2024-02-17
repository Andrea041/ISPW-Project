package com.example.codiceprogetto.logic.dao;

import com.example.codiceprogetto.logic.entities.Product;

import java.util.List;

public interface ProductDAO {
    Product fetchProduct(String prodID);

    List<Product> fetchAllProduct();
}
