package com.example.codiceprogetto.logic.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SignupView {
    private Parent root;
    public SignupView() throws Exception{
        root = FXMLLoader.load(getClass().getResource("/com/example/codiceprogetto/FXML/LoggingForm/SignUpForm.fxml"));
    }
    public Parent getSignupView(){
        return this.root;
    }
}
