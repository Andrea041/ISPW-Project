package com.example.codiceprogetto.logic.enumeration;

public enum OrderStatus {
    NEW("new"),
    CONFIRMED("confirmed"),
    CANCELLED("cancelled"),
    CLOSED("closed");

    private final String id;

    OrderStatus(String id) {
        this.id = id;
    }

    public static OrderStatus fromString(String id) {
        for(OrderStatus order : values()) {
            if(order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
