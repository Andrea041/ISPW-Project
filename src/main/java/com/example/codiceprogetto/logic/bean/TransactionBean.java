package com.example.codiceprogetto.logic.bean;

import com.example.codiceprogetto.logic.enumeration.PaymentType;

public class TransactionBean {
    private String TransactionID;
    private PaymentType paymentType;

    public String getTransactionID() {
        return TransactionID;
    }
    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }
    public PaymentType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
