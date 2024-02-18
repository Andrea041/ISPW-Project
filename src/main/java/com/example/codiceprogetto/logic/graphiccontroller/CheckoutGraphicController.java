package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.CheckoutApplicativeController;
import com.example.codiceprogetto.logic.bean.AddressBean;
import com.example.codiceprogetto.logic.bean.CartBean;
import com.example.codiceprogetto.logic.bean.CouponBean;
import com.example.codiceprogetto.logic.bean.ShippingBean;
import com.example.codiceprogetto.logic.exception.AlreadyAppliedCouponException;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.exception.NotLoggedUserException;
import com.example.codiceprogetto.logic.utils.Utilities;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CheckoutGraphicController extends Utilities {
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
        cOut = new CheckoutApplicativeController();

        try {
            cOut.logoutUser();
            navigateTo(HOME);
        } catch (NotLoggedUserException e) {
            alert("You are not logged in!");
        }
    }

    public void cartGUI() {
        navigateTo(CART);
    }

    public void gotoPaymentGUI() {
        if (memoAddress.isSelected() && checkAddress()) {
            alert("There isn't any memorized address!");
            return;
        }

        AddressBean address = null;

        if (!memoAddress.isSelected()) {
            address = new AddressBean(stateField.getText(),
                    cityField.getText(),
                    phoneNumberField.getText(),
                    nameField.getText(),
                    lastNameField.getText(),
                    addressField.getText());

            try {
                new CheckoutApplicativeController().checkEmptyFieldAddress(address);
                if (checkAddress() && displayConfirmBox("Do you want to save your delivery address?", "yes", "no")) {
                    new CheckoutApplicativeController().insertAddress(address, SessionUser.getInstance().getThisUser().getEmail());
                }
            } catch (EmptyInputException | DAOException e) {
                alert(e.getMessage());
                return;
            }
        }

        try {
            new CheckoutApplicativeController().createOrder(address, SessionUser.getInstance().getThisUser().getEmail());
            navigateTo(PAY);
        } catch (DAOException | SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
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
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        updateLabel();
    }

    public void couponDelete() {
        String toDisplay = "Coupon removed!";
        cOut = new CheckoutApplicativeController();

        try {
            cOut.removeCoupon();
        } catch(DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        updateLabel();

        alert(toDisplay);
    }

    public void checkShipping() {
        ShippingBean shippingBean = new ShippingBean();
        cOut = new CheckoutApplicativeController();

        if(threeRadio.isSelected())
            shippingBean.setShippingValue(3);
        else if(fiveRadio.isSelected())
            shippingBean.setShippingValue(5);
        else if(freeRadio.isSelected())
            shippingBean.setShippingValue(0);

        try {
            cOut.addShipping(shippingBean);
        } catch(DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        updateLabel();
    }

    private void updateLabel() {
        CartBean cart = new CartBean();
        cOut = new CheckoutApplicativeController();

        try {
            cart = cOut.calculateTotal(cart);
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
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
        } catch (SQLException | DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

        return !res;
    }
}
