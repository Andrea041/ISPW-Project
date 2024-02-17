package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.IncomingOrderApplicativeController;
import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.utils.GraphicTool;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IncomingOrderGraphicController extends GraphicTool {
    @FXML
    private VBox orderLocation;

    @FXML
    void initialize() {
        IncomingOrderApplicativeController incOrder = new IncomingOrderApplicativeController();
        List<OrderBean> orderBeanList = new ArrayList<>();

        try {
            orderBeanList = incOrder.retrieveOrders();
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        if(!orderBeanList.isEmpty()) {
            for(OrderBean orderBean : orderBeanList)
                appendToDashboard(orderBean.getOrderID());
        }
    }

    public void appendToDashboard(String orderID) {
        Parent root = null;

        OrderBannerGraphicController orderBanner = new OrderBannerGraphicController(orderID);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/codiceprogetto/FXML/IncomingOrder/Order.fxml"));
        fxmlLoader.setController(orderBanner);

        try {
            root = fxmlLoader.load();
        } catch (Exception exception) {
            Logger.getAnonymousLogger().log(Level.INFO, "Invalid page");
        }

        orderLocation.getChildren().add(root);
    }

    public void orderGUI() {
        navigateTo(ORDER);
    }

    public void accountGUI() {
        SessionUser.getInstance().logout();
        navigateTo(LOGIN);
    }
}
