package com.example.codiceprogetto.logic.entities;

import java.util.List;

public class Cart {
    private String emailCustomer;
    private List<Product> products;

    public Cart(String emailCustomer, List<Product> products) {
        this.emailCustomer = emailCustomer;
        this.products = products;
    }
    public List<Product> getProducts() {
        return products;
    }
    public String getEmailCustomer() {
        return emailCustomer;
    }
    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
