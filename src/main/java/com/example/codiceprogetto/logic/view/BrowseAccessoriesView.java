package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.util.Objects;

public class BrowseAccessoriesView{
    private final Parent root;
    public BrowseAccessoriesView() throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/codiceprogetto/FXML/BrowseAccessories/BrowseProduct.fxml")));
    }
    public Parent getAccessoriesView(){
        return this.root;
    }
}
