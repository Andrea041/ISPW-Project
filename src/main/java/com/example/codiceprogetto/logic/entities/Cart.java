package com.example.codiceprogetto.logic.entities;

import java.util.List;

public class Cart {
    private String emailCustomer;
    private List<Product> products;
    private double total;
    private int discount;
    private int shipping;

    public Cart(String emailCustomer, List<Product> products, double total, int discount, int shipping) {
        this.emailCustomer = emailCustomer;
        this.products = products;
        this.total = total;
        this.discount = discount;
        this.shipping = shipping;
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
    public void setDiscount(int discount) {
        this.discount = discount;
    }
    public void setShipping(int shipping) {
        this.shipping = shipping;
    }
    public int getDiscount() {
        return discount;
    }
    public int getShipping() {
        return shipping;
    }
}
