package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.SignupApplicativeController;
import com.example.codiceprogetto.logic.bean.SignupBean;
import com.example.codiceprogetto.logic.utils.GraphicTool;

import com.example.codiceprogetto.logic.view.HomePageView;
import com.example.codiceprogetto.logic.view.LoginView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SignupGraphicController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passTextField;
    @FXML
    private PasswordField passTextFieldConfirm;
    public void loginGUI(MouseEvent mouseEvent) throws Exception {
        Parent loginView = new LoginView().getLoginView();
        GraphicTool.navigateTo(mouseEvent, loginView);
    }
    public void signUp(MouseEvent mouseEvent) throws Exception {
        Stage rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        int ret;

        if(passTextFieldConfirm.getText().equals(passTextField.getText()) && !passTextField.getText().isEmpty() && !passTextFieldConfirm.getText().isEmpty()){
            SignupBean signBean = new SignupBean(emailField.getText(), passTextField.getText());
            SignupApplicativeController signup = new SignupApplicativeController();

            // check for user type by the email
            int startIndex = emailField.getText().indexOf('@');
            int endIndex = emailField.getText().indexOf('.', startIndex);
            if(startIndex == -1 || endIndex == -1) {
                GraphicTool.alert("Wrong email format (****@****.com)", rootToDisplay);
                cleanUpField();
            }
            else {
                ret = signup.signupUser(signBean);
                if(ret == -1) {
                    GraphicTool.alert("User alredy exist", rootToDisplay);
                } else {
                    Parent homeView = new HomePageView().getHomeView();
                    GraphicTool.navigateTo(mouseEvent, homeView);
                }
            }
        } else if(passTextField.getText().isEmpty() || passTextFieldConfirm.getText().isEmpty() || emailField.getText().isEmpty()) {
            GraphicTool.alert("Fields must not be empty", rootToDisplay);
        } else {
            GraphicTool.alert("Inserted passwords doesn't match", rootToDisplay);
            cleanUpField();
        }
    }
    public void cleanUpField() {
        emailField.setText("");
        passTextField.setText("");
        passTextFieldConfirm.setText("");
    }
}
