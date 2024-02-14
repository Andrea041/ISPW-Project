package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.PaymentApplicativeController;
import com.example.codiceprogetto.logic.bean.OrderAmountBean;
import com.example.codiceprogetto.logic.bean.PaymentBean;
import com.example.codiceprogetto.logic.enumeration.PaymentType;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.GraphicTool;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentGraphicController extends GraphicTool {
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField expirationField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField zipField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField cvvField;
    @FXML
    private Label totalAmount;
    @FXML
    private CheckBox paymetCheckbox;

    Stage rootToDisplay;


    @FXML
    void initialize() {
        OrderAmountBean orderBean = new OrderAmountBean();
        PaymentApplicativeController cOut = new PaymentApplicativeController();

        try {
            orderBean = cOut.fetchTotal(SessionUser.getInstance().getThisUser().getEmail(), orderBean);
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        totalAmount.setText(round(orderBean.getFinalTotal(), 2) + "€");
    }

    public void homeGUI(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "HOME");
    }

    public void pay(MouseEvent mouseEvent) {
        rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        boolean res;
        String toDisplay = "There isn't any memorized payment method!";
        PaymentType payment = PaymentType.PAYPAL;

        Node source = (Node) mouseEvent.getSource();
        if(source instanceof Button paymentType) {
            payment = PaymentType.fromString(paymentType.getId());
        } else if (source instanceof SVGPath) {
            payment = PaymentType.fromString(payment.getId());
        }

        PaymentApplicativeController toPay = new PaymentApplicativeController();
        PaymentBean payBean;

        if(paymetCheckbox.isSelected() && Objects.requireNonNull(payment).getId().equals(PaymentType.CARD.getId())) {
            res = checkPayment();
            if(!res) {
                alert(toDisplay, rootToDisplay);
                return;
            }
        } else if(Objects.requireNonNull(payment).getId().equals(PaymentType.CARD.getId())) {
            payBean = new PaymentBean(nameField.getText(),
                                      lastNameField.getText(),
                                      expirationField.getText(),
                                      cardNumberField.getText(),
                                      cvvField.getText(),
                                      zipField.getText());

            res = checkPayment();

            if(!res) {
                res = displayConfirmBox("Do you want to save your payment method?", "yes", "no");
                if(res) {
                    try {
                        toPay.insertPayment(payBean, SessionUser.getInstance().getThisUser().getEmail());
                    } catch(SQLException e) {
                        Logger.getAnonymousLogger().log(Level.INFO, "DB error");
                    } catch (EmptyInputException e) {
                        alert(e.getMessage(), rootToDisplay);
                        return;
                    }
                }
            }
        }

        try {
            toPay.createTransaction(SessionUser.getInstance().getThisUser().getEmail(), payment.getId());
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO,"DB error");
        }
    }

    private boolean checkPayment() {
        PaymentApplicativeController toPay = new PaymentApplicativeController();
        boolean res = false;

        try {
            res = toPay.checkCustomerPayment(SessionUser.getInstance().getThisUser().getEmail());
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        return res;
    }
}
