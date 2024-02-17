package com.example.codiceprogetto.logic.bean;

import com.example.codiceprogetto.logic.entities.DeliveryAddress;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;

public class OrderBean {
    private double finalTotal;
    private String orderID;
    private OrderStatus orderStatus;
    private DeliveryAddress address;

    public OrderBean(String orderID) {
        this.orderID = orderID;
    }

    public OrderBean(double finalTotal, DeliveryAddress address) {
        this.finalTotal = finalTotal;
        this.address = address;
    }

    public OrderBean() {}

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
    public DeliveryAddress getAddress() {
        return address;
    }
    public void setAddress(DeliveryAddress address) {
        this.address = address;
    }
}
