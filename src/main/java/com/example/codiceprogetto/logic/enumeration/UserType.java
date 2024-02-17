package com.example.codiceprogetto.logic.enumeration;

public enum UserType {
    CUSTOMER("customer"),
    SELLER("seller");

    private final String id;

    UserType(String id) {
        this.id = id;
    }

    public static UserType fromString(String id) {
        for(UserType userType : values()) {
            if(userType.getId().equals(id)) {
                return userType;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
