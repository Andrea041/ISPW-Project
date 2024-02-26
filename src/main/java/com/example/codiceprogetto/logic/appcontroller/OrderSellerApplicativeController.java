package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.dao.OrderDAO;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.exception.DAOException;

import java.sql.SQLException;

public class OrderSellerApplicativeController {
    public OrderBean updateUI(OrderBean orderBean) throws SQLException {
        Order order;

        order = new OrderDAO().fetchOrderByID(orderBean.getOrderID(), orderBean.getOrderStatus().getId());

        orderBean.setFinalTotal(order.getTotal());
        orderBean.setAddress(order.getAddress());
        orderBean.setEmail(order.getEmail());

        return orderBean;
    }

    public void acceptOrder(OrderBean orderBean) throws DAOException {
        new OrderDAO().updateOrderStatusByID(orderBean.getOrderID(), orderBean.getOrderStatus().getId());
    }
}

