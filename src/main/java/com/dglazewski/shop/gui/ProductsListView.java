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
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;

@Route("product/all")
public class ProductsListView extends VerticalLayout {
    //SERVICE
    private final ProductService productService;

    //BUTTON
    private final Button addButton;
    private final Button saveButton;
    private final Button cancelButton;
    private final Button editButton;

    //GRID
    private final Grid<Product> productGrid;

    //EDITOR
    private final Editor<Product> productEditor;

    //BINDER
    private final Binder<Product> binder;

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

    public ProductsListView(ProductService productService) {
        //SERVICE
        this.productService = productService;

        //BUTTON
        this.addButton = new Button("Add product");

        this.editButton = new Button("Edit");

        this.saveButton = new Button("Save");

        this.cancelButton = new Button("Cancel", VaadinIcon.CLOSE.create());





        //GRID
        this.productGrid = new Grid<>(Product.class, false);

        //EDITOR
        this.productEditor = this.productGrid.getEditor();

        //BINDER
        this.binder = new Binder<>(Product.class);

        //FIELDS
        this.filterTextField = new TextField();
        this.nameTextField = new TextField();
        this.amountIntegerField = new IntegerField();
        this.priceNumberField = new NumberField();
        this.imageUrlTextField = new TextField();

        //VALIDATION MESSAGE
        this.nameValidationMessage = new ValidationMessage();
        this.priceValidationMessage = new ValidationMessage();
        this.amountValidationMessage = new ValidationMessage();
        this.imageUrlValidationMessage = new ValidationMessage();

        configFilterTextField();
        configAddButton();
        configProductGrid();

        configCss();



        add(new HorizontalLayout(this.filterTextField, this.addButton));
        add(productGrid,
                nameValidationMessage,
                priceValidationMessage,
                amountValidationMessage,
                imageUrlValidationMessage);
    }

    private void configCss() {
        Style style = this.getElement().getStyle();
        style.set("margin", "10px");

        getThemeList().clear();
        getThemeList().add("spacing-s");
    }

    private void configProductGrid() {
        productGrid.setItems(this.productService.getAllProducts().getEntity());

        Grid.Column<Product> nameColumn = productGrid.addColumn(Product::getName)
                .setHeader("Name").setSortable(true).setWidth("400px").setFlexGrow(1);

        Grid.Column<Product> amountColumn = productGrid.addColumn(Product::getAmount)
                .setHeader("Amount").setSortable(true).setWidth("100px").setFlexGrow(0);

        Grid.Column<Product> priceColumn = productGrid.addColumn(Product::getPrice)
                .setHeader("Price").setSortable(true).setWidth("100px").setFlexGrow(0);

        Grid.Column<Product> imageColumn = productGrid.addComponentColumn(product -> {
            Image image = new Image(product.getImageUrl(), "alt text");
            image.setWidth(80, Unit.PIXELS);
            image.setHeight(80, Unit.PIXELS);
            return image;
        }).setHeader("Preview").setWidth("400px").setFlexGrow(2);

        Grid.Column<Product> editColumn = productGrid.addComponentColumn(product -> {
            this.editButton.addClickListener(e -> {
                if (productEditor.isOpen()) {
                    productEditor.cancel();
                }
                productGrid.getEditor().editItem(product);
            });
            return editButton;
        }).setWidth("220px").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);


        configBinder();

        configEditingFields();


        binder.forField(this.nameTextField)
                .asRequired("Name must not be empty")
                .withStatusLabel(this.nameValidationMessage)
                .bind(Product::getName, Product::setName);
        nameColumn.setEditorComponent(nameTextField);

        binder.forField(amountIntegerField).asRequired("Amount must not be empty")
                .withValidator(new IntegerRangeValidator("Please enter a valid amount", 0, 10000))
                .withStatusLabel(this.amountValidationMessage)
                .bind(Product::getAmount, Product::setAmount);
        amountColumn.setEditorComponent(amountIntegerField);

        binder.forField(priceNumberField).asRequired("Price must not be empty")
                .withStatusLabel(this.priceValidationMessage)
                .bind(Product::getPrice, Product::setPrice);
        priceColumn.setEditorComponent(priceNumberField);

        binder.forField(imageUrlTextField)
                .asRequired("Url must not be empty")
                .withStatusLabel(this.imageUrlValidationMessage)
                .bind(Product::getImageUrl, Product::setImageUrl);
        imageColumn.setEditorComponent(imageUrlTextField);

        saveButton.addClickListener(e -> {
                    //TODO:Add Validator
                    DataBaseStatusResponse<Product> dataBaseStatusResponse = this.productService.updateProduct(
                    this.productGrid.getEditor().getItem().getId(),
                    Product.create(
                            nameTextField.getValue(),
                            priceNumberField.getValue(),
                            amountIntegerField.getValue(),
                            imageUrlTextField.getValue()));
                    Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
                    productEditor.save();
                }
        );
        this.cancelButton.addClickListener(e -> productEditor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);

        productEditor.addCancelListener(e -> {
            nameValidationMessage.setText("");
            priceValidationMessage.setText("");
            amountValidationMessage.setText("");
            imageUrlValidationMessage.setText("");
        });
    }

    private void configEditingFields() {
        this.nameTextField.setWidthFull();
        this.imageUrlTextField.setWidthFull();
        this.priceNumberField.setWidthFull();
        this.amountIntegerField.setWidthFull();
    }

    private void configBinder() {
        this.productEditor.setBinder(binder);
        this.productEditor.setBuffered(true);
    }

    private void configFilterTextField() {
        this.filterTextField.setPlaceholder("Search by name...");
    }

    private void configAddButton() {
        this.addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addButton.addClickListener(buttonClickEvent -> this.addButton.getUI().ifPresent(ui ->
                ui.navigate("product/add")));
    }
}
