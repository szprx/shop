package com.dglazewski.shop.gui.everyone.view;

import com.dglazewski.shop.gui.everyone.components.AppLayoutDrawer;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "", layout = AppLayoutDrawer.class)
@PageTitle("HOME")
public class HomeView extends VerticalLayout {
    public HomeView() {
        add(new Paragraph("Hello user!"));
        add(new Paragraph("This shop was created using Spring boot and Vaadin. I hope you like it."));
        add(new Image("https://i1.sndcdn.com/avatars-TUe1SAjnzEMNOXS0-D2FRUQ-t240x240.jpg", "piesek"));
    }
}
