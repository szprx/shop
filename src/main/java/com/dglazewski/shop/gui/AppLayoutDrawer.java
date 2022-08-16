package com.dglazewski.shop.gui;


import com.dglazewski.shop.api.enums.RoleEnum;
import com.dglazewski.shop.gui.admin.OrdersAdminView;
import com.dglazewski.shop.gui.admin.ProductAddAdminView;
import com.dglazewski.shop.gui.admin.ProductsAdminView;
import com.dglazewski.shop.gui.guest.RegisterView;
import com.dglazewski.shop.gui.user.NotificationsView;
import com.dglazewski.shop.gui.user.OrdersUserView;
import com.dglazewski.shop.gui.user.ShoppingCardView;
import com.dglazewski.shop.gui.user.UserAccountView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;

public class AppLayoutDrawer extends AppLayout {

    private RoleEnum roleEnum = RoleEnum.ADMIN;

    public AppLayoutDrawer() {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Spring Vaadin Shop Online");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

        Tabs tabs = getTabs();

        addToDrawer(tabs);
        addToNavbar(toggle, title);

        DarkTheme darkTheme = new DarkTheme();
        addToDrawer(darkTheme);

        setPrimarySection(Section.DRAWER);
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        Icon icon;
        RouterLink link;

        icon = VaadinIcon.HOME.create();///home page
        setStyles(icon);
        link = new RouterLink();
        link.add(icon, new Span("Home page"));
        link.setRoute(HomePage.class);
        link.setTabIndex(-1);
        tabs.add(new Tab(link));


        icon = VaadinIcon.LINES_LIST.create();//products
        setStyles(icon);
        link = new RouterLink();
        link.add(icon, new Span("Products"));
        link.setRoute(ProductsView.class);
        link.setTabIndex(-1);
        tabs.add(new Tab(link));

        if (this.roleEnum == RoleEnum.ADMIN) {
            icon = VaadinIcon.EDIT.create();//manage product
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Manage products"));
            link.setRoute(ProductsAdminView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));

            icon = VaadinIcon.PLUS.create();//add product
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Add product"));
            link.setRoute(ProductAddAdminView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));

            icon = VaadinIcon.CUBES.create();//admin orders
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Orders"));
            link.setRoute(OrdersAdminView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));
        }


        if (this.roleEnum == RoleEnum.CUSTOMER) {
            icon = VaadinIcon.CART.create();//shoping card
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Shopping Card"));
            link.setRoute(ShoppingCardView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));

            icon = VaadinIcon.BELL_O.create();//notifications
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Notifications"));
            link.setRoute(NotificationsView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));

            icon = VaadinIcon.PACKAGE.create();//order history
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("My orders"));
            link.setRoute(OrdersUserView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));


            icon = VaadinIcon.USER.create();//my profile
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("My account"));
            link.setRoute(UserAccountView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));
        }

        if (this.roleEnum == RoleEnum.ADMIN || this.roleEnum == RoleEnum.CUSTOMER) {
            icon = VaadinIcon.EXIT.create();//log out
//        icon.addClickListener(iconClickEvent -> {
//            //TODO: add logging out
//        });
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Log out"));
            link.setRoute(LoginView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));
        }
        if (this.roleEnum == RoleEnum.GUEST) {
            icon = VaadinIcon.CLIPBOARD_USER.create();//register
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Register"));
            link.setRoute(RegisterView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));
        }

        tabs.setOrientation(Tabs.Orientation.VERTICAL);
        return tabs;
    }

    private void setStyles(Icon icon) {
        icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");
    }
}