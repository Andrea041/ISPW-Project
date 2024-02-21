package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.bean.PaymentBean;
import com.example.codiceprogetto.logic.bean.TransactionBean;
import com.example.codiceprogetto.logic.dao.*;
import com.example.codiceprogetto.logic.entities.Customer;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.entities.Transaction;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.enumeration.PaymentType;
import com.example.codiceprogetto.logic.enumeration.TransactionStatus;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;

import java.sql.SQLException;

public class PaymentApplicativeController {
    public void insertPaymentMethod(PaymentBean paymentBean, String email) {
        new PaymentDAO().insertPaymentMethod(email,
                                             paymentBean.getName(),
                                             paymentBean.getLastName(),
                                             paymentBean.getExpiration(),
                                             paymentBean.getCardNumber(),
                                             paymentBean.getCvv(),
                                             paymentBean.getZipCode());
    }

    public boolean checkCustomerPaymentMethod(String email) throws SQLException, DAOException {
        Customer customer;
        boolean checker = false;

        customer = new CustomerDAO().findCustomer(email);

        if(customer.getPortfolio().getCardNumber() != null)
            checker = true;

        return checker;
    }

    public void createTransaction(String email, PaymentType paymentType) throws SQLException {
        Order order;
        TransactionStatus transactionStatus = TransactionStatus.NEW;
        String status;

        status = transactionStatus.getId();
        order = new OrderDAO().fetchOrder(email, OrderStatus.NEW.getId());

        new TransactionDAO().insertTransaction(email, status, order.getOrderID(), paymentType.getId());
    }

    public OrderBean fetchTotal(String email, OrderBean orderBean) throws SQLException {
        Order order;

        order = new OrderDAO().fetchOrder(email, OrderStatus.NEW.getId());
        orderBean.setFinalTotal(order.getTotal());
        orderBean.setOrderID(order.getOrderID());
        orderBean.setOrderStatus(order.getStatus());

        return orderBean;
    }

    public TransactionBean fetchTransaction(OrderBean orderBean) throws DAOException {
        Transaction transaction;
        TransactionBean transactionBean = new TransactionBean();

        transaction = new TransactionDAO().fetchTransaction(orderBean.getOrderID());
        transactionBean.setTransactionID(transaction.getTransactionID());
        transactionBean.setPaymentType(transaction.getPaymentType());

        return transactionBean;
    }

    public void makeCartEmpty(String email) throws SQLException, DAOException, TooManyUnitsExcpetion {
        OrderStatus orderStatus = OrderStatus.CONFIRMED;

        new OrderDAO().updateOrderStatus(email, orderStatus.getId());

        new CartDAO().updateCart(null, email, "REMOVE ALL");
        new CartDAO().updateCartTotal(0.0, email);
        new CartDAO().updateCartShipping(0, email);
        new CartDAO().updateCartCoupon(0, email);
    }

    public void deleteOrder(String email) throws DAOException {
        new OrderDAO().deleteOrder(email);
    }
}
