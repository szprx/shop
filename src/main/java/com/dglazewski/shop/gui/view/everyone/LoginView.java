package com.dglazewski.shop.gui.view.everyone;

import com.dglazewski.shop.api.seciurity.SecurityService;
import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "login", layout = AppLayoutDrawer.class)
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    //SERVICE
    private final String role = SecurityService.getCurrentUserRole();

    public LoginView() {
        addClassName("login-view");
        //TODO change to LoginOverlay
        // https://vaadin.com/docs/latest/components/login#validation
        // https://vaadin.com/api/platform/23.1.6/com/vaadin/flow/component/login/LoginOverlay.html

        getStyle()
                .set("background-color", "var(--lumo-contrast-5pct)")
                .set("display", "flex")
                .set("justify-content", "center")
                .set("padding", "var(--lumo-space-l)");

        LoginI18n i18n = LoginI18n.createDefault();


        LoginI18n.Form loginI18nForm = i18n.getForm();
        loginI18nForm.setTitle("Hello user");
        loginI18nForm.setUsername("Email");
        loginI18nForm.setPassword("Password");
        loginI18nForm.setSubmit("Log in");
        loginI18nForm.setForgotPassword("Forget password?");
        i18n.setForm(loginI18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("Wrong email or password.");
        i18nErrorMessage.setMessage("Check that the email and password are correct and try again.");
        i18n.setErrorMessage(i18nErrorMessage);

        LoginForm loginForm = new LoginForm();
        loginForm.setI18n(i18n);
        loginForm.setAction("login");
        loginForm.addClassName("login-form");

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

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!"ROLE_ANONYMOUS".equals(role)) {
            beforeEnterEvent.forwardTo(HomeView.class);
        }
    }
}
