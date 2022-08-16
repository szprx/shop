package com.dglazewski.shop.gui.guest;

import com.dglazewski.shop.gui.AppLayoutDrawer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "register", layout = AppLayoutDrawer.class)
@PageTitle("Register")
public class RegisterView extends VerticalLayout {
    public RegisterView() {
    }
}
