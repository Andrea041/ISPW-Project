package com.example.codiceprogetto.logic.bean;

public class ApprovedOrderBean {
    private int approvedOrder;
    private int notApprovedOrder;

    public int getApprovedOrder() {
        return approvedOrder;
    }
    public int getNotApprovedOrder() {
        return notApprovedOrder;
    }
    public void setNotApprovedOrder(int notApprovedOrder) {
        this.notApprovedOrder = notApprovedOrder;
    }
    public void setApprovedOrder(int approvedOrder) {
        this.approvedOrder = approvedOrder;
    }
}
