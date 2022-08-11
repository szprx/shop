package com.dglazewski.shop.gui.admin;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class AppLayoutDrawerAdmin extends AppLayout {

    public AppLayoutDrawerAdmin() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Dashboard");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, title);

        setPrimarySection(Section.DRAWER);
    }
    // end::snippet[]

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
//                createTab(VaadinIcon.DASHBOARD, "Dashboard"),
//                createTab(VaadinIcon.CART, "Orders"),
//                createTab(VaadinIcon.USER_HEART, "Customers"),

        Icon icon = VaadinIcon.PACKAGE.create();
        icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");

        RouterLink link = new RouterLink();
        link.add(icon, new Span("Products"));
        link.setRoute(ProductsListView.class);
        link.setTabIndex(-1);
        tabs.add(new Tab(link));


        icon = VaadinIcon.CART.create();
        icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");
        link = new RouterLink();
        link.add(icon, new Span("Orders"));
        link.setRoute(ProductAddView.class);
        link.setTabIndex(-1);
        tabs.add(new Tab(link));



//                createTab(VaadinIcon.RECORDS, "Documents"),
//                createTab(VaadinIcon.LIST, "Tasks"),
//                createTab(VaadinIcon.CHART, "Analytics")
        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }


    // tag::snippet[]
}
// end::snippet[]

