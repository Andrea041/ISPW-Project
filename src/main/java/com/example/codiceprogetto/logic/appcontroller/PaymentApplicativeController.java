package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.bean.PaymentBean;
import com.example.codiceprogetto.logic.bean.TransactionBean;
import com.example.codiceprogetto.logic.dao.*;
import com.example.codiceprogetto.logic.entities.Customer;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.entities.Transaction;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.enumeration.TransactionStatus;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;

import java.sql.SQLException;

public class PaymentApplicativeController {
    public void insertPayment(PaymentBean paymentBean, String email) throws EmptyInputException, SQLException {
        if(paymentBean.getName().isEmpty() ||
                paymentBean.getLastName().isEmpty() ||
                paymentBean.getExpiration().isEmpty() ||
                paymentBean.getCardNumber().isEmpty() ||
                paymentBean.getCVV().isEmpty() ||
                paymentBean.getZipCode().isEmpty())
            throw new EmptyInputException("There are some empty fields!");

        new PaymentDAO().insertPaymentMethod(email,
                                             paymentBean.getName(),
                                             paymentBean.getLastName(),
                                             paymentBean.getExpiration(),
                                             paymentBean.getCardNumber(),
                                             paymentBean.getCVV(),
                                             paymentBean.getZipCode());
    }

    public boolean checkCustomerPayment(String email) throws SQLException {
        Customer customer;
        boolean checker = false;

        customer = new CustomerDAO().findCustomer(email);

        if(customer != null && customer.getPortfolio() != null)
            checker = true;

        return checker;
    }

    public void createTransaction(String email, String paymentType) throws SQLException {
        Order order;
        TransactionStatus transactionStatus = TransactionStatus.NEW;
        String status;

        status = transactionStatus.getId();
        order = new OrderDAO().fetchOrder(email);

        new TransactionDAO().insertTransaction(email, status, order.getOrderID(), paymentType);


    }

    public OrderBean fetchTotal(String email, OrderBean orderBean) throws SQLException {
        Order order;

        order = new OrderDAO().fetchOrder(email);
        orderBean.setFinalTotal(order.getTotal());
        orderBean.setOrderID(order.getOrderID());
        orderBean.setOrderStatus(order.getStatus());

        return orderBean;
    }

    public TransactionBean fetchTransaction(OrderBean orderBean) throws SQLException {
        Transaction transaction;
        TransactionBean transactionBean = new TransactionBean();

        transaction = new TransactionDAO().fetchTransaction(orderBean.getOrderID());
        transactionBean.setTransactionID(transaction.getTransactionID());
        transactionBean.setPaymentType(transaction.getPaymentType());

        return transactionBean;
    }

    public void updateOrder(String email) throws SQLException, DAOException, TooManyUnitsExcpetion {
        OrderStatus orderStatus = OrderStatus.CONFIRMED;

        new OrderDAO().updateOrderStatus(email, orderStatus.getId());

        new CartDAO().updateCart(null, email, "REMOVE ALL");
        new CartDAO().updateCartTotal("0", email);
        new CartDAO().updateCartShipping(0, email);
    }
}
