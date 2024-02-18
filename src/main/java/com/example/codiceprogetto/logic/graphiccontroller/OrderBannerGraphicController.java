package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.OrderSellerApplicativeController;
import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.utils.Utilities;
import com.example.codiceprogetto.logic.utils.SessionUser;
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
    private final String orderID;
    OrderSellerApplicativeController ordApp;

    @FXML
    void initialize() {
        updateGUI();
    }

    protected OrderBannerGraphicController(String orderID) {
        this.orderID = orderID;
    }

    public void acceptOrder() {
        ordApp = new OrderSellerApplicativeController();

        try {
            ordApp.acceptOrder(orderID);
        } catch(DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        svgAccept.setVisible(true);
        svgAcceptDouble.setVisible(true);
        acceptButton.setVisible(false);
        cancelButton.setVisible(false);
    }

    public void updateGUI() {
        ordApp = new OrderSellerApplicativeController();
        OrderBean orderBean = new OrderBean();

        try {
            orderBean = ordApp.updateUI(orderID, orderBean);
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        state.setText(orderBean.getAddress().getState());
        price.setText(round(orderBean.getFinalTotal(),2) + "€");
        customerEmail.setText(SessionUser.getInstance().getThisUser().getEmail());
        labelID.setText(orderID);
    }
}
