package com.example.codiceprogetto.logic.appcontroller;

import com.example.codiceprogetto.logic.bean.ApprovedOrderBean;
import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.dao.OrderDAO;
import com.example.codiceprogetto.logic.entities.Order;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;

import java.sql.SQLException;
import java.util.List;

public class HomePageApplicativeController extends UserTool {
    public ApprovedOrderBean fetchAllOrders(OrderBean orderBean) throws SQLException {
        List<Order> orderList;
        ApprovedOrderBean approvedOrderBean = new ApprovedOrderBean();
        int count = 0;

        orderList = new OrderDAO().fetchAllOrderByEmail(orderBean.getOrderStatus().getId(), orderBean.getEmail());

        count = orderList.size();

        if (orderBean.getOrderStatus().getId().equals(OrderStatus.CLOSED.getId()))
            approvedOrderBean.setApprovedOrder(count);
        else
            approvedOrderBean.setNotApprovedOrder(count);

        return approvedOrderBean;
    }
}
