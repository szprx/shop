package com.dglazewski.shop.gui.user;


import com.dglazewski.shop.gui.AppLayoutDrawer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "user/orders", layout = AppLayoutDrawer.class)
@PageTitle(" Orders | User")
public class OrdersUserView extends VerticalLayout {
}
