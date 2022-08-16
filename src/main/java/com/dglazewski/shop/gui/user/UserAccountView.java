package com.dglazewski.shop.gui.user;

import com.dglazewski.shop.api.entity.Order;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.gui.AppLayoutDrawer;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.details.Details;
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
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "myAccount", layout = AppLayoutDrawer.class)
@PageTitle("My account | USER")
public class UserAccountView extends Div {

    private final Tab profile;
    private final Tab settings;
    private final Tab notifications;

    private final VerticalLayout content;
    private final CustomerService customerService;

    //
    private final ComponentRenderer<Component, Product> personCardRenderer = new ComponentRenderer<>(product -> {
        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);

        Avatar avatar = new Avatar(product.getName(), product.getImageUrl());
        avatar.setHeight("64px");
        avatar.setWidth("64px");

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);
        infoLayout.getElement().appendChild(ElementFactory.createStrong(product.getName()));
        infoLayout.add(new Div(new Text(String.valueOf(product.getPrice()))));

        VerticalLayout detailsLayout = new VerticalLayout();
        detailsLayout.setSpacing(false);
        detailsLayout.setPadding(false);
        detailsLayout.add(new Div(new Text(product.getName())));
        detailsLayout.add(new Div(new Text(String.valueOf(product.getAmount()))));
        infoLayout.add(new Details("More information", detailsLayout));

        cardLayout.add(avatar, infoLayout);
        return cardLayout;
    });


    //

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
        notifications = new Tab(
                VaadinIcon.BELL.create(),
                new Span("Notifications")
        );

        // Set the icon on top
        for (Tab tab : new Tab[]{profile, settings, notifications}) {
            tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        }

        Tabs tabs = new Tabs(profile, settings, notifications);
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
        } else {
            content.add(new Paragraph("This is the Notifications tab"));
        }
    }

    private Div getProfileDiv() {
        Div div = new Div();

        //2 lub 3 divy w jednej sekcji
        //a zamiast divow to bym zrobil nawet verticalLayout bo lepiej idzie to srodkowac
        //wykminic co trza tu wrzucic

        Div profileDataDiv = new Div();
        Div profileOrderHistory = new Div();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPadding(true);
        horizontalLayout.add(profileDataDiv, profileOrderHistory);

        div.add(new Paragraph("This is the profile tab"));


        //TODO dodac przekzywanie kokretnego usera badz jego id aby pobierac jego szczegolowe dane

        TextField firstName = new TextField("First name");
        firstName.setReadOnly(true);
        firstName.setValue("Jan");
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
        profileOrderHistory.add(new Paragraph("Order history"));

//TODO: pass actual id using idk cookie?
        List<Order> orders = customerService.getCustomer(1L).getEntity().getOrders();
        VirtualList<Order> list = new VirtualList<>();
        list.setItems(orders);
        list.setRenderer((ValueProvider<Order, String>) personCardRenderer);
        profileOrderHistory.add(list);

        div.add(horizontalLayout);

        return div;
    }
}
