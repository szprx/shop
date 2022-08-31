package com.dglazewski.shop.gui.admin.components;

import com.dglazewski.shop.gui.admin.components.validator.ProductValidator;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

@Getter
public class ProductFormLayout extends FormLayout {

    //FIELD
    private final TextField nameTextField;
    private final NumberField priceNumberField;
    private final IntegerField amountIntegerField;
    private final TextField imageUrlTextField;

    //VALIDATOR
    private final ProductValidator productValidator;

    public ProductFormLayout() {
        //FIELD
        this.nameTextField = new TextField("Name");
        this.priceNumberField = new NumberField("Price");
        this.amountIntegerField = new IntegerField("Amount");
        this.imageUrlTextField = new TextField("Image url");

        //FIELD CONFIG
        configNameTextField();
        configPriceNumberField();
        configAmountIntegerField();
        configImageUrlTextField();
        configFromLayout();

        //VALIDATOR
        this.productValidator = new ProductValidator();

        add(
                nameTextField, priceNumberField,
                imageUrlTextField, amountIntegerField);
    }

    private void configNameTextField() {
        this.nameTextField.setClearButtonVisible(false);
    }

    private void configImageUrlTextField() {
        this.imageUrlTextField.setClearButtonVisible(false);

    }

    private void configFromLayout() {
        setResponsiveSteps(new FormLayout.ResponsiveStep("200px", 2));
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


    public boolean isValid() {
        validate();
        return !nameTextField.isInvalid() && !amountIntegerField.isInvalid() && !priceNumberField.isInvalid() && !imageUrlTextField.isInvalid();

    }

    private void validate() {
        productValidator.validateName(nameTextField);
        productValidator.validateAmount(amountIntegerField);
        productValidator.validatePrice(priceNumberField);
        productValidator.validateImageUrl(imageUrlTextField);
    }
}