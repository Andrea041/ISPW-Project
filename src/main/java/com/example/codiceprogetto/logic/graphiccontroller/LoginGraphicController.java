package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.bean.LoginBean;
import com.example.codiceprogetto.logic.appcontroller.LoginApplicativeController;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.GraphicTool;

import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginGraphicController extends GraphicTool {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passTextField;
    public void redirectGUI(MouseEvent mouseEvent) {
        Stage rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        int ret;
        String errorToDisplay = "Login error";

        LoginBean passLogArg = new LoginBean(emailField.getText(), passTextField.getText());
        LoginApplicativeController loginApp = new LoginApplicativeController();

        try {
            ret = loginApp.loginUser(passLogArg);
            if (ret == -1) {
                GraphicTool.alert(errorToDisplay, rootToDisplay);
            } else if (ret == 1){
                SessionUser.getInstance().setPage(mouseEvent);
            } else {
                GraphicTool.alert(errorToDisplay, rootToDisplay);
            }
        } catch (SQLException | AlreadyLoggedUserException e) {
            GraphicTool.alert(errorToDisplay, rootToDisplay);
        }
    }
    public void singupGUI(MouseEvent mouseEvent) {
        GraphicTool.navigateTo(mouseEvent, "SIGNUP");
    }
}
