package com.example.codiceprogetto.logic.enumeration;

public enum PaymentType {
    CARD("card"),
    PAYPAL("paypal");


    private final String id;

    PaymentType(String id) {
        this.id = id;
    }

    public static PaymentType fromString(String id) {
        for(PaymentType paymentType : values()) {
            if(paymentType.getId().equals(id)) {
                return paymentType;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
