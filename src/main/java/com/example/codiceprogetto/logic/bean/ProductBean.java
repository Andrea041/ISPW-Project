package com.example.codiceprogetto.logic.bean;

public class ProductBean {
    private String name;
    private int id;
    private int unitsToOrder;
    private String size;
    public ProductBean(String name, int id, int unitsToOrder, String size) {
        this.name = name;
        this.id = id;
        this.unitsToOrder = unitsToOrder;
        this.size = size;
    }
    public String getSize() {
        return size;
    }
    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public int getUnitsToOrder() {
        return unitsToOrder;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setUnitsToOrder(int unitsToOrder) {
        this.unitsToOrder = unitsToOrder;
    }
}
