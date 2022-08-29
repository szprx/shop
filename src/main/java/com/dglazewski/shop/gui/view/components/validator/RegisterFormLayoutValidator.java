package com.dglazewski.shop.gui.view.components.validator;

import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class RegisterFormLayoutValidator {

    public void validateName(TextField nameTextField) {
        String name = nameTextField.getValue();

        if (name.trim().length() < 2) {
            nameTextField.setInvalid(true);
            nameTextField.setErrorMessage("Name must contain at least two characters");
        }
    }

    public void validateEmail(EmailField emailField) {
        String email = emailField.getValue();

        if (email.trim().length() < 5) {
            emailField.setInvalid(true);
            emailField.setErrorMessage("Email must contain at least five characters");
        }

        if (!(email.contains("@") && email.contains("."))) {
            emailField.setInvalid(true);
            emailField.setErrorMessage("Email must contain '@' and '.'");
        }
    }

    public void validatePassword(PasswordField passwordField, PasswordField confirmPasswordField) {
        String password = passwordField.getValue();
        String confirmPassword = confirmPasswordField.getValue();

        if (password.trim().length() < 6) {
            passwordField.setInvalid(true);
            passwordField.setErrorMessage("Password must contain at least six characters");
        }

        if (!password.equals(confirmPassword)) {
            passwordField.setInvalid(true);
            passwordField.setErrorMessage("Passwords are not the same");
        }
    }
    //TODO add strength checker for password
}