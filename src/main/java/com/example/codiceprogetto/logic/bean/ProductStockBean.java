package com.example.codiceprogetto.logic.bean;

public class ProductStockBean {
    private double totalAmount;
    private String labelID;
    private String productName;
    private double price;
    private String prodImage;

    public ProductStockBean(String labelID, String prodImage, double price, String productName) {
        this.productName = productName;
        this.labelID = labelID;
        this.prodImage = prodImage;
        this.price = price;
    }

    public ProductStockBean() {}

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getLabelID() {
        return labelID;
    }
    public double getPrice() {
        return price;
    }
    public String getProdImage() {
        return prodImage;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setLabelID(String labelID) {
        this.labelID = labelID;
    }
    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }
}
