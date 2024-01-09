package com.example.codiceprogetto.BrowseProduct;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class BrowseAccessoriesView{
    private Parent root;
    public BrowseAccessoriesView() throws Exception {
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/BrowseAccessories/BrowseProduct.fxml"));
    }
    public Parent getAccessoriesView(){
        return this.root;
    }
}
