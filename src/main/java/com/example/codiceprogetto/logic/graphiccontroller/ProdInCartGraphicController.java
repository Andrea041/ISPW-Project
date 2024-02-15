package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.ProdInCartApplicativeController;
import com.example.codiceprogetto.logic.bean.ProductInCartBean;
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
import java.io.FileNotFoundException;
import java.sql.SQLException;
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
    ProdInCartApplicativeController prodBox = new ProdInCartApplicativeController();
    String prodID;

    public ProdInCartGraphicController(String prodID) {
        super();
        this.prodID = prodID;
    }

    @FXML
    void initialize() {
        updateGUI();
    }

    public void removeUnits() {
        int res;

        counter--;
        if(counter < 1)
            counter = 1;
        changeQuantity.setText(Integer.toString(counter));

        try {
            res = prodBox.changeUnits(labelID.getText(), "DELETE");
            if(res <= 0)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");
        } catch(TooManyUnitsExcpetion e) {
            alert(e.getMessage());
        } catch(SQLException | DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        notifyObserver();
        updateGUI();
    }

    public void addUnits() {
        int res;

        counter++;
        if(counter > 10)
            counter = 10;
        changeQuantity.setText(Integer.toString(counter));

        try {
            res = prodBox.changeUnits(labelID.getText(), "ADD");
            if(res <= 0)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");
        } catch(TooManyUnitsExcpetion e) {
            alert(e.getMessage());
        } catch(SQLException | DAOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }

        notifyObserver();
        updateGUI();
    }

    public void removeProd() {
        int res;

        try {
            res = prodBox.removeProduct(labelID.getText());
            if(res == -1)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");
        } catch(DAOException | TooManyUnitsExcpetion e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }
    }

    public void updateGUI() {
        int selectedUnits;

        ProductInCartBean cartTotal = new ProductInCartBean();

        try {
            cartTotal = prodBox.calculateTotalSingleProd(labelID.getText(), cartTotal);
            if(cartTotal == null) {
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");
                return;
            }

            selectedUnits = prodBox.displaySelectedUnits(labelID.getText());
            if(selectedUnits == -1)
                Logger.getAnonymousLogger().log(Level.INFO, "Unknown error");
            Image image = new Image(new FileInputStream(cartTotal.getProdImage()));

            totalAmountPerProd.setText(round(cartTotal.getTotalAmount(), 2) + "€");
            changeQuantity.setText(String.valueOf(selectedUnits));
            labelID.setText(cartTotal.getLabelID());
            productName.setText(cartTotal.getProductName());
            price.setText(round(cartTotal.getPrice(), 2) + "€");
            prodImage.setImage(image);
            counter = selectedUnits;
        } catch(DAOException | FileNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        } catch(SQLException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "DB error");
        }
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
