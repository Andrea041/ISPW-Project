package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.ProdInCartApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.TooManyUnitsExcpetion;
import com.example.codiceprogetto.logic.observer.Observer;
import com.example.codiceprogetto.logic.observer.Subject;
import com.example.codiceprogetto.logic.utils.GraphicTool;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdInCartGraphicController extends GraphicTool implements Subject {
    @FXML
    private Label totalAmountPerProd;
    @FXML
    private Label labelID;
    @FXML
    private Label productName;
    @FXML
    private Label price;
    @FXML
    private TextField changeQuantity;
    @FXML
    private ImageView prodImage;
    private int counter = 0;
    ProdInCartApplicativeController prodBox;
    private final String prodID;
    private final List<Observer> observers = new ArrayList<>();
    private static final String ERROR = "Unknown error";

    public ProdInCartGraphicController(String prodID) {
        super();
        this.prodID = prodID;
    }

    @FXML
    void initialize() {
        updateGUI();
    }

    public void removeUnits() {
        changeUnit("DELETE");
    }

    public void addUnits() {
        changeUnit("ADD");
    }

    public void removeProd() {
        performAction("REMOVE", "Product removed successfully!");
    }

    public void updateGUI() {
        performAction("UPDATE", null);
    }

    private void changeUnit(String action) {
        try {
            int res = prodBox.changeUnits(labelID.getText(), action);
            if (res <= 0) {
                Logger.getAnonymousLogger().log(Level.INFO, ERROR);
            } else {
                counter += (action.equals("ADD")) ? 1 : -1;
                counter = Math.min(Math.max(counter, 0), 10);
                changeQuantity.setText(Integer.toString(counter));
            }
        } catch (TooManyUnitsExcpetion | SQLException | DAOException e) {
            handleException(e);
        }

        notifyObserver();
        updateGUI();
    }

    private void performAction(String action, String successMessage) {
        try {
            int res;
            if (action.equals("REMOVE")) {
                res = prodBox.removeProduct(labelID.getText());
                if (res == -1) {
                    Logger.getAnonymousLogger().log(Level.INFO, ERROR);
                }
            } else if (action.equals("UPDATE")) {
                updateGUIComponents();
            }

            if (successMessage != null) {
                alert(successMessage);
            }
        } catch (DAOException | SQLException | TooManyUnitsExcpetion e) {
            handleException(e);
        }

        notifyObserver();
    }

    private void updateGUIComponents() {
        try {
            ProductStockBean cartTotal = prodBox.updateUI(prodID, new ProductStockBean());
            if (cartTotal == null) {
                Logger.getAnonymousLogger().log(Level.INFO, ERROR);
                return;
            }

            int selectedUnits = prodBox.displaySelectedUnits(prodID);
            if (selectedUnits == -1) {
                Logger.getAnonymousLogger().log(Level.INFO, ERROR);
                return;
            }

            Image image = new Image(new FileInputStream(cartTotal.getProdImage()));

            totalAmountPerProd.setText(round(cartTotal.getTotalAmount() * selectedUnits, 2) + "€");
            changeQuantity.setText(String.valueOf(selectedUnits));
            labelID.setText(cartTotal.getLabelID());
            productName.setText(cartTotal.getProductName());
            price.setText(round(cartTotal.getPrice(), 2) + "€");
            prodImage.setImage(image);
            counter = selectedUnits;
        } catch (DAOException | SQLException | IOException e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
    }

    public void attach(Observer o) {
        observers.add(o);
    }
    public void detach(Observer o) {
        observers.remove(o);
    }
    public void notifyObserver() {
        for(Observer o : observers) {
            o.update();
        }
    }
}
