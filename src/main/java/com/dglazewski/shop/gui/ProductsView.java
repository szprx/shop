package com.dglazewski.shop.gui;

import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.service.ProductService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "product/all", layout = AppLayoutDrawer.class)
@PageTitle("Products")
@AnonymousAllowed
public class ProductsView extends VerticalLayout {

    //SERVICE
    private final ProductService productService;

    public ProductsView(ProductService productService) {
        this.productService = productService;

        VirtualList<Product> list = new VirtualList<>();
        list.setHeight("820px");
        list.setItems(this.productService.getAllProducts().getEntity());
        list.setRenderer(productCardRender);
        add(list);
    }

    private ComponentRenderer<Component, Product> productCardRender = new ComponentRenderer<>(product -> {
        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);

        Button addToShoppingCardButton = new Button("Add to card");
        addToShoppingCardButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        addToShoppingCardButton.addClickListener(buttonClickEvent -> {
            //TODO: add adding to shopping card a verification about
            // logged user and notification about adding product to card
        });
        addToShoppingCardButton.setWidth("150px");

        HorizontalLayout activities = new HorizontalLayout(addToShoppingCardButton);
        activities.setAlignItems(Alignment.CENTER);

        Image image = new Image(product.getImageUrl(), "no image available");
        image.setWidth(130, Unit.PIXELS);
        image.setHeight(130, Unit.PIXELS);

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);
        infoLayout.getElement().appendChild(ElementFactory.createStrong(product.getName()));
        infoLayout.add(new Div(new Text(String.valueOf("Price per kilogram " + product.getPrice() + "$"))));

        VerticalLayout contactLayout = new VerticalLayout();
        contactLayout.setSpacing(false);
        contactLayout.setPadding(false);
        contactLayout.add(new Div(new Text("Still available " + product.getAmount() + " kilograms")));
        infoLayout.add(new Details("More information", contactLayout));

        cardLayout.add(image, infoLayout, activities);
        return cardLayout;
    });
}
