package com.dglazewski.shop.gui.view.admin;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.service.ProductService;
import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.dglazewski.shop.gui.view.components.form.NewProductFormLayout;
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
    private final NewProductFormLayout newProductFormLayout;

    //BUTTON
    private final Button addProductButton;
    private final Button cancelButton;
    private final Button clearButton;

    public ProductAddAdminView(ProductService productService) {
        //SERVICE
        this.productService = productService;

        //FORM LAYOUT
        this.newProductFormLayout = new NewProductFormLayout();

        //BUTTON
        this.addProductButton = new Button("Add");
        configAddProductButton();
        this.clearButton = new Button("Clear");
        configClearButton();
        this.cancelButton = new Button("Cancel");
        configCancelButton();

        configCss();

        add(newProductFormLayout,
                new HorizontalLayout(addProductButton, clearButton, cancelButton));
    }

    private void configCss() {
        setAlignItems(Alignment.AUTO);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void configAddProductButton() {
        this.addProductButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addProductButton.addClickListener(buttonClickEvent -> {
            try {
                if (this.newProductFormLayout.isValid()) {
                    DataBaseStatusResponse<Product> dataBaseStatusResponse = this.productService.addProduct(Product.create(
                            this.newProductFormLayout.getNameTextField().getValue(),
                            this.newProductFormLayout.getPriceNumberField().getValue(),
                            this.newProductFormLayout.getAmountIntegerField().getValue(),
                            this.newProductFormLayout.getImageUrlTextField().getValue()));
                    Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                Notification.show("ONE OR MORE OF THE VALUES OF THE PRODUCT ARE EMPTY");
            }
        });
    }

    private void configClearButton() {
        this.clearButton.addClickListener(buttonClickEvent -> {
            this.newProductFormLayout.getNameTextField().setValue("");
            this.newProductFormLayout.getPriceNumberField().setValue(0.00);
            this.newProductFormLayout.getAmountIntegerField().setValue(0);
            this.newProductFormLayout.getImageUrlTextField().setValue("");
            Notification.show("LABELS CLEARED");
        });
    }

    private void configCancelButton() {
        this.cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.cancelButton.addClickListener(buttonClickEvent -> this.cancelButton.getUI().ifPresent(ui ->
                ui.navigate("admin/product/all")));
    }
}