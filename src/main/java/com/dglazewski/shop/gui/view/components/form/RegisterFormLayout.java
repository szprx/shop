package com.dglazewski.shop.gui.view.components.form;

import com.dglazewski.shop.gui.view.components.validator.RegisterFormLayoutValidator;
import com.dglazewski.shop.gui.view.components.validator.Validatable;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

@Getter
public class RegisterFormLayout extends FormLayout implements Validatable {

    //FIELD
    private final TextField firstNameTextField;
    private final TextField lastNameTextField;
    private final EmailField emailField;
    private final PasswordField passwordField;
    private final PasswordField confirmPasswordField;

    //VALIDATOR
    private final RegisterFormLayoutValidator registerValidator;

    public RegisterFormLayout() {
        //FIELD
        this.firstNameTextField = new TextField("First name");
        this.lastNameTextField = new TextField("Last name");
        this.emailField = new EmailField("Email");
        this.passwordField = new PasswordField("Password");
        this.confirmPasswordField = new PasswordField("Confirm password");

        //FIELD CONFIG
        this.passwordField.setHelperText("Minimum 6 chars");

        configFormLayout();

        //VALIDATOR
        this.registerValidator = new RegisterFormLayoutValidator();

        add(
                firstNameTextField, lastNameTextField,
                emailField,
                passwordField, confirmPasswordField
        );
    }

    private void configFormLayout() {
        setResponsiveSteps(
                new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 2)
        );
        setColspan(emailField, 2);
    }

    @Override
    public boolean isValid() {
        validate();
        return !firstNameTextField.isInvalid() && !lastNameTextField.isInvalid() && !emailField.isInvalid() && !passwordField.isInvalid();
    }

    private void validate() {
        registerValidator.validateName(firstNameTextField);
        registerValidator.validateName(lastNameTextField);
        registerValidator.validateEmail(emailField);
        registerValidator.validatePassword(passwordField, confirmPasswordField);
    }
}