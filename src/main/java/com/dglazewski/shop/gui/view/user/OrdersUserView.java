package com.dglazewski.shop.gui.view.user;


import com.dglazewski.shop.api.entity.Order;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "user/orders", layout = AppLayoutDrawer.class)
@PageTitle(" Orders | User")
@RolesAllowed("CUSTOMER")
public class OrdersUserView extends VerticalLayout {
    private final CustomerService customerService;

    public OrdersUserView(CustomerService customerService) {
        this.customerService = customerService;

        VirtualList<Order> list = new VirtualList<>();
        list.setHeight("820px");
        list.setItems(this.customerService.getCustomer(1L).getEntity().getOrders());
//        list.setRenderer(orderCardRender);
        add(list);
        //TODO lista w liscie bo mamy liste zamowienin a w zamowieniu moze byc kilka produkto
        // jaka lista to mozna wykminic uzywajac vaadina
    }

//    private ComponentRenderer<Component, Order> orderCardRender = new ComponentRenderer<>(product -> {
//        HorizontalLayout cardLayout = new HorizontalLayout();
//        cardLayout.setMargin(true);
//
////        Button addToShoppingCardButton = new Button("Add to card");
////        addToShoppingCardButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
////        addToShoppingCardButton.addClickListener(buttonClickEvent -> {
////            //TODO: add adding to shopping card a verification about
////            // logged user and notification about adding product to card
////        });
////        addToShoppingCardButton.setWidth("150px");
//
////        HorizontalLayout activities = new HorizontalLayout(addToShoppingCardButton);
////        activities.setAlignItems(Alignment.CENTER);
//
//        Image image = new Image(product.getImageUrl(), "no image available");
//        image.setWidth(130, Unit.PIXELS);
//        image.setHeight(130, Unit.PIXELS);
//
//        VerticalLayout infoLayout = new VerticalLayout();
//        infoLayout.setSpacing(false);
//        infoLayout.setPadding(false);
//        infoLayout.getElement().appendChild(ElementFactory.createStrong(product.getName()));
//        infoLayout.add(new Div(new Text(String.valueOf("Price per kilogram " + product.getPrice() + "$"))));
//
//        VerticalLayout contactLayout = new VerticalLayout();
//        contactLayout.setSpacing(false);
//        contactLayout.setPadding(false);
//        contactLayout.add(new Div(new Text("Still available " + product.getAmount() + " kilograms")));
//        infoLayout.add(new Details("More information", contactLayout));
//
//        cardLayout.add(image, infoLayout);
//        return cardLayout;
//    });

}
