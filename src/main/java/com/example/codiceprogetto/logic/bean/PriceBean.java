package com.example.codiceprogetto.logic.bean;

public class PriceBean {
    private double total;
    private double tax;
    private double subtotal;
    public PriceBean(double total, double tax, double subtotal) {
        this.total = total;
        this.subtotal = subtotal;
        this.tax = tax;
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
