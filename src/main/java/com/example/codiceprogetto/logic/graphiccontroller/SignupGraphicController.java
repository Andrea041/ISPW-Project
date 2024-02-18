package com.example.codiceprogetto.logic.graphiccontroller;

import com.example.codiceprogetto.logic.appcontroller.SignupApplicativeController;
import com.example.codiceprogetto.logic.bean.SignupBean;
import com.example.codiceprogetto.logic.enumeration.UserType;
import com.example.codiceprogetto.logic.exception.*;
import com.example.codiceprogetto.logic.utils.Utilities;
import com.example.codiceprogetto.logic.utils.SessionUser;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupGraphicController extends Utilities {
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
        SignupApplicativeController signup = new SignupApplicativeController();

        try {
            if (!passTextFieldConfirm.getText().equals(passTextField.getText())) {
                cleanUpField();
                throw new ControlSquenceException("Inserted passwords don't match");
            }

            if (checkBox.isSelected() && !keySignUp.getText().equals(KEY)) {
                keySignUp.clear();
                throw new ControlSquenceException("Sorry wrong key!");
            }

            signBean = new SignupBean(emailField.getText(), passTextField.getText(), name.getText(), surname.getText());

            signBean.setUserType(checkBox.isSelected() && keySignUp.getText().equals(KEY) ?
                    UserType.SELLER.getId() :
                    UserType.CUSTOMER.getId()
            );

            int ret = signup.signupUser(signBean);

            if (ret != 1) {
                switch (SessionUser.getInstance().getThisUser().getUserType()) {
                    case "CUSTOMER":
                        navigateTo(HOME);
                        break;
                    case "SELLER":
                        navigateTo(ORDER);
                        break;
                    default:
                        break;
                }
            } else {
                alert(errorToDisplay);
            }
        } catch (EmptyInputException | AlreadyExistingUserException | DAOException | AlreadyLoggedUserException | ControlSquenceException e) {
            alert(e.getMessage());
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
