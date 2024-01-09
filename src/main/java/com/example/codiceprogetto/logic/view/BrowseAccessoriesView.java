package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class BrowseAccessoriesView{
    private Parent root;
    public BrowseAccessoriesView() throws Exception {
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/BrowseAccessories/BrowseProduct.fxml"));
    }
    public Parent getAccessoriesView(){
        return this.root;
    }
}
