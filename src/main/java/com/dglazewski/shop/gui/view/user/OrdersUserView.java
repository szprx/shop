package com.dglazewski.shop.gui.view.user;


import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.Order;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.seciurity.SecurityService;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "user/orders", layout = AppLayoutDrawer.class)
@PageTitle(" Orders | User")
@RolesAllowed("CUSTOMER")
public class OrdersUserView extends VerticalLayout {
    private final CustomerService customerService;
    private final SecurityService securityService;

    public OrdersUserView(CustomerService customerService, SecurityService securityService) {
        this.customerService = customerService;
        this.securityService = securityService;
        String customerEmail = securityService.getAuthenticatedUser().getUsername();
        Customer customer = customerService.getCustomer(customerEmail).getEntity();

        VirtualList<Order> list = new VirtualList<>();
        list.setHeight("820px");
        list.setItems(customer.getOrders());
        list.setRenderer(new ComponentRenderer<>(order -> {
            HorizontalLayout orderLayout = new HorizontalLayout();
            orderLayout.setMargin(true);
            H1 orderId = new H1(String.valueOf(order.getId()));
            String isActive = order.isActive() ? "active" : "disabled";
            String isPaid = order.isPaid() ? "paid" : "unpaid";

            VerticalLayout orderInfo = new VerticalLayout();
            orderInfo.setSpacing(false);
            orderInfo.setPadding(false);
            orderInfo.getElement().appendChild(ElementFactory.createStrong("order.getOrderDate().toString()"));
            orderInfo.add(new Div(new Text("Order is  " + isPaid + " and " + isActive + ".")));

            VerticalLayout productsFromOrder = new VerticalLayout();
            productsFromOrder.setSpacing(false);
            productsFromOrder.setPadding(false);


            VirtualList<Product> productVirtualList = new VirtualList<>();
            productVirtualList.setItems(order.getProducts());

            productVirtualList.setRenderer(new ComponentRenderer<>(product -> {
                HorizontalLayout productLayout = new HorizontalLayout();
                productLayout.setMargin(true);

                Image image = new Image(product.getImageUrl(), "no image available");
                image.setWidth(50, Unit.PIXELS);
                image.setHeight(50, Unit.PIXELS);

                VerticalLayout productInfo = new VerticalLayout();
                productInfo.setSpacing(false);
                productInfo.setPadding(false);
                productInfo.getElement().appendChild(ElementFactory.createStrong(product.getName()));
                productInfo.add(new Div(new Text("Price : " + product.getPrice() + "$")));

                VerticalLayout productDetails = new VerticalLayout();
                productDetails.setSpacing(false);
                productDetails.setPadding(false);
                productDetails.add(new Div(new Text("Still available " + product.getAmount())));
                productInfo.add(new Details("More information", productDetails));

                productLayout.add(image, productInfo);
                return productLayout;
            }));
//TODO change everything
            productsFromOrder.add(new Div(productVirtualList));
            orderInfo.add(new Details("See products", productsFromOrder));
//            orderInfo.add(new Details("See products XD", new H1("DOBRZE BY BYLO")));

            orderLayout.add(orderId, orderInfo);
            return orderLayout;
        }));
        add(list);
    }
}