package com.dglazewski.shop.gui;

import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.service.ProductService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;

@Route("product/all")
public class ProductsListView extends VerticalLayout {

    private final ProductService productService;

    private TextField filterTextField;
    private Button addButton;

    private final Grid<Product> productGrid;

    public ProductsListView(ProductService productService) {
        this.productService = productService;

        this.filterTextField = new TextField();
        this.addButton = new Button("Add new product");
        this.productGrid = new Grid<>(Product.class, false);

        configFilterTextField();
        configAddButton();
        configProductGrid();

        configCss();

        add(getToolBar());
        add(productGrid);

    }

    private void configCss() {
        Style style = this.getElement().getStyle();
        style.set("margin", "10px");
    }

    private void configProductGrid() {
        productGrid.setItems(this.productService.getAllProducts().getEntity());
        productGrid.addColumn(Product::getName).setHeader("name");
        productGrid.addColumn(Product::getAmount).setHeader("amount");
        productGrid.addColumn(Product::getPrice).setHeader("price");
        productGrid.addComponentColumn(product -> {
            Image image = new Image(product.getImageUrl(), "alt text");
            image.setWidth(80, Unit.PIXELS);
            image.setHeight(80, Unit.PIXELS);
            return image;
        }).setHeader("preview");
    }

    private void configFilterTextField() {
        this.filterTextField.setPlaceholder("Filter by name...");
    }

    private void configAddButton() {
        this.addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addButton.addClickListener(buttonClickEvent -> this.addButton.getUI().ifPresent(ui ->
                ui.navigate("product/add")));
    }

    private HorizontalLayout getToolBar() {
        return new HorizontalLayout(this.filterTextField,this.addButton);

    }
}
