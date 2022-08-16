package com.dglazewski.shop.gui.user;

import com.dglazewski.shop.gui.AppLayoutDrawer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "user/notifications", layout = AppLayoutDrawer.class)
@PageTitle(" Notifications | User")
public class NotificationsView extends VerticalLayout {
}
