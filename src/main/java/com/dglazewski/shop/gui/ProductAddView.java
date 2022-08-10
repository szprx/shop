package com.dglazewski.shop.gui;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Product;
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
    private TextField imageUrlTextField;
    private FormLayout addingFromLayout;

    private Button addProductButton;
    private Button cancelButton;
    private Button clearButton;
    private HorizontalLayout buttonLayout;

    private final ProductService productService;

    public ProductAddView(ProductService productService) {
        this.productService = productService;

        this.nameTextField = new TextField("name");
        this.priceNumberField = new NumberField("price");
        this.amountIntegerField = new IntegerField("amount");
        this.imageUrlTextField = new TextField("image url");
        this.addingFromLayout = new FormLayout();

        this.addProductButton = new Button("Add");
        this.clearButton = new Button("Clear");
        this.cancelButton = new Button("Cancel");
        this.buttonLayout = new HorizontalLayout(addProductButton, clearButton, cancelButton);

        configNameTextField();
        configPriceNumberField();
        configAmountIntegerField();
        configImageUrlTextField();
        configAddingFromLayout();

        configAddProductButton();
        configClearButton();
        configCancelButton();

        setAlignItems(Alignment.AUTO);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(addingFromLayout, buttonLayout);
    }

    private void configAddProductButton() {
        this.addProductButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addProductButton.addClickListener(buttonClickEvent -> {
            DataBaseStatusResponse<Product> dataBaseStatusResponse = this.productService.addProduct(
                    Product.create(
                            this.nameTextField.getValue(),
                            this.priceNumberField.getValue(),
                            this.amountIntegerField.getValue(),
                            this.imageUrlTextField.getValue()));
            Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
        });
    }

    private void configClearButton() {
        this.clearButton.addClickListener(buttonClickEvent -> {
            this.nameTextField.setValue("");
            this.priceNumberField.setValue(0.0);
            this.amountIntegerField.setValue(1);
            this.imageUrlTextField.setValue("");
            Notification.show("LABELS CLEARED");
        });
    }

    private void configCancelButton() {
        this.cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.cancelButton.addClickListener(buttonClickEvent -> this.cancelButton.getUI().ifPresent(ui ->
                ui.navigate("product/all")));
    }

    private void configNameTextField() {
        this.nameTextField.setClearButtonVisible(false);
        this.nameTextField.setRequired(true);
        this.nameTextField.setRequiredIndicatorVisible(true);
    }

    private void configImageUrlTextField() {
        this.imageUrlTextField.setClearButtonVisible(false);
        this.imageUrlTextField.setRequiredIndicatorVisible(true);

    }

    private void configAddingFromLayout() {
        this.addingFromLayout.add(
                this.nameTextField, this.priceNumberField,
                this.imageUrlTextField, this.amountIntegerField);
        this.addingFromLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("200px", 2));
        this.addingFromLayout.setColspan(this.nameTextField, 1);
    }

    private void configAmountIntegerField() {
        this.amountIntegerField.setHelperText("Max 10 000 items");
        this.amountIntegerField.setMin(0);
        this.amountIntegerField.setMax(10000);
        this.amountIntegerField.setValue(1);
        this.amountIntegerField.setHasControls(true);
        this.amountIntegerField.setRequiredIndicatorVisible(true);
    }

    private void configPriceNumberField() {
        this.priceNumberField.setValue(1.0);
        Div dollarSuffix = new Div();
        dollarSuffix.setText("$");
        this.priceNumberField.setSuffixComponent(dollarSuffix);
        this.priceNumberField.setRequiredIndicatorVisible(true);
    }


}
