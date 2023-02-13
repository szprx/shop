package com.dglazewski.shop.gui.everyone.view;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.seciurity.SecurityService;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.api.service.ProductService;
import com.dglazewski.shop.gui.everyone.components.AppLayoutDrawer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "product/all", layout = AppLayoutDrawer.class)
@PageTitle("Products")
public class ProductsView extends VerticalLayout {

    private final String role = SecurityService.getCurrentUserRole();

    public ProductsView(ProductService productService, CustomerService customerService, SecurityService securityService) {


        VirtualList<Product> list = new VirtualList<>();
        UI.getCurrent().getPage().retrieveExtendedClientDetails(extendedClientDetails -> list.setHeight(extendedClientDetails.getWindowInnerHeight() - 100 + "px"));
        list.setItems(productService.getAllProducts().getEntity());
        list.setRenderer(new ComponentRenderer<>(product -> {
            HorizontalLayout cardLayout = new HorizontalLayout();
            cardLayout.setMargin(true);

            Button button = new Button("Add to card");
            button.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
            button.addClickListener(buttonClickEvent -> {
                if (role.equals("ROLE_ADMIN")) {
                    Notification.show("You are logged in as ADMIN, action forbidden");
                } else if (role.equals("ROLE_CUSTOMER")) {
                    Customer customer = customerService.getCustomer(securityService.getAuthenticatedUser().getUsername()).getEntity();
                    DataBaseStatusResponse<Customer> response = customerService.addProductToCustomerCard(customer.getUser().getEmail(), product);
                    if (response.getStatus() != Status.RECORD_CREATED_SUCCESSFULLY) {
                        Notification.show("Something gone wrong, try later");
                    }
                    Notification.show(product.getName() + " added to your card");
                } else {
                    button.getUI().ifPresent(ui ->
                            ui.navigate("login"));
                }
//todo somethiong is wrong, think abou just notification taht there are not available products
            });
            button.setWidth("150px");
            if (product.getAmount() < 0) {
                button.setText("Notify about availability");
                button.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
                button.addClickListener(buttonClickEvent -> {
                    if (role.equals("ROLE_ADMIN")) {
                        Notification.show("You are logged in as ADMIN, action forbidden");
                    } else if (role.equals("ROLE_CUSTOMER")) {
                        Notification.show("Sorry, not implemented yet");
                        //todo add maioling about availability, maybe using rabbitMq idk shceduler or something
                    } else {
                        button.getUI().ifPresent(ui ->
                                ui.navigate("login"));
                    }
                });
            }

            HorizontalLayout activities = new HorizontalLayout(button);
            activities.setAlignItems(Alignment.CENTER);

            Image image = new Image(product.getImageUrl(), "no image available");
            image.setWidth(150, Unit.PIXELS);
            image.setHeight(150, Unit.PIXELS);

            VerticalLayout infoLayout = new VerticalLayout();
            infoLayout.setSpacing(false);
            infoLayout.setPadding(false);
            infoLayout.getElement().appendChild(ElementFactory.createStrong(product.getName()));
            infoLayout.add(new Div(new Text("Price per kilogram " + product.getPrice() + "$")));

            VerticalLayout contactLayout = new VerticalLayout();
            contactLayout.setSpacing(false);
            contactLayout.setPadding(false);
            contactLayout.add(new Div(new Text("Still available " + product.getAmount() + " kilograms")));
            infoLayout.add(new Details("More information", contactLayout));

            cardLayout.add(image, infoLayout, activities);
            return cardLayout;
        }));
        add(list);
    }
}