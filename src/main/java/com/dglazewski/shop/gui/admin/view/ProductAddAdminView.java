package com.dglazewski.shop.gui.admin.view;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.service.ProductService;
import com.dglazewski.shop.gui.admin.components.ProductFormLayout;
import com.dglazewski.shop.gui.everyone.components.AppLayoutDrawer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@Route(value = "admin/product/add", layout = AppLayoutDrawer.class)
@PageTitle("Add product | ADMIN")
@RolesAllowed("ADMIN")
public class ProductAddAdminView extends VerticalLayout {

    //SERVICE
    private final ProductService productService;

    //FORM LAYOUT
    private final ProductFormLayout productFormLayout;

    //BUTTON
    private final Button addProductButton;
    private final Button cancelButton;
    private final Button clearButton;

    public ProductAddAdminView(ProductService productService) {
        //SERVICE
        this.productService = productService;

        //FORM LAYOUT
        this.productFormLayout = new ProductFormLayout();

        //BUTTON
        this.addProductButton = new Button("Add");
        configAddProductButton();
        this.clearButton = new Button("Clear");
        configClearButton();
        this.cancelButton = new Button("Cancel");
        configCancelButton();

        configCss();

        add(productFormLayout,
                new HorizontalLayout(addProductButton, clearButton, cancelButton));
    }

    private void configCss() {
        setAlignItems(Alignment.AUTO);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void configAddProductButton() {
        this.addProductButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addProductButton.addClickListener(buttonClickEvent -> {
            if (this.productFormLayout.isValid()) {
                DataBaseStatusResponse<Product> dataBaseStatusResponse = this.productService.saveProduct(Product.create(
                        this.productFormLayout.getNameTextField().getValue(),
                        this.productFormLayout.getPriceNumberField().getValue(),
                        this.productFormLayout.getAmountIntegerField().getValue(),
                        this.productFormLayout.getImageUrlTextField().getValue()));
                Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
            }
        });
    }

    private void configClearButton() {
        this.clearButton.addClickListener(buttonClickEvent -> {
            this.productFormLayout.getNameTextField().setValue("");
            this.productFormLayout.getPriceNumberField().setValue(0.00);
            this.productFormLayout.getAmountIntegerField().setValue(0);
            this.productFormLayout.getImageUrlTextField().setValue("");
            Notification.show("LABELS CLEARED");
        });
    }

    private void configCancelButton() {
        this.cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.cancelButton.addClickListener(buttonClickEvent -> this.cancelButton.getUI().ifPresent(ui ->
                ui.navigate("admin/product/all")));
    }
}