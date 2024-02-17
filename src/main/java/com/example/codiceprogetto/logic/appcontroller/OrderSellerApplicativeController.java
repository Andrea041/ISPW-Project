package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.dao.OrderDAO;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;

import java.sql.SQLException;

public class OrderSellerApplicativeController {
    public OrderBean updateUI(String orderId, OrderBean orderBean) throws SQLException {
        Order order;

        order = new OrderDAO().fetchOrderByID(orderId, OrderStatus.CONFIRMED.getId());

        orderBean.setFinalTotal(order.getTotal());
        orderBean.setAddress(order.getAddress());

        return orderBean;
    }

    public void acceptOrder(String orderId) throws SQLException {
        OrderStatus orderStatus = OrderStatus.CLOSED;

        new OrderDAO().updateOrderStatusByID(orderId, orderStatus.getId());
    }
}
