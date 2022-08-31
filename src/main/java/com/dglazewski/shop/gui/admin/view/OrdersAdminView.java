package com.dglazewski.shop.gui.admin.view;

import com.dglazewski.shop.gui.everyone.components.AppLayoutDrawer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "admin/order/all", layout = AppLayoutDrawer.class)
@PageTitle("Orders | ADMIN")
@RolesAllowed("ADMIN")
public class OrdersAdminView extends VerticalLayout {

    //SERVICE

    public OrdersAdminView() {
    }

    //TODO: zrobic orders
}
