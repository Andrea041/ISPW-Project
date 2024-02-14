package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.OrderAmountBean;
import com.example.codiceprogetto.logic.bean.PaymentBean;
import com.example.codiceprogetto.logic.dao.CustomerDAO;
import com.example.codiceprogetto.logic.dao.OrderDAO;
import com.example.codiceprogetto.logic.dao.PaymentDAO;
import com.example.codiceprogetto.logic.dao.TransactionDAO;
import com.example.codiceprogetto.logic.entities.Customer;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.enumeration.TransactionStatus;
import com.example.codiceprogetto.logic.exception.EmptyInputException;

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
        order = new OrderDAO().fetchNewOrder(email);

        new TransactionDAO().insertTransaction(email, status, order.getOrderID(), paymentType);
    }

    public OrderAmountBean fetchTotal(String email, OrderAmountBean orderBean) throws SQLException {
        Order order;

        order = new OrderDAO().fetchNewOrder(email);
        orderBean.setFinalTotal(order.getTotal());

        return orderBean;
    }
}
