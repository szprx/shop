package com.dglazewski.shop.gui.view.anonymous;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.seciurity.SecurityService;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.dglazewski.shop.gui.view.components.RegisterDialog;
import com.dglazewski.shop.gui.view.components.form.RegisterFormLayout;
import com.dglazewski.shop.gui.view.everyone.HomeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@Route(value = "register", layout = AppLayoutDrawer.class)
@PageTitle("Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout implements BeforeEnterObserver {

    private static final String REGISTER_SITE_URL = "http://localhost:8080/register";

    //SERVICE
    private final String role = SecurityService.getCurrentUserRole();
    private final CustomerService customerService;

    //FORM LAYOUT
    private final RegisterFormLayout registerFormLayout;

    //BUTTON
    private final Button registerButton;

    //DIALOG
    private final RegisterDialog registerDialog;

    public RegisterView(CustomerService customerService) {

        //SERVICE
        this.customerService = customerService;

        //FORM LAYOUT
        this.registerFormLayout = new RegisterFormLayout();

        //BUTTON
        this.registerButton = new Button("Register");
        configRegisterButton();

        //DIALOG
        this.registerDialog = new RegisterDialog("Registration successful", "Verification link has been send to your email. Please confirm your account.");

        add(new Paragraph("Register now!"));

        add(this.registerFormLayout,
                this.registerButton,
                this.registerDialog);
    }

    private void configRegisterButton() {
        this.registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.registerButton.addClickListener(buttonClickEvent -> {
            if (registerFormLayout.isValid()) {
                try {
                    DataBaseStatusResponse<Customer> response = customerService.register(Customer.create(
                            registerFormLayout.getFirstNameTextField().getValue(),
                            registerFormLayout.getLastNameTextField().getValue(),
                            registerFormLayout.getEmailField().getValue(),
                            registerFormLayout.getPasswordField().getValue()), REGISTER_SITE_URL);
                    Notification.show(response.getStatus().toString().replace("_", " "));
                    if (response.getStatus()== Status.RECORD_CREATED_SUCCESSFULLY) {
                        this.registerDialog.getDialog().open();
                    }
                } catch (MessagingException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!"ROLE_ANONYMOUS".equals(role)) {
            beforeEnterEvent.forwardTo(HomeView.class);
        }
    }
}