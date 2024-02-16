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
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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
    CheckoutApplicativeController cOut;

    @FXML
    void initialize() {
        updateLabel();
        shipping.setText(0.0 + "€");
    }

    public void back() {
        navigateTo(CART);
    }

    public void accountGUI() {
        SessionUser.getInstance().logout();
        navigateTo(LOGIN);
    }

    public void cartGUI() {
        navigateTo(CART);
    }

    public void gotoPaymentGUI() {
        boolean res;
        String toDisplay = "There isn't any memorized address!";
        cOut = new CheckoutApplicativeController();

        AddressBean address = null;

        res = checkAddress();

        if(memoAddress.isSelected() && !res) {
            alert(toDisplay);
            return;
        } else if(!memoAddress.isSelected()) {
            address = new AddressBean(stateField.getText(),
                                      cityField.getText(),
                                      countryField.getText(),
                                      phoneNumberField.getText(),
                                      nameField.getText(),
                                      lastNameField.getText(),
                                      addressField.getText());

            try {
                cOut.checkEmptyFieldAddress(address);
            } catch (EmptyInputException e) {
                alert(e.getMessage());
                return;
            }

            if(!res) {
                res = displayConfirmBox("Do you want to save your delivery address?", "yes", "no");
                if(res) {
                    try {
                        cOut.insertAddress(address, SessionUser.getInstance().getThisUser().getEmail());
                    } catch(SQLException e) {
                        Logger.getAnonymousLogger().log(Level.INFO, "DB error");
                    }
                }
            }
        }

        try {
            cOut.createOrder(address, SessionUser.getInstance().getThisUser().getEmail());
            navigateTo(PAY);
        } catch (DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }
    }

    public void couponCheck() {
        CouponBean coupon = new CouponBean(couponText.getText());
        cOut = new CheckoutApplicativeController();

        try {
            cOut.checkCouponCode(coupon);
        } catch(DAOException | AlreadyAppliedCouponException e) {
            alert(e.getMessage());
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        updateLabel();
    }

    public void couponDelete() {
        String toDisplay = "Coupon removed!";
        cOut = new CheckoutApplicativeController();

        try {
            cOut.removeCoupon();
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        updateLabel();

        alert(toDisplay);
    }

    public void checkShipping() {
        ShippingBean shipping = new ShippingBean(0);
        cOut = new CheckoutApplicativeController();

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
        cOut = new CheckoutApplicativeController();

        try {
            cart = cOut.calculateTotal(cart);
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        } catch(DAOException e) {
            alert(e.getMessage());
        }

        subtotal.setText(round(cart.getSubtotal(), 2) + "€");
        total.setText(round(cart.getTotal(),2) + "€");
        tax.setText(round(cart.getTax(),2) + "€");
        discount.setText(round(cart.getDiscount(),2) + "€");
        shipping.setText(round(cart.getShipping(),2) + "€");
    }

    private boolean checkAddress() {
        boolean res = false;

        try {
            res = cOut.checkCustomerAddress(SessionUser.getInstance().getThisUser().getEmail());
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        return res;
    }
}
