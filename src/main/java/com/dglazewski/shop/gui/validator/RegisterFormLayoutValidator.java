package com.dglazewski.shop.gui.validator;

import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class RegisterFormLayoutValidator {

    public TextField validateName(TextField nameTextField) {
        String name = nameTextField.getValue();

        if (name.trim().length() < 2) {
            nameTextField.setInvalid(true);
            nameTextField.setErrorMessage("Name must contain at least two characters");
            return nameTextField;
        }
        return nameTextField;
    }

    public EmailField validateEmail(EmailField emailField) {
        String email = emailField.getValue();

        if (email.trim().length() < 5) {
            emailField.setInvalid(true);
            emailField.setErrorMessage("Email must contain at least five characters");
            return emailField;
        }

        if (!(email.contains("@") && email.contains("."))) {
            emailField.setInvalid(true);
            emailField.setErrorMessage("Email must contain '@' and '.'");
            return emailField;
        }
        return emailField;
    }

    public void validatePassword(PasswordField passwordField, PasswordField confirmPasswordField) {
        String password = passwordField.getValue();
        String confirmPassword = confirmPasswordField.getValue();

        if (password.trim().length() < 6) {
            passwordField.setInvalid(true);
            passwordField.setErrorMessage("Password must contain at least six characters");
//            return passwordField;
        }

        if (!password.equals(confirmPassword)) {
            passwordField.setInvalid(true);
            passwordField.setErrorMessage("Passwords are not the same");
//            return passwordField;
        }
//        return passwordField;
    }
    //TODO add strength checker for password
}