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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;

@Route("admin/product/all")
public class ProductsListView extends VerticalLayout {

    //SERVICE
    private final ProductService productService;

    //FIELDS
    private final TextField filterTextField;
    private final TextField nameTextField;
    private final TextField imageUrlTextField;
    private final NumberField priceNumberField;
    private final IntegerField amountIntegerField;

    //VALIDATION MESSAGE
    private final ValidationMessage nameValidationMessage;
    private final ValidationMessage priceValidationMessage;
    private final ValidationMessage amountValidationMessage;
    private final ValidationMessage imageUrlValidationMessage;

    //BUTTON
    private final Button addButton;

    //GRID
    private final Grid<Product> productGrid;

    //BINDER
    private final Binder<Product> binder;

    //EDITOR
    private final Editor<Product> productGridEditor;

    //COLUMNS
    private Grid.Column<Product> nameColumn;
    private Grid.Column<Product> amountColumn;
    private Grid.Column<Product> priceColumn;
    private Grid.Column<Product> imageColumn;
    private Grid.Column<Product> editColumn;


    public ProductsListView(ProductService productService) {

        //SERVICE
        this.productService = productService;

        //FIELDS
        this.filterTextField = new TextField();
        this.filterTextField.setPlaceholder("Search by name...");

        this.nameTextField = new TextField();
        this.nameTextField.setWidthFull();

        this.amountIntegerField = new IntegerField();
        this.amountIntegerField.setWidthFull();

        this.priceNumberField = new NumberField();
        this.priceNumberField.setWidthFull();

        this.imageUrlTextField = new TextField();
        this.imageUrlTextField.setWidthFull();

        //VALIDATION MESSAGE
        this.nameValidationMessage = new ValidationMessage();
        this.priceValidationMessage = new ValidationMessage();
        this.amountValidationMessage = new ValidationMessage();
        this.imageUrlValidationMessage = new ValidationMessage();

        //BUTTON
        this.addButton = new Button("Add product");
        configAddButton();

        //GRID
        this.productGrid = new Grid<>(Product.class, false);
        this.productGrid.setItems(this.productService.getAllProducts().getEntity());

        //BINDER
        this.binder = new Binder<>(Product.class);

        //EDITOR
        this.productGridEditor = this.productGrid.getEditor();
        configProductGrindEditor();

        //COLUMNS
        configColumns();
        bindFieldsToColumns();

        this.editColumn.setEditorComponent(getEditingModeActions());

        configCss();

        add(new HorizontalLayout(this.filterTextField, this.addButton));
        add(this.productGrid,
                this.nameValidationMessage, this.amountValidationMessage, this.priceValidationMessage, this.imageUrlValidationMessage);
    }

    private void configColumns() {
        this.nameColumn = this.productGrid.addColumn(Product::getName).setHeader("Name")
                .setSortable(true).setWidth("400px").setFlexGrow(1);
        this.amountColumn = this.productGrid.addColumn(Product::getAmount).setHeader("Amount")
                .setSortable(true).setWidth("100px").setFlexGrow(0);
        this.priceColumn = this.productGrid.addColumn(Product::getPrice).setHeader("Price")
                .setSortable(true).setWidth("100px").setFlexGrow(0);
        this.imageColumn = this.productGrid.addComponentColumn(product -> {
            Image image = new Image(product.getImageUrl(), "alt text");
            image.setWidth(80, Unit.PIXELS);
            image.setHeight(80, Unit.PIXELS);
            return image;
        }).setHeader("Preview").setWidth("400px").setFlexGrow(2);
        this.editColumn = this.productGrid.addComponentColumn(product -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (this.productGridEditor.isOpen())
                    this.productGridEditor.cancel();
                this.productGrid.getEditor().editItem(product);
            });
            return editButton;
        }).setWidth("220px").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
    }

    private void bindFieldsToColumns() {
        this.binder.forField(this.nameTextField).asRequired("Name must not be empty")
                .withStatusLabel(this.nameValidationMessage)
                .bind(Product::getName, Product::setName);
        this.nameColumn.setEditorComponent(this.nameTextField);

        this.binder.forField(this.amountIntegerField).asRequired("Amount must not be empty")
                .withValidator(new IntegerRangeValidator("Please enter a valid amount", 0, 100000))
                .withStatusLabel(this.amountValidationMessage)
                .bind(Product::getAmount, Product::setAmount);
        this.amountColumn.setEditorComponent(this.amountIntegerField);

        this.binder.forField(this.priceNumberField).asRequired("Price must not be empty")
                .withValidator(new DoubleRangeValidator("Please enter a valid price",0.01,1000000.00))
                .withStatusLabel(this.priceValidationMessage)
                .bind(Product::getPrice, Product::setPrice);
        this.priceColumn.setEditorComponent(this.priceNumberField);

        this.binder.forField(this.imageUrlTextField).asRequired("Url must not be empty")
                .withStatusLabel(this.imageUrlValidationMessage)
                .bind(Product::getImageUrl, Product::setImageUrl);
        this.imageColumn.setEditorComponent(this.imageUrlTextField);
    }

    private void configProductGrindEditor() {
        this.productGridEditor.addCancelListener(e -> {
            this.nameValidationMessage.setText("");
            this.amountValidationMessage.setText("");
            this.priceValidationMessage.setText("");
            this.imageUrlValidationMessage.setText("");
        });
        this.productGridEditor.setBinder(this.binder);
        this.productGridEditor.setBuffered(true);
    }

    private HorizontalLayout getEditingModeActions() {

        //SAVE BUTTON
        Button saveButton = new Button("Save", e -> {
            //TODO:Add Validator
            DataBaseStatusResponse<Product> dataBaseStatusResponse = this.productService.updateProduct(
                    this.productGrid.getEditor().getItem().getId(),
                    Product.create(
                            this.nameTextField.getValue(),
                            this.priceNumberField.getValue(),
                            this.amountIntegerField.getValue(),
                            this.imageUrlTextField.getValue()));
            Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
            this.productGridEditor.save();
        });

        //CANCEL BUTTON
        Button cancelButton = new Button("Cancel",
                e -> this.productGridEditor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);

        //ACTIONS
        HorizontalLayout actions = new HorizontalLayout(saveButton, cancelButton);
        actions.setPadding(false);

        return actions;
    }

    private void configCss() {
        getThemeList().clear();
        getThemeList().add("spacing-s");

        Style style = this.getElement().getStyle();
        style.set("margin", "10px");
    }

    private void configAddButton() {
        this.addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addButton.addClickListener(buttonClickEvent -> this.addButton.getUI().ifPresent(ui ->
                ui.navigate("admin/product/add")));
    }
}
