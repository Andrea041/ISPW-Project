package com.example.codiceprogetto.logic.entities;

public class Product {
    private String name;
    private int id;
    private int availableUnits;
    private String size;
    private double price;
    private String category;
    public Product(String name, int id, int availableUnits, String size, double price, String category) {
        this.name = name;
        this.id = id;
        this.availableUnits = availableUnits;
        this.size = size;
        this.price = price;
        this.category = category;
    }
    public int getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }
    public int getAvailableUnits() {
        return availableUnits;
    }
    public String getCategory() {
        return category;
    }
    public String getName() {
        return name;
    }
    public String getSize() {
        return size;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAvailableUnits(int availableUnits) {
        this.availableUnits = availableUnits;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setSize(String size) {
        this.size = size;
    }
}
