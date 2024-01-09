package com.example.codiceprogetto.LoggingForm;

import com.example.codiceprogetto.utils.GraphicTool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginGraphicController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passTextField;
    public void redirectGUI(ActionEvent actionEvent) {
        LoginBean passLogArg = new LoginBean(emailField.getText(), passTextField.getText());
        LoginApplicativeController loginApp = new LoginApplicativeController(passLogArg);
    }

    public void singupGUI(MouseEvent mouseEvent) throws Exception {
        Parent signupView = new SignupView().getSignupView();
        GraphicTool.navigateTo(mouseEvent, signupView);
    }
}
