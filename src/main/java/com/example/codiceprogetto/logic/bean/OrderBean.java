package com.example.codiceprogetto.logic.bean;

import com.example.codiceprogetto.logic.enumeration.OrderStatus;

public class OrderBean {
    private double finalTotal;
    private String orderID;
    private OrderStatus orderStatus;

    public double getFinalTotal() {
        return finalTotal;
    }
    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    public String getOrderID() {
        return orderID;
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
