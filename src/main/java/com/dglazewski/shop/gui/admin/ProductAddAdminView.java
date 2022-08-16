package com.dglazewski.shop.gui.admin;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.service.ProductService;
import com.dglazewski.shop.gui.AppLayoutDrawer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "admin/product/add", layout = AppLayoutDrawer.class)
@PageTitle("Add product | ADMIN")
public class ProductAddAdminView extends VerticalLayout {

    //SERVICE
    private final ProductService productService;

    //FIELDS
    private final TextField nameTextField;
    private final NumberField priceNumberField;
    private final IntegerField amountIntegerField;
    private final TextField imageUrlTextField;
    private final FormLayout addingFromLayout;

    //BUTTON
    private final Button addProductButton;
    private final Button cancelButton;
    private final Button clearButton;


    //BINDER
    private final Binder<Product> binder;

    public ProductAddAdminView(ProductService productService) {
        //SERVICE
        this.productService = productService;

        //FIELDS
        this.nameTextField = new TextField("name");
        configNameTextField();

        this.priceNumberField = new NumberField("price");
        configPriceNumberField();

        this.amountIntegerField = new IntegerField("amount");
        configAmountIntegerField();

        this.imageUrlTextField = new TextField("image url");
        configImageUrlTextField();

        this.addingFromLayout = new FormLayout();
        configAddingFromLayout();

        //BUTTON
        this.addProductButton = new Button("Add");
        configAddProductButton();

        this.clearButton = new Button("Clear");
        configClearButton();

        this.cancelButton = new Button("Cancel");
        configCancelButton();

        //BINDER
        this.binder = new Binder<>(Product.class);
        bindFields();

        configCss();

        add(addingFromLayout,
                new HorizontalLayout(addProductButton, clearButton, cancelButton));
    }

    private void bindFields() {
        this.binder.forField(nameTextField)
                .asRequired("Every product has need to have name")
                .withValidator(
                        name -> name.length() >= 2,
                        "Name must contain at least two characters")
                .bind(Product::getName, Product::setName);

        this.binder.forField(this.amountIntegerField).asRequired("Every product has need to have amount")
                .withValidator(new IntegerRangeValidator("Invalid amount", 0, 100000))
                .bind(Product::getAmount, Product::setAmount);

        this.binder.forField(this.priceNumberField).asRequired("Every product has need to have price")
                .withValidator(new DoubleRangeValidator("Invalid price", 0.01, 1000000.00))
                .bind(Product::getPrice, Product::setPrice);

        this.binder.forField(this.imageUrlTextField).asRequired("Every product has need to have image URL")
                .bind(Product::getImageUrl, Product::setImageUrl);
    }

    private void configCss() {
        setAlignItems(Alignment.AUTO);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void configAddProductButton() {
        this.addProductButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addProductButton.addClickListener(buttonClickEvent -> {
            try {
                DataBaseStatusResponse<Product> dataBaseStatusResponse = this.productService.addProduct(
                        Product.create(
                                this.nameTextField.getValue(),
                                this.priceNumberField.getValue(),
                                this.amountIntegerField.getValue(),
                                this.imageUrlTextField.getValue()));
                Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
            } catch (NullPointerException ex) {
                ex.printStackTrace();
                Notification.show("ONE OR MORE OF THE VALUES OF THE PRODUCT ARE EMPTY");
            }
        });
    }

    private void configClearButton() {
        this.clearButton.addClickListener(buttonClickEvent -> {
            this.nameTextField.setValue("");
            this.priceNumberField.setValue(0.00);
            this.amountIntegerField.setValue(0);
            this.imageUrlTextField.setValue("");
            Notification.show("LABELS CLEARED");
        });
    }

    private void configCancelButton() {
        this.cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.cancelButton.addClickListener(buttonClickEvent -> this.cancelButton.getUI().ifPresent(ui ->
                ui.navigate("admin/product/all")));
    }

    private void configNameTextField() {
        this.nameTextField.setClearButtonVisible(false);
    }

    private void configImageUrlTextField() {
        this.imageUrlTextField.setClearButtonVisible(false);

    }

    private void configAddingFromLayout() {
        this.addingFromLayout.add(
                this.nameTextField, this.priceNumberField,
                this.imageUrlTextField, this.amountIntegerField);
        this.addingFromLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("200px", 2));
        this.addingFromLayout.setColspan(this.nameTextField, 1);
    }

    private void configAmountIntegerField() {
        this.amountIntegerField.setHelperText("Max 100 000 items");
        this.amountIntegerField.setMin(0);
        this.amountIntegerField.setMax(100000);
        this.amountIntegerField.setValue(1);
        this.amountIntegerField.setHasControls(true);
    }

    private void configPriceNumberField() {
        this.priceNumberField.setValue(1.0);
        Div dollarSuffix = new Div();
        dollarSuffix.setText("$");
        this.priceNumberField.setSuffixComponent(dollarSuffix);
    }
}