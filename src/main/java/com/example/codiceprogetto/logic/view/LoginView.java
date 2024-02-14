package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginView {
    private final Parent root;
    public LoginView() throws Exception{
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/LoggingForm/LoginForm.fxml"));
    }
    public Parent getLoginView(){
        return this.root;
    }
}
