package com.dglazewski.shop.gui.admin.components.validator;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductValidator {
    private final String FILL_UP_MESSAGE = "Fill up";

    public void validateName(TextField nameTextField) {
        if (nameTextField.getValue() == null) {
            nameTextField.setInvalid(true);
            nameTextField.setErrorMessage(FILL_UP_MESSAGE);
            return;
        }
        String name = nameTextField.getValue();

        if (name.trim().length() < 2) {
            nameTextField.setInvalid(true);
            nameTextField.setErrorMessage("Name must contain at least two characters");
        }
    }

    public void validateAmount(IntegerField amountIntegerField) {
        if (amountIntegerField.getValue() == null) {
            amountIntegerField.setInvalid(true);
            amountIntegerField.setErrorMessage(FILL_UP_MESSAGE);
            return;
        }
        int amount = amountIntegerField.getValue();

        if (amount < 1) {
            amountIntegerField.setInvalid(true);
            amountIntegerField.setErrorMessage("Amount min 1");
        }
    }

    public void validatePrice(NumberField priceNumberField) {
        if (priceNumberField.getValue() == null) {
            priceNumberField.setInvalid(true);
            priceNumberField.setErrorMessage(FILL_UP_MESSAGE);
            return;
        }
        double price = priceNumberField.getValue();

        if (price < 0.01) {
            priceNumberField.setInvalid(true);
            priceNumberField.setErrorMessage("Price min 0.01");
        }
    }

    public void validateImageUrl(TextField imageUrlTextField) {
        if (imageUrlTextField.getValue() == null) {
            imageUrlTextField.setInvalid(true);
            imageUrlTextField.setErrorMessage(FILL_UP_MESSAGE);
            return;
        }

        String name = imageUrlTextField.getValue();

        if (name.trim().length() < 2) {
            imageUrlTextField.setInvalid(true);
            imageUrlTextField.setErrorMessage("Invalid url");
        }
    }
}