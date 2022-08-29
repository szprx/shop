package com.dglazewski.shop.gui.view.components.validator;

import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegisterNewProductValidator {
    public void validateName(TextField nameTextField) {
        String name = nameTextField.getValue();

        if (name.trim().length() < 2) {
            nameTextField.setInvalid(true);
            nameTextField.setErrorMessage("Name must contain at least two characters");
        }
    }

    public void validateAmount(IntegerField amountIntegerField) {
        int amount = amountIntegerField.getValue();

        if (amount < 1) {
            amountIntegerField.setInvalid(true);
            amountIntegerField.setErrorMessage("Amount min 1");
        }
    }

    public void validatePrice(NumberField priceNumberField) {
        double price = priceNumberField.getValue();

        if (price < 0.01) {
            priceNumberField.setInvalid(true);
            priceNumberField.setErrorMessage("Price min 0.01");
        }
    }

    public void validateImageUrl(TextField imageUrlTextField) {
        String name = imageUrlTextField.getValue();

        if (name.trim().length() < 2) {
            imageUrlTextField.setInvalid(true);
            imageUrlTextField.setErrorMessage("Invalid url");
        }
    }
}