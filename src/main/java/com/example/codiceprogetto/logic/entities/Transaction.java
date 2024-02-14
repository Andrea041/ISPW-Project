package com.example.codiceprogetto.logic.entities;

import com.example.codiceprogetto.logic.enumeration.PaymentType;
import com.example.codiceprogetto.logic.enumeration.TransactionStatus;

public class Transaction {
    private String email;
    private TransactionStatus status;
    private String transactionID;
    private PaymentType paymentType;

    public Transaction(String email, TransactionStatus status, String transactionID, PaymentType paymentType) {
        this.email = email;
        this.status = status;
        this.transactionID = transactionID;
        this.paymentType = paymentType;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
    public String getEmail() {
        return email;
    }
    public String getTransactionID() {
        return transactionID;
    }
    public PaymentType getPaymentType() {
        return paymentType;
    }
    public TransactionStatus getStatus() {
        return status;
    }
}
