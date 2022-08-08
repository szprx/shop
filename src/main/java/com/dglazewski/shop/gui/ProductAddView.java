package com.dglazewski.shop.gui;

import com.dglazewski.shop.api.database_response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.service.ProductService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("product/add")
@CssImport("./styles/styles.css")
public class ProductAddView extends VerticalLayout {
    private TextField nameTextField;
    private NumberField priceNumberField;
    private IntegerField amountIntegerField;
    private FormLayout addingFromLayout;

    private Button addProductButton;
    private Button cancelButton;
    private HorizontalLayout buttonLayout;

    private ProductService productService;

    public ProductAddView(ProductService productService) {
        this.productService = productService;

        this.nameTextField = new TextField("name");
        this.priceNumberField = new NumberField("price");
        this.amountIntegerField = new IntegerField("amount");
        this.addingFromLayout = new FormLayout();

        this.addProductButton = new Button("add");
        this.cancelButton = new Button("Cancel");
        this.buttonLayout = new HorizontalLayout(addProductButton, cancelButton);

        configNameTextField();
        configPriceNumberField();
        configAmountIntegerField();
        configAddingFromLayout();

        configAddProductButton();

        add(addingFromLayout, buttonLayout);
    }

    private void configAddProductButton() {
        this.addProductButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addProductButton.addClickListener(buttonClickEvent -> {
            DataBaseStatusResponse<Product> dataBaseStatusResponse = this.productService.addProduct(
                    Product.create(
                            this.nameTextField.getValue(),
                            this.priceNumberField.getValue(),
                            this.amountIntegerField.getValue()));

            if (dataBaseStatusResponse.getStatus() == Status.RECORD_CREATED_SUCCESSFULLY) {
                Notification.show("Product added");
            } else {
                Notification.show(dataBaseStatusResponse.getStatus().toString());
            }
        });
    }

    private void configNameTextField() {
        this.nameTextField.setClearButtonVisible(false);
    }

    private void configAddingFromLayout() {
        this.addingFromLayout.add(this.nameTextField,
                this.priceNumberField, this.amountIntegerField);
        this.addingFromLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("320px", 2));
        this.addingFromLayout.setColspan(this.nameTextField, 2);
    }

    private void configAmountIntegerField() {
        this.amountIntegerField.setHelperText("Max 10 000 items");
        this.amountIntegerField.setMin(0);
        this.amountIntegerField.setMax(10000);
        this.amountIntegerField.setValue(1);
        this.amountIntegerField.setHasControls(true);
    }

    private void configPriceNumberField() {
        Div dollarSuffix = new Div();
        dollarSuffix.setText("$");
        this.priceNumberField.setSuffixComponent(dollarSuffix);
    }


}
