package com.example.codiceprogetto.logic.bean;

import com.example.codiceprogetto.logic.enumeration.PaymentType;

public class TransactionBean {
    private String transactionID;
    private PaymentType paymentType;

    public String getTransactionID() {
        return transactionID;
    }
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
    public PaymentType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}
