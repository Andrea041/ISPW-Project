package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.dao.OrderDAO;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomingOrderApplicativeController extends UserTool {
    public List<OrderBean> retrieveOrders() throws SQLException {
        List<Order> orderList;
        OrderStatus orderStatus = OrderStatus.CONFIRMED;

        List<OrderBean> orderBeanList = new ArrayList<>();
        OrderBean orderBean;

        orderList = new OrderDAO().fetchAllOrder(orderStatus.getId());

        for(Order order : orderList) {
            orderBean = new OrderBean(order.getTotal(), order.getAddress(), order.getOrderID(), order.getEmail());
            orderBeanList.add(orderBean);
        }

        return orderBeanList;
    }
}
