package com.example.codiceprogetto.logic.entities;

public class Product {
    private String name;
    private String id;
    private int selectedUnits;
    private String size;
    private double price;
    private String image;
    public Product(String name, String id, double price, String image) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.image = image;
    }

    public Product(String name, String id, int selectedUnits, double price, String image) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.image = image;
        this.selectedUnits = selectedUnits;
    }

    public String getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }
    public int getSelectedUnits() {
        return selectedUnits;
    }
    public String getName() {
        return name;
    }
    public String getSize() {
        return size;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSelectedUnits(int selectedUnits) {
        this.selectedUnits = selectedUnits;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}
