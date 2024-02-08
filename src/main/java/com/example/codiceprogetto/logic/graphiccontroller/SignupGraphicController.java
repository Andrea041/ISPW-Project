package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.SignupApplicativeController;
import com.example.codiceprogetto.logic.bean.SignupBean;
import com.example.codiceprogetto.logic.exception.AlreadyExistingUserException;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.GraphicTool;

import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SignupGraphicController extends GraphicTool{
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passTextField;
    @FXML
    private PasswordField passTextFieldConfirm;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private CheckBox checkBox;
    @FXML
    private PasswordField keySignUp;
    private static final String KEY = "ISPW2324";

    public void loginGUI(MouseEvent mouseEvent) {
        navigateTo(mouseEvent, "LOGIN");
    }
    public void signUp(MouseEvent mouseEvent) {
        Stage rootToDisplay = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();
        int ret;
        String errorToDisplay = "Signup error";

        if(passTextFieldConfirm.getText().equals(passTextField.getText())){
            SignupBean signBean = new SignupBean(emailField.getText(), passTextField.getText(), name.getText(), surname.getText());
            SignupApplicativeController signup = new SignupApplicativeController();

            // check form field syntax
            if(!emailField.getText().isEmpty()) {
                int startIndex = emailField.getText().indexOf('@');
                int endIndex = emailField.getText().indexOf('.', startIndex);
                if (startIndex == -1 || endIndex == -1) {
                    alert("Wrong email format (****@****.com)", rootToDisplay);
                    cleanUpField();
                }
            }

            if(checkBox.isSelected()) {
                if(keySignUp.getText().equals(KEY))
                    signBean.setUserType("SELLER");
                else {
                    alert("Wrong key", rootToDisplay);
                    keySignUp.setText("");
                }
            } else
                signBean.setUserType("CUSTOMER");

            try {
                ret = signup.signupUser(signBean);
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

                } else {
                    alert(errorToDisplay, rootToDisplay);
                }
            } catch (SQLException | AlreadyLoggedUserException e) {
                alert(errorToDisplay, rootToDisplay);
            } catch (EmptyInputException | AlreadyExistingUserException e) {
                alert(e.getMessage(), rootToDisplay);
            }
        } else {
            alert("Inserted passwords doesn't match", rootToDisplay);
            cleanUpField();
        }
    }
    public void cleanUpField() {
        emailField.setText("");
        passTextField.setText("");
        passTextFieldConfirm.setText("");
    }

    public void changeVisibility() {
        keySignUp.setVisible(checkBox.isSelected());
    }
}
