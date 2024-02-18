package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.PaymentApplicativeController;
import com.example.codiceprogetto.logic.bean.OrderBean;
import com.example.codiceprogetto.logic.bean.TransactionBean;
import com.example.codiceprogetto.logic.enumeration.OrderStatus;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.utils.Utilities;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentSummaryGraphicController extends Utilities {
    @FXML
    private Label totalAmount;
    @FXML
    private Label paymentMethod;
    @FXML
    private Label orderID;
    @FXML
    private Label orderStatus;
    @FXML
    private Label customerEmail;

    @FXML
    void initialize() {
        PaymentApplicativeController toPay = new PaymentApplicativeController();
        OrderBean orderBean = new OrderBean();
        TransactionBean transactionBean = new TransactionBean();

        try {
            orderBean = toPay.fetchTotal(SessionUser.getInstance().getThisUser().getEmail(), orderBean);
            transactionBean = toPay.fetchTransaction(orderBean);

            toPay.makeCartEmpty(SessionUser.getInstance().getThisUser().getEmail());
            orderBean.setOrderStatus(OrderStatus.CONFIRMED);
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        } catch (DAOException | TooManyUnitsExcpetion e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        totalAmount.setText(round(orderBean.getFinalTotal(), 2) + "€");
        paymentMethod.setText(transactionBean.getPaymentType().getId());
        orderID.setText(orderBean.getOrderID());
        orderStatus.setText(orderBean.getOrderStatus().getId());
        customerEmail.setText(SessionUser.getInstance().getThisUser().getEmail());
    }


    public void backHome() {
        navigateTo(HOME);
    }
}
