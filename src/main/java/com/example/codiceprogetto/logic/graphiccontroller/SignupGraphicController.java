package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.SignupApplicativeController;
import com.example.codiceprogetto.logic.bean.SignupBean;
import com.example.codiceprogetto.logic.enumeration.UserType;
import com.example.codiceprogetto.logic.exception.AlreadyExistingUserException;
import com.example.codiceprogetto.logic.exception.AlreadyLoggedUserException;
import com.example.codiceprogetto.logic.exception.DAOException;
import com.example.codiceprogetto.logic.exception.EmptyInputException;
import com.example.codiceprogetto.logic.utils.GraphicTool;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void loginGUI() {
        navigateTo(LOGIN);
    }
    public void signUp() {
        String errorToDisplay = "Signup error";
        SignupBean signBean;

        if(passTextFieldConfirm.getText().equals(passTextField.getText())){
            signBean = new SignupBean(emailField.getText(), passTextField.getText(), name.getText(), surname.getText());
            SignupApplicativeController signup = new SignupApplicativeController();

            signBean.setUserType(
                    checkBox.isSelected() && keySignUp.getText().equals(KEY) ?
                            UserType.SELLER.getId() :
                            UserType.CUSTOMER.getId()
            );

            if (checkBox.isSelected() && !keySignUp.getText().equals(KEY)) {
                alert("Wrong key");
                keySignUp.clear();
            }


            try {
                int ret = signup.signupUser(signBean);
                if (ret != 1){
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
            }  catch (EmptyInputException | AlreadyExistingUserException | DAOException | AlreadyLoggedUserException e) {
                alert(e.getMessage());
            }
        } else {
            alert("Inserted passwords doesn't match");
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
