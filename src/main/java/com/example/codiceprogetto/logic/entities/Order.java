package com.example.codiceprogetto.logic.entities;

import com.example.codiceprogetto.logic.enumeration.OrderStatus;

import java.util.List;

public class Order {
    private List<Product> products;
    private DeliveryAddress address;
    private String coupon;
    private String orderID;
    private OrderStatus status;

    public Order(List<Product> products, DeliveryAddress address, String coupon, String orderID, OrderStatus status) {
        this.products = products;
        this.address = address;
        this.coupon = coupon;
        this.orderID = orderID;
        this.status = status;
    }
    public List<Product> getProducts() {
        return products;
    }
    public String getOrderID() {
        return orderID;
    }
    public String getCoupon() {
        return coupon;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
