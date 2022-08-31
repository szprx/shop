package com.dglazewski.shop.gui.everyone.components;


import com.dglazewski.shop.api.seciurity.SecurityService;
import com.dglazewski.shop.gui.everyone.view.ProductsView;
import com.dglazewski.shop.gui.user.view.NotificationsView;
import com.dglazewski.shop.gui.user.view.OrdersUserView;
import com.dglazewski.shop.gui.user.view.ShoppingCardView;
import com.dglazewski.shop.gui.user.view.UserAccountView;
import com.dglazewski.shop.gui.everyone.view.HomeView;
import com.dglazewski.shop.gui.everyone.view.LoginView;
import com.dglazewski.shop.gui.admin.view.OrdersAdminView;
import com.dglazewski.shop.gui.admin.view.ProductAddAdminView;
import com.dglazewski.shop.gui.admin.view.ProductsMenageAdminView;
import com.dglazewski.shop.gui.anonymous.view.RegisterView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppLayoutDrawer extends AppLayout {

    private Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    private String roleEnum = auth.getAuthorities().stream().toList().get(0).toString();

    private SecurityService securityService;


    public AppLayoutDrawer(SecurityService securityService) {
        this.securityService = securityService;
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Spring Vaadin Shop Online " + roleEnum);
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
        link.setRoute(HomeView.class);
        link.setTabIndex(1);
        tabs.add(new Tab(link));


        icon = VaadinIcon.LINES_LIST.create();//products
        setStyles(icon);
        link = new RouterLink();
        link.add(icon, new Span("Products"));
        link.setRoute(ProductsView.class);
        link.setTabIndex(-1);
        tabs.add(new Tab(link));

        if (this.roleEnum == "ROLE_ADMIN") {
            icon = VaadinIcon.EDIT.create();//manage product
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Manage products"));
            link.setRoute(ProductsMenageAdminView.class);
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


        if (this.roleEnum == "ROLE_CUSTOMER") {
            icon = VaadinIcon.CART.create();//shopping card
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

        if (this.roleEnum == "ROLE_ANONYMOUS") {

            icon = VaadinIcon.CLIPBOARD_USER.create();//login
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Login"));
            link.setRoute(LoginView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));


            icon = VaadinIcon.CLIPBOARD_TEXT.create();//register
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Register"));
            link.setRoute(RegisterView.class);
            link.setTabIndex(-1);
            tabs.add(new Tab(link));
        }
        if (this.roleEnum != "ROLE_ANONYMOUS") {
            Tab logOutTab = new Tab();
            icon = VaadinIcon.EXIT.create();//log out
            setStyles(icon);
            link = new RouterLink();
            link.add(icon, new Span("Log out"));
            link.setRoute(HomeView.class);
            link.setTabIndex(-1);
            logOutTab.add(link);
            logOutTab.getElement().addEventListener("click", event -> this.securityService.logout());
            tabs.add(logOutTab);
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