package com.dglazewski.shop.gui.view.user;

import com.dglazewski.shop.api.seciurity.SecurityService;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "myAccount", layout = AppLayoutDrawer.class)
@PageTitle("My account | USER")
@RolesAllowed("CUSTOMER")
public class UserAccountView extends Div {

    private final Tab profile;
    private final Tab settings;

    private final VerticalLayout content;
    private final CustomerService customerService;
    private final SecurityService securityService = new SecurityService();


    public UserAccountView(CustomerService customerService) {
        this.customerService = customerService;
        profile = new Tab(
                VaadinIcon.USER.create(),
                new Span("Profile")
        );
        settings = new Tab(
                VaadinIcon.COG.create(),
                new Span("Settings")
        );

        // Set the icon on top
        for (Tab tab : new Tab[]{profile, settings}) {
            tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        }

        Tabs tabs = new Tabs(profile, settings);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        tabs.addSelectedChangeListener(event ->
                setContent(event.getSelectedTab())
        );

        content = new VerticalLayout();
        content.setSpacing(false);
        setContent(tabs.getSelectedTab());

        add(tabs, content);
    }

    private void setContent(Tab tab) {
        content.removeAll();

        if (tab.equals(profile)) {
            content.add(getProfileDiv());
        } else if (tab.equals(settings)) {
            content.add(new Paragraph("This is the settings tab"));
        }
    }

    private Div getProfileDiv() {
        Div div = new Div();

        //2 lub 3 divy w jednej sekcji
        //a zamiast divow to bym zrobil nawet verticalLayout bo lepiej idzie to srodkowac
        //wykminic co trza tu wrzucic

        Div profileDataDiv = new Div();
//        Div profileOrderHistory = new Div();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPadding(true);
        horizontalLayout.add(profileDataDiv);

        div.add(new Paragraph("This is the profile tab"));


        //TODO dodac przekzywanie kokretnego usera badz jego id aby pobierac jego szczegolowe dane

        TextField firstName = new TextField("First name");
        firstName.setReadOnly(true);

        //todo zmienic zeby szukalo po czyms innym
        firstName.setValue(customerService.getCustomer(securityService.getAuthenticatedUser().getUsername()).getEntity().getName());
        TextField lastName = new TextField("Last name");
        lastName.setReadOnly(true);
        lastName.setValue("Kowalski");
        EmailField email = new EmailField("Email");
        email.setReadOnly(true);
        email.setValue("jan_kowalski@gmail.com");

        //change password raczej w oddzielnej karcie
//        PasswordField password = new PasswordField("Password");
//        PasswordField confirmPassword = new PasswordField("Confirm password");

        FormLayout formLayout = new FormLayout();
        formLayout.add(
                firstName, lastName,
                email
        );
        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2)
        );
        // Stretch the username field over 2 columns
        formLayout.setColspan(firstName, 2);

        profileDataDiv.add(formLayout);
//        profileOrderHistory.add(new Paragraph("Order history"));


        div.add(horizontalLayout);

        return div;
    }
}
