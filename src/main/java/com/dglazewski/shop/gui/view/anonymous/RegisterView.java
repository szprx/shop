package com.dglazewski.shop.gui.view.anonymous;

import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.seciurity.SecurityService;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.dglazewski.shop.gui.view.components.RegisterFormLayout;
import com.dglazewski.shop.gui.view.everyone.HomeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
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

    private final String siteUrl = "http://localhost:8080/register";

    //SERVICE
    private final String role = SecurityService.getCurrentUserRole();
    private final CustomerService customerService;

    //FORM LAYOUT
    private final RegisterFormLayout registerFormLayout;

    //BUTTON
    private final Button registerButton;

    public RegisterView(CustomerService customerService) {

        //SERVICE
        this.customerService = customerService;

        //FORM LAYOUT
        this.registerFormLayout = new RegisterFormLayout();

        //BUTTON
        this.registerButton = new Button("Register");
        configRegisterButton();

        add(new Paragraph("Register now!"));

        add(this.registerFormLayout,
                this.registerButton);
    }


    //TODO add verify by email link
    private void configRegisterButton() {
        this.registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.registerButton.addClickListener(buttonClickEvent -> {
            registerFormLayout.validate();
            if (registerFormLayout.isValid()) {

                try {
                    customerService.register(Customer.create(
                            registerFormLayout.getFirstNameTextField().getValue(),
                            registerFormLayout.getLastNameTextField().getValue(),
                            registerFormLayout.getEmailField().getValue(),
                            registerFormLayout.getPasswordField().getValue()), siteUrl);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//                DataBaseStatusResponse<Customer> dataBaseStatusResponse = this.customerService.addCustomer(
//                        Customer.create(
//                                registerFormLayout.getFirstNameTextField().getValue(),
//                                registerFormLayout.getLastNameTextField().getValue(),
//                                registerFormLayout.getEmailField().getValue(),
//                                registerFormLayout.getPasswordField().getValue()));
//                Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
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