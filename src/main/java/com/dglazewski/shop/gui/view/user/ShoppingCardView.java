package com.dglazewski.shop.gui.view.user;

import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;


@Route(value = "user/shopping-card", layout = AppLayoutDrawer.class)
@PageTitle(" Shopping Card | User")
@RolesAllowed("CUSTOMER")
public class ShoppingCardView extends VerticalLayout {
}
