package com.example.codiceprogetto.logic.bean;

public class CartBean {
    private double total;
    private double tax;
    private double subtotal;
    private double discount;
    private int shipping;

    public double getSubtotal() {
        return subtotal;
    }
    public double getTax() {
        return tax;
    }
    public double getTotal() {
        return total;
    }
    public double getDiscount() {
        return discount;
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
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public void setShipping(int shipping) {
        this.shipping = shipping;
    }
    public int getShipping() {
        return shipping;
    }
}
