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
                alert(errorToDisplay, rootToDisplay);
            } else if (ret == 1){
                switch(SessionUser.getInstance().getThisUser().getUserType()) {
                    case "CUSTOMER":
                        navigateTo(mouseEvent, "HOME");
                        break;
                    case "SELLER":
                        //navigateTo(mouseEvent, "INBOX");
                        break;
                    default:
                        break;
                }
            } else
                alert(errorToDisplay, rootToDisplay);
        } catch (SQLException e) {
            alert(errorToDisplay, rootToDisplay);
        } catch (AlreadyLoggedUserException | EmptyInputException e) {
            alert(e.getMessage(), rootToDisplay);
        }
    }
    public void singupGUI(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "SIGNUP");
    }
}
