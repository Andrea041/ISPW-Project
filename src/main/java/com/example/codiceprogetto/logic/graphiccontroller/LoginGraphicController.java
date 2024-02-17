package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.LoginApplicativeController;
import com.example.codiceprogetto.logic.bean.LoginBean;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.GraphicTool;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginGraphicController extends GraphicTool {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passTextField;
    public void redirectGUI() {
        int ret;
        String errorToDisplay = "Login error";

        LoginBean passLogArg = new LoginBean(emailField.getText(), passTextField.getText());
        LoginApplicativeController loginApp = new LoginApplicativeController();

        try {
            ret = loginApp.loginUser(passLogArg);
            if (ret == -1) {
                alert(errorToDisplay);
            } else if (ret == 1){
                switch(SessionUser.getInstance().getThisUser().getUserType()) {
                    case "CUSTOMER":
                        navigateTo(HOME);
                        break;
                    case "SELLER":
                        navigateTo(ORDER);
                        break;
                    default:
                        break;
                }
            } else
                alert(errorToDisplay);
        } catch (DAOException e) {
            alert(errorToDisplay);
        } catch (AlreadyLoggedUserException | EmptyInputException e) {
            alert(e.getMessage());
        }
    }
    public void signupGUI() {
        navigateTo(SIGNUP);
    }
}
