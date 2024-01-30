package com.example.codiceprogetto.logic.entities;

public class Product {
    private String name;
    private int id;
    private int selectedUnits;
    private String size;
    private double price;
    public Product(String name, int id, int selectedUnits, String size, double price) {
        this.name = name;
        this.id = id;
        this.selectedUnits = selectedUnits;
        this.size = size;
        this.price = price;
    }
    public int getId() {
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
    public void setId(int id) {
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
}
