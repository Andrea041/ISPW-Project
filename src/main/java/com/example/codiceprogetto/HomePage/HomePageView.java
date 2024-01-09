package com.example.codiceprogetto.HomePage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageView{
    private Parent root;
    public HomePageView() throws Exception{
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/HomePage/HomePage.fxml"));
    }
    public Parent getHomeView(){
        return this.root;
    }
}
