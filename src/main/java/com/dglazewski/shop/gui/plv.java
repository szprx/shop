package com.dglazewski.shop.gui;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.service.ProductService;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;

@Route("list")
public class plv extends VerticalLayout {

    //SERVICE
    private final ProductService productService;

    private final TextField filterTextField;

    //BUTTON
    private final Button addButton;

    //GRID
    private final Grid<Product> productGrid;

    //VALIDATION MESSAGE
    private final ValidationMessage nameValidationMessage;
    private final ValidationMessage priceValidationMessage;
    private final ValidationMessage amountValidationMessage;
    private final ValidationMessage imageUrlValidationMessage;

    public plv(ProductService productService) {

        //BUTTON
        this.addButton = new Button("Add product");

        //VALIDATION MESSAGE
        this.nameValidationMessage = new ValidationMessage();
        this.priceValidationMessage = new ValidationMessage();
        this.amountValidationMessage = new ValidationMessage();
        this.imageUrlValidationMessage = new ValidationMessage();

        this.productService = productService;



        this.productGrid = new Grid<>(Product.class, false);
        Editor<Product> editor = productGrid.getEditor();

        Grid.Column<Product> nameColumn = productGrid
                .addColumn(Product::getName).setHeader("Name")
                .setSortable(true).setWidth("400px").setFlexGrow(1);

        Grid.Column<Product> amountColumn = productGrid
                .addColumn(Product::getAmount).setHeader("Amount")
                .setSortable(true).setWidth("100px").setFlexGrow(0);

        Grid.Column<Product> priceColumn = productGrid
                .addColumn(Product::getPrice).setHeader("Price")
                .setSortable(true).setWidth("100px").setFlexGrow(0);

        Grid.Column<Product> imageColumn = productGrid
                .addComponentColumn(product -> {
                    Image image = new Image(product.getImageUrl(), "alt text");
                    image.setWidth(80, Unit.PIXELS);
                    image.setHeight(80, Unit.PIXELS);
                    return image;
                }).setHeader("Preview").setWidth("400px").setFlexGrow(2);

        Grid.Column<Product> editColumn = productGrid.addComponentColumn(product -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                productGrid.getEditor().editItem(product);
            });
            return editButton;
        }).setWidth("220px").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);

        Binder<Product> binder = new Binder<>(Product.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField nameTextField = new TextField();
        nameTextField.setWidthFull();
        binder.forField(nameTextField).asRequired("Name must not be empty")
                .withStatusLabel(nameValidationMessage)
                .bind(Product::getName, Product::setName);
        nameColumn.setEditorComponent(nameTextField);

        IntegerField amountIntegerField = new IntegerField();
        amountIntegerField.setWidthFull();
        binder.forField(amountIntegerField).asRequired("Amount must not be empty")
                .withStatusLabel(amountValidationMessage)
                .bind(Product::getAmount, Product::setAmount);
        amountColumn.setEditorComponent(amountIntegerField);

        NumberField priceNumberField = new NumberField();
        priceNumberField.setWidthFull();
        binder.forField(priceNumberField).asRequired("Price must not be empty")
                .withStatusLabel(priceValidationMessage)
                .bind(Product::getPrice, Product::setPrice);
        priceColumn.setEditorComponent(priceNumberField);

        TextField imageUrlTextField = new TextField();
        imageUrlTextField.setWidthFull();
        binder.forField(imageUrlTextField).asRequired("Url must not be empty")
                .withStatusLabel(imageUrlValidationMessage)
                .bind(Product::getImageUrl, Product::setImageUrl);
        imageColumn.setEditorComponent(imageUrlTextField);


        Button saveButton = new Button("Save", e ->
        {
            //TODO:Add Validator
            DataBaseStatusResponse<Product> dataBaseStatusResponse = this.productService.updateProduct(
                    this.productGrid.getEditor().getItem().getId(),
                    Product.create(
                            nameTextField.getValue(),
                            priceNumberField.getValue(),
                            amountIntegerField.getValue(),
                            imageUrlTextField.getValue()));
            Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
            editor.save();
        }
        );
        Button cancelButton = new Button("Cancel",
                e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);

        editor.addCancelListener(e -> {
            nameValidationMessage.setText("");
            amountValidationMessage.setText("");
            priceValidationMessage.setText("");
            imageUrlValidationMessage.setText("");
        });

        productGrid.setItems(this.productService.getAllProducts().getEntity());

        getThemeList().clear();
        getThemeList().add("spacing-s");
        add(productGrid,
                nameValidationMessage,
                amountValidationMessage,
                priceValidationMessage,
                imageUrlValidationMessage);

        this.filterTextField = new TextField();

        configFilterTextField();
        configAddButton();

        configCss();

        add(getToolBar());
        add(productGrid);

    }

    private void configCss() {
        Style style = this.getElement().getStyle();
        style.set("margin", "10px");
    }


    private void configFilterTextField() {
        this.filterTextField.setPlaceholder("Search by name...");
    }

    private void configAddButton() {
        this.addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addButton.addClickListener(buttonClickEvent -> this.addButton.getUI().ifPresent(ui ->
                ui.navigate("product/add")));
    }

    private HorizontalLayout getToolBar() {
        return new HorizontalLayout(this.filterTextField, this.addButton);

    }
}
