package com.dglazewski.shop.gui.view.components.form;

import com.dglazewski.shop.gui.view.components.validator.RegisterNewProductValidator;
import com.dglazewski.shop.gui.view.components.validator.Validatable;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;

@Getter
public class NewProductFormLayout extends FormLayout implements Validatable {

    //FIELD
    private final TextField nameTextField;
    private final NumberField priceNumberField;
    private final IntegerField amountIntegerField;
    private final TextField imageUrlTextField;

    //VALIDATOR
    private final RegisterNewProductValidator registerNewProductValidator;

    public NewProductFormLayout() {
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
        this.registerNewProductValidator = new RegisterNewProductValidator();

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
        setColspan(this.nameTextField, 1);
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

    @Override
    public boolean isValid() {
        validate();
        return !nameTextField.isInvalid() && !amountIntegerField.isInvalid() && !priceNumberField.isInvalid() && !imageUrlTextField.isInvalid();

    }

    private void validate() {
        registerNewProductValidator.validateName(nameTextField);
        registerNewProductValidator.validateAmount(amountIntegerField);
        registerNewProductValidator.validatePrice(priceNumberField);
        registerNewProductValidator.validateImageUrl(imageUrlTextField);
    }
}