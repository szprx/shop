package com.dglazewski.shop.gui.admin;

import com.dglazewski.shop.gui.AppLayoutDrawer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "admin/order/all", layout = AppLayoutDrawer.class)
@PageTitle("Orders | ADMIN")
public class OrdersAdminView extends VerticalLayout {
    public OrdersAdminView() {
    }
}
