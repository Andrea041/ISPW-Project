package com.example.codiceprogetto.logic.entities;

import com.example.codiceprogetto.logic.enumeration.OrderStatus;

public class Order {
    private Cart cart;
    private DeliveryAddress address;
    private String coupon;
    private String orderID;
    private OrderStatus status;

    public Order(Cart cart, DeliveryAddress address, String coupon, String orderID, OrderStatus status) {
        this.cart = cart;
        this.address = address;
        this.coupon = coupon;
        this.orderID = orderID;
        this.status = status;
    }
    public Cart getCart() {
        return cart;
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
    public DeliveryAddress getAddress() {
        return address;
    }
    public void setAddress(DeliveryAddress address) {
        this.address = address;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
