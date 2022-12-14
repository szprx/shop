package com.dglazewski.shop.gui.admin.view;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Product;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.service.ProductService;
import com.dglazewski.shop.gui.admin.components.validator.ProductValidator;
import com.dglazewski.shop.gui.everyone.components.AppLayoutDrawer;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "admin/product/all", layout = AppLayoutDrawer.class)
@PageTitle("All products | ADMIN")
public class ProductsMenageAdminView extends VerticalLayout {

    //SERVICE
    private final ProductService productService;
    private final List<Product> products;

    //FIELDS
    private final TextField searchField;
    private final TextField nameTextField;
    private final TextField imageUrlTextField;
    private final NumberField priceNumberField;
    private final IntegerField amountIntegerField;

    //VALIDATOR
    private final ProductValidator productValidator;

    //GRID
    private final Grid<Product> productGrid;
    private final GridListDataView<Product> dataView;

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


    public ProductsMenageAdminView(ProductService productService) {
        //SERVICE
        this.productService = productService;
        this.products = this.productService.getAllProducts().getEntity();

        //FIELDS
        this.searchField = new TextField();
        this.searchField.setPlaceholder("Search by name...");

        this.nameTextField = new TextField();
        this.nameTextField.setWidthFull();

        this.amountIntegerField = new IntegerField();
        this.amountIntegerField.setWidthFull();

        this.priceNumberField = new NumberField();
        this.priceNumberField.setWidthFull();

        this.imageUrlTextField = new TextField();
        this.imageUrlTextField.setWidthFull();

        //VALIDATOR
        this.productValidator = new ProductValidator();

        //GRID
        this.productGrid = new Grid<>(Product.class, false);
        this.dataView = this.productGrid.setItems(products);

        //BINDER
        this.binder = new Binder<>(Product.class);

        //EDITOR
        this.productGridEditor = this.productGrid.getEditor();
        configProductGrindEditor();

        //COLUMNS
        configColumns();
        bindFieldsToColumns();

        this.editColumn.setEditorComponent(getEditingModeActions());

        configSearching();
        configCss();
        add(new HorizontalLayout(this.searchField));
        add(this.productGrid);
    }

    private void configSearching() {
        this.searchField.setWidth("600px");
        this.searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        this.searchField.setValueChangeMode(ValueChangeMode.EAGER);
        this.searchField.addValueChangeListener(e -> this.dataView.refreshAll());

        dataView.addFilter(product -> {
            String searchTerm = this.searchField.getValue().trim();
            if (searchTerm.isEmpty()) {
                return true;
            }
            return matchesTerm(product.getName(),
                    searchTerm);
        });
    }

    private void configColumns() {
        this.nameColumn = this.productGrid.addColumn(Product::getName).setHeader("Name")
                .setSortable(true).setWidth("400px").setFlexGrow(1);
        this.amountColumn = this.productGrid.addColumn(Product::getAmount).setHeader("Amount")
                .setSortable(true).setWidth("110px").setFlexGrow(0);
        this.priceColumn = this.productGrid.addColumn(Product::getPrice).setHeader("Price")
                .setSortable(true).setWidth("110px").setFlexGrow(0);
        this.imageColumn = this.productGrid.addComponentColumn(product -> {
            Image image = new Image(product.getImageUrl(), "no image available");
            image.setWidth(150, Unit.PIXELS);
            image.setHeight(150, Unit.PIXELS);
            return image;
        }).setHeader("Preview").setWidth("400px").setFlexGrow(2);
        this.editColumn = this.productGrid.addComponentColumn(product -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (this.productGridEditor.isOpen()) {
                    this.productGridEditor.cancel();
                }
                this.productGridEditor.editItem(product);
            });
            return editButton;
        }).setWidth("220px").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);
    }

    private void bindFieldsToColumns() {
        this.binder.forField(this.nameTextField)
                .bind(Product::getName, Product::setName);
        this.nameColumn.setEditorComponent(this.nameTextField);

        this.binder.forField(this.amountIntegerField)
                .bind(Product::getAmount, Product::setAmount);
        this.amountColumn.setEditorComponent(this.amountIntegerField);

        this.binder.forField(this.priceNumberField)
                .bind(Product::getPrice, Product::setPrice);
        this.priceColumn.setEditorComponent(this.priceNumberField);

        this.binder.forField(this.imageUrlTextField)
                .bind(Product::getImageUrl, Product::setImageUrl);
        this.imageColumn.setEditorComponent(this.imageUrlTextField);
    }

    private void configProductGrindEditor() {
        this.productGridEditor.setBinder(this.binder);
        this.productGridEditor.setBuffered(true);
    }

    private HorizontalLayout getEditingModeActions() {
        //SAVE BUTTON
        Button saveButton = new Button("Save", e -> {
            if (this.areProductFieldsValid()) {
                DataBaseStatusResponse<Product> dataBaseStatusResponse = this.productService.updateProduct(
                        this.productGridEditor.getItem().getId(),
                        Product.create(
                                this.nameTextField.getValue(),
                                this.priceNumberField.getValue(),
                                this.amountIntegerField.getValue(),
                                this.imageUrlTextField.getValue()));
                Notification.show(dataBaseStatusResponse.getStatus().toString().replace("_", " "));
                if (dataBaseStatusResponse.getStatus().equals(Status.RECORD_UPDATED_SUCCESSFULLY)) {
                    this.productGridEditor.save();
                }
                this.productGrid.getDataProvider().refreshAll();
            }
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

        productGrid.setWidthFull();
        productGrid.setHeight("830px");
        productGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }

    private boolean areProductFieldsValid() {
        validateProductFields();
        return !nameTextField.isInvalid() && !amountIntegerField.isInvalid() && !priceNumberField.isInvalid() && !imageUrlTextField.isInvalid();

    }

    private void validateProductFields() {
        productValidator.validateName(nameTextField);
        productValidator.validateAmount(amountIntegerField);
        productValidator.validatePrice(priceNumberField);
        productValidator.validateImageUrl(imageUrlTextField);
    }
}