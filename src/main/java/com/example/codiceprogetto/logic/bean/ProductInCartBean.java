package com.example.codiceprogetto.logic.bean;

import com.example.codiceprogetto.logic.graphiccontroller.ShoppingCartGraphicController;
import com.example.codiceprogetto.logic.observer.Subject;

public class ProductInCartBean extends Subject {
    private double totalAmount;
    public ProductInCartBean(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void notifySubscriber() {
        super.notifyObserver();
    }
}
