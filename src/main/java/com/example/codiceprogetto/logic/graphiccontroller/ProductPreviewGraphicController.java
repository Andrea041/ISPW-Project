package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.bean.ProductStockBean;
import com.example.codiceprogetto.logic.utils.Utilities;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductPreviewGraphicController extends Utilities {
    @FXML
    private Label prodName;
    @FXML
    private Label prodPrice;
    @FXML
    private Label prodId;
    @FXML
    private ImageView image;
    private final ProductStockBean prod;
    SelectProductGraphicController selProd;

    protected ProductPreviewGraphicController(ProductStockBean prod) {
        this.prod = prod;
    }

    @FXML
    void initialize() throws FileNotFoundException {
        prodName.setText(prod.getProductName());
        prodPrice.setText(round(prod.getPrice(), 2) + "€");
        prodId.setText(prod.getLabelID());


        Image prodImage = new Image(new FileInputStream(prod.getProdImage()));
        image.setImage(prodImage);
    }

    public void selectProduct() {
        Parent root;
        selProd = new SelectProductGraphicController(prod);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(COBRA));
        fxmlLoader.setController(selProd);

        try {
            root = fxmlLoader.load();
            navigateToAlternative(root);
        } catch(IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
        }

    }
}
