package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.OrderSellerApplicativeController;
import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.Utilities;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderBannerGraphicController extends Utilities {
    @FXML
    private Label price;
    @FXML
    private Label labelID;
    @FXML
    private Label state;
    @FXML
    private Label customerEmail;
    @FXML
    private SVGPath svgAcceptDouble;
    @FXML
    private SVGPath svgAccept;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    @FXML
    private SVGPath deleteSVG;
    private final String orderID;
    OrderSellerApplicativeController ordApp;

    @FXML
    void initialize() {
        ordApp = new OrderSellerApplicativeController();
        updateGUI();
    }

    public OrderBannerGraphicController(String orderID) {
        this.orderID = orderID;
    }

    public void acceptOrder() {
        OrderBean orderBean = new OrderBean(orderID, OrderStatus.CLOSED);

        try {
            ordApp.acceptOrder(orderBean);
        } catch(DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        svgAccept.setVisible(true);
        svgAcceptDouble.setVisible(true);
        acceptButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    public void deleteOrder() {
        OrderBean orderBean = new OrderBean(orderID, OrderStatus.CANCELLED);

        try {
            ordApp.acceptOrder(orderBean);
        } catch(DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        deleteSVG.setVisible(true);
        acceptButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    private void updateGUI() {
        OrderBean orderBean = new OrderBean(orderID, OrderStatus.CONFIRMED);

        try {
            orderBean = ordApp.updateUI(orderBean);
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        state.setText(orderBean.getAddress().getState());
        price.setText(round(orderBean.getFinalTotal(),2) + "€");
        customerEmail.setText(orderBean.getEmail());
        labelID.setText(orderID);
    }
}
