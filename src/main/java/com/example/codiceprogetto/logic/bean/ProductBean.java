package com.example.codiceprogetto.logic.bean;

public class ProductBean {
    private String name;
    private int ID;
    private int unitsToOrder;
    private String size;
    public ProductBean(String name, int ID, int unitsToOrder, String size) {
        this.name = name;
        this.ID = ID;
        this.unitsToOrder = unitsToOrder;
        this.size = size;
    }
    public String getSize() {
        return size;
    }
    public String getName() {
        return name;
    }
    public int getID() {
        return ID;
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
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setUnitsToOrder(int unitsToOrder) {
        this.unitsToOrder = unitsToOrder;
    }
}
