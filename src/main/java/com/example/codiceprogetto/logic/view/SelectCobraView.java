package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SelectCobraView {
    private final Parent root;
    public SelectCobraView() throws Exception{
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/SelectCobra/SelectCobra.fxml"));
    }
    public Parent getCobraView(){
        return this.root;
    }
}
