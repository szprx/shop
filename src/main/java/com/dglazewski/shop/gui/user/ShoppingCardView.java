package com.dglazewski.shop.gui.user;

import com.dglazewski.shop.gui.AppLayoutDrawer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "user/shopping-card", layout = AppLayoutDrawer.class)
@PageTitle(" Shopping Card | User")
public class ShoppingCardView extends VerticalLayout {
}
