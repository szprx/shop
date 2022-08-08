package com.dglazewski.shop.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route("login")
@PageTitle("Login | Vaadin")
public class LoginView extends VerticalLayout {

    public LoginView() {
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form loginI18nForm = i18n.getForm();
        loginI18nForm.setTitle("Hello user");
        loginI18nForm.setUsername("Username");
        loginI18nForm.setPassword("Password");
        loginI18nForm.setSubmit("Log in");
        loginI18nForm.setForgotPassword("Forget password?");
        i18n.setForm(loginI18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Wrong username or password.");
        i18nErrorMessage.setMessage("Check that the username and password are correct and try again.");
        i18n.setErrorMessage(i18nErrorMessage);

        LoginForm loginForm = new LoginForm();
        loginForm.setI18n(i18n);
        loginForm.setAction("login");

        setSizeFull();
        setAlignItems(Alignment.CENTER); // vertical centering for HorizontalLayouts
        setJustifyContentMode(JustifyContentMode.CENTER); // horizontal centering for HorizontalLayouts


        add(loginForm);

        //*************************CONNECT TO SPRING SECURITY*************************
        //button
        //https://vaadin.com/docs/latest/tutorial/components-and-layouts
        //security
        //https://vaadin.com/docs/latest/tutorial/login-and-authentication

    }
}
