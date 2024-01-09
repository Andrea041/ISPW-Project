package com.example.codiceprogetto.LoggingForm;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginView {
    private Parent root;
    public LoginView() throws Exception{
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/LoggingForm/LoginForm.fxml"));
    }
    public Parent getLoginView(){
        return this.root;
    }
}
