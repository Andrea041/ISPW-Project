package com.example.codiceprogetto.logic.entities;

import java.util.List;

public class Cart {
    private String emailCustomer;
    private List<Product> products;
    private double total;

    public Cart(String emailCustomer, List<Product> products, double total) {
        this.emailCustomer = emailCustomer;
        this.products = products;
        this.total = total;
    }
    public List<Product> getProducts() {
        return products;
    }
    public String getEmailCustomer() {
        return emailCustomer;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
