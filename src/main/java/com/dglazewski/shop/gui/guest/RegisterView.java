package com.dglazewski.shop.gui.guest;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.seciurity.SecurityService;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.gui.AppLayoutDrawer;
import com.dglazewski.shop.gui.HomeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register", layout = AppLayoutDrawer.class)
@PageTitle("Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout implements BeforeEnterObserver {

    //SERVICE
    private final String role = SecurityService.getCurrentUserRole();
    private final CustomerService customerService;

    //FIELDS
    private final TextField firstName;
    private final TextField lastName;
    private final TextField username;
    private final PasswordField password;
    private final PasswordField confirmPassword;

    private final FormLayout registerFormLayout;

    //BUTTON
    private final Button registerButton;


    public RegisterView(CustomerService customerService) {
        this.customerService = customerService;

        add(new Paragraph("Register now!"));

        //FIELDS
        this.firstName = new TextField("First name");
        this.lastName = new TextField("Last name");
        this.username = new TextField("Username");
        this.password = new PasswordField("Password");
        this.confirmPassword = new PasswordField("Confirm password");

        this.registerFormLayout = new FormLayout();
        configRegisterFormLayout();

        //BUTTON
        this.registerButton = new Button("Register");
        configRegisterButton();

        add(this.registerFormLayout,
                new HorizontalLayout(registerButton));


    }

    private void configRegisterFormLayout() {
        this.registerFormLayout.add(
                firstName, lastName,
                username,
                password, confirmPassword
        );
        this.registerFormLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2)
        );
        // Stretch the username field over 2 columns
        this.registerFormLayout.setColspan(username, 2);
    }

    private void configRegisterButton() {
        this.registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.registerButton.addClickListener(buttonClickEvent -> {


            try {
                DataBaseStatusResponse<Customer> dataBaseStatusResponse = this.customerService.addCustomer(
                        Customer.create(
                                this.firstName.getValue(),
                                this.lastName.getValue(),
                                this.username.getValue(),
                                this.password.getValue()));
                Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                Notification.show("ONE OR MORE OF THE VALUES ARE EMPTY");
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
