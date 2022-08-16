package com.dglazewski.shop.gui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = AppLayoutDrawer.class)
@PageTitle("HOME")
public class HomePage extends VerticalLayout {
    public HomePage() {
    }
}
