package com.dglazewski.shop.gui.user.view;

import com.dglazewski.shop.gui.everyone.components.AppLayoutDrawer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "user/notifications", layout = AppLayoutDrawer.class)
@PageTitle("Notifications")
public class NotificationsView extends VerticalLayout {
}
