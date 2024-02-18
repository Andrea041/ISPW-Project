package com.example.codiceprogetto.logic.entities;

import com.example.codiceprogetto.logic.enumeration.OrderStatus;

import java.util.List;

public class Order {
    private DeliveryAddress address;
    private double total;
    private String orderID;
    private OrderStatus status;
    private List<Product> productList;
    private String email;

    public Order(String email, DeliveryAddress address, double total, String orderID, OrderStatus status, List<Product> productList) {
        this.address = address;
        this.total = total;
        this.orderID = orderID;
        this.status = status;
        this.productList = productList;
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }
    public String getOrderID() {
        return orderID;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public DeliveryAddress getAddress() {
        return address;
    }
    public void setAddress(DeliveryAddress address) {
        this.address = address;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    public List<Product> getProductList() {
        return productList;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public double getTotal() {
        return total;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
