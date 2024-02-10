package com.example.codiceprogetto.logic.bean;

import com.example.codiceprogetto.logic.graphiccontroller.ShoppingCartGraphicController;

public class CartPriceBean {
    private double total;
    private double tax;
    private double subtotal;
    public CartPriceBean(double total, double tax, double subtotal) {
        this.total = total;
        this.tax = tax;
        this.subtotal = subtotal;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public double getTax() {
        return tax;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    public void setTax(double tax) {
        this.tax = tax;
    }
}
