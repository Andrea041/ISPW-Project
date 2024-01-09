package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class HomePageView{
    private Parent root;
    public HomePageView() throws Exception{
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/HomePage/HomePage.fxml"));
    }
    public Parent getHomeView(){
        return this.root;
    }
}
