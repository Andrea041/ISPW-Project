package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.CheckoutApplicativeController;
import com.example.codiceprogetto.logic.bean.AddressBean;
import com.example.codiceprogetto.logic.bean.CartBean;
import com.example.codiceprogetto.logic.bean.CouponBean;
import com.example.codiceprogetto.logic.bean.ShippingBean;
import com.example.codiceprogetto.logic.exception.AlreadyAppliedCouponException;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.GraphicTool;

import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckoutGraphicController extends GraphicTool {
    @FXML
    private TextField stateField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private CheckBox memoAddress;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField countryField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField couponText;
    @FXML
    private Label total;
    @FXML
    private Label subtotal;
    @FXML
    private Label tax;
    @FXML
    private Label discount;
    @FXML
    private Label shipping;
    @FXML
    private RadioButton threeRadio;
    @FXML
    private RadioButton fiveRadio;
    @FXML
    private RadioButton freeRadio;

    Stage rootToDisplay;

    @FXML
    void initialize() {
        updateLabel();
    }

    public void back(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "CART");
    }

    public void accountGUI(MouseEvent mouseEvent) {
    }

    public void cartGUI(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "CART");
    }

    public void gotoPaymentGUI(MouseEvent mouseEvent) {
        rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        boolean pending = false;
        boolean res;
        String toDisplay = "There isn't any memorized address!";

        CheckoutApplicativeController cOut = new CheckoutApplicativeController();
        AddressBean address = null;

        try {
            pending = cOut.checkPendingOrder(SessionUser.getInstance().getThisUser().getEmail());
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        if(memoAddress.isSelected()) {
            res = checkAddress();
            if(!res) {
                alert(toDisplay, rootToDisplay);
                return;
            }
        } else {
            address = new AddressBean(stateField.getText(), cityField.getText(), countryField.getText(), phoneNumberField.getText(), nameField.getText(), lastNameField.getText(), addressField.getText());

            res = checkAddress();

            if(!res) {
                res = displayConfirmBox("Do you want to save your delivery address?", "yes", "no");
                if(res) {
                    try {
                        cOut.insertAddress(address, SessionUser.getInstance().getThisUser().getEmail());
                    } catch(SQLException e) {
                        Logger.getAnonymousLogger().log(Level.INFO, "DB error");
                    } catch (EmptyInputException e) {
                        alert(e.getMessage(), rootToDisplay);
                        return;
                    }
                }
            }
        }

        if(!pending) {
            try {
                cOut.createOrder(address, SessionUser.getInstance().getThisUser().getEmail());
            } catch (DAOException e) {
                Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
            } catch (SQLException e) {
                Logger.getAnonymousLogger().log(Level.INFO, "DB error");
            }
        }

        navigateTo(mouseEvent, "PAY");
    }

    public void couponCheck(MouseEvent mouseEvent) {
        rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

        CouponBean coupon = new CouponBean(couponText.getText());
        CheckoutApplicativeController cOut = new CheckoutApplicativeController();

        try {
            cOut.checkCouponCode(coupon);
        } catch(DAOException | AlreadyAppliedCouponException e) {
            alert(e.getMessage(), rootToDisplay);
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        updateLabel();
    }

    public void couponDelete(MouseEvent mouseEvent) {
        rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

        CheckoutApplicativeController cOut = new CheckoutApplicativeController();
        String toDisplay = "Coupon removed!";

        try {
            cOut.removeCoupon();
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        updateLabel();

        alert(toDisplay, rootToDisplay);
    }

    public void checkShipping() {
        CheckoutApplicativeController cOut = new CheckoutApplicativeController();
        ShippingBean shipping = new ShippingBean(0);

        if(threeRadio.isSelected())
            shipping.setShippingValue(3);
        else if(fiveRadio.isSelected())
            shipping.setShippingValue(5);
        else if(freeRadio.isSelected())
            shipping.setShippingValue(0);

        try {
            cOut.addShipping(shipping);
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        updateLabel();
    }

    private void updateLabel() {
        CartBean cart = new CartBean();
        CheckoutApplicativeController cOut = new CheckoutApplicativeController();

        try {
            cart = cOut.calculateTotal(cart);
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        } catch(DAOException e) {
            alert(e.getMessage(), rootToDisplay);
        }

        subtotal.setText(round(cart.getSubtotal(), 2) + "€");
        total.setText(round(cart.getTotal(),2) + "€");
        tax.setText(round(cart.getTax(),2) + "€");
        discount.setText(round(cart.getDiscount(),2) + "€");
        shipping.setText(round(cart.getShipping(),2) + "€");
    }

    private boolean checkAddress() {
        CheckoutApplicativeController cOut = new CheckoutApplicativeController();
        boolean res = false;

        try {
            res = cOut.checkCustomerAddress(SessionUser.getInstance().getThisUser().getEmail());
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        return res;
    }
}
