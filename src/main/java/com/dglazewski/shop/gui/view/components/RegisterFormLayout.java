package com.dglazewski.shop.gui.view.components;

import com.dglazewski.shop.gui.validator.RegisterFormLayoutValidator;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

@Getter
public class RegisterFormLayout extends FormLayout {

    //FIELD
    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private EmailField emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;

    //VALIDATOR
    private RegisterFormLayoutValidator registerValidator;

    public RegisterFormLayout() {
        //FIELD
        this.firstNameTextField = new TextField("First name");
        this.lastNameTextField = new TextField("Last name");
        this.emailField = new EmailField("Email");
        this.passwordField = new PasswordField("Password");
        this.confirmPasswordField = new PasswordField("Confirm password");

        //FIELD CONFIG
        this.passwordField.setHelperText("Minimum 6 chars");

        setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );
        setColspan(emailField, 2);

        //VALIDATOR
        this.registerValidator = new RegisterFormLayoutValidator();

        add(
                firstNameTextField, lastNameTextField,
                emailField,
                passwordField, confirmPasswordField
        );
    }

    public boolean isValid() {
        return !firstNameTextField.isInvalid() && !lastNameTextField.isInvalid() && !emailField.isInvalid() && !passwordField.isInvalid();
    }

    public void validate() {
        registerValidator.validateName(firstNameTextField);
        registerValidator.validateName(lastNameTextField);
        registerValidator.validateEmail(emailField);
        registerValidator.validatePassword(passwordField, confirmPasswordField);

    }
}