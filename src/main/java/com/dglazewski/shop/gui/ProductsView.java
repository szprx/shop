package com.dglazewski.shop.gui;

import com.dglazewski.shop.api.service.ProductService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "product/all", layout = AppLayoutDrawer.class)
@PageTitle("Products")
public class ProductsView extends VerticalLayout {

    //SERVICE
    private final ProductService productService;

    public ProductsView(ProductService productService) {
        this.productService = productService;
    }
}
