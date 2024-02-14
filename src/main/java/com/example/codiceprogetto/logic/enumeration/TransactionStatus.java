package com.example.codiceprogetto.logic.enumeration;

public enum TransactionStatus {
    NEW("new"),
    CONFIRMED("confirmed"),
    REJECTED("rejected"),
    PROCESSING("processing");

    private final String id;

    TransactionStatus(String id) {
        this.id = id;
    }

    public static TransactionStatus fromString(String id) {
        for(TransactionStatus transaction : values()) {
            if(transaction.getId().equals(id)) {
                return transaction;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
