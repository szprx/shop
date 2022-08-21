package com.dglazewski.shop.gui.guest;

import com.dglazewski.shop.gui.AppLayoutDrawer;
import com.dglazewski.shop.gui.HomeView;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "register", layout = AppLayoutDrawer.class)
@PageTitle("Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout implements BeforeEnterObserver {

    private Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    private final String roleEnum = auth.getAuthorities().stream().toList().get(0).toString();

    public RegisterView() {
        add(new Paragraph("Register now!"));

        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmPassword = new PasswordField("Confirm password");

        FormLayout formLayout = new FormLayout();
        formLayout.add(
                firstName, lastName,
                username,
                password, confirmPassword
        );
        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2)
        );
        // Stretch the username field over 2 columns
        formLayout.setColspan(username, 2);
        add(formLayout);


    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (!"ROLE_ANONYMOUS".equals(roleEnum)) {
            beforeEnterEvent.forwardTo(HomeView.class);
        }
    }
}
