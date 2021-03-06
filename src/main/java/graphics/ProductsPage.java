package graphics;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import controller.Database;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import main.Main;
import model.Category;
import model.Filter;
import model.Product;
import model.Property;

import java.util.ArrayList;
import java.util.Comparator;

public class ProductsPage {
    public GridPane gridPane;
    public ChoiceBox<Category> categoryBox;
    public VBox filtersBox;
    public JFXCheckBox inStock;
    public JFXCheckBox hasOff;
    public JFXTextField nameField;
    public JFXTextField minPrice;
    public JFXTextField maxPrice;
    public JFXTextField brandField;
    public JFXTextField sellerField;
    public IntegerValidator numberValidator;
    public ChoiceBox<String> sortBox;
    public Spinner pageSpinner;
    public HBox pageHBox;
    public SpinnerValueFactory.IntegerSpinnerValueFactory pageFactory;
    Comparator<Product> comparator;
    ArrayList<Product> products = Database.getAllProducts();
    ArrayList<JFXTextField> fields = new ArrayList<>();
    ArrayList<JFXCheckBox> checkBoxes = new ArrayList<>();
    Filter filter = new Filter();

    @FXML
    public void initialize() {

        ArrayList<Category> categories = Database.getAllCategories();
        categoryBox.setItems(FXCollections.observableArrayList(categories));

        categoryBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            resetProducts(categories.get(newValue.intValue()).getProducts());
            makeFilters((categories.get(newValue.intValue())));
        });

        setComparator("Name");
        sortBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            setComparator(sortBox.getItems().get(newValue.intValue()));
            resetProducts(products);
        });

        pageSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            resetProducts(products);
        });

        resetProducts(products);
        makeFilters(null);
    }

    private void setComparator(String type) {
        switch (type) {
            case "Name":
                comparator = Comparator.comparing(Product::getName);
                break;
            case "Price (low to high)":
                comparator = Comparator.comparing(Product::getPrice);
                break;
            case "Price (high to low)":
                comparator = Comparator.comparing(Product::getPrice).reversed();
                break;
            case "Rating":
                comparator = Comparator.comparing(Product::getAverageScore).reversed();
                break;
        }
    }

    private void makeFilters(Category category) {
        filter = new Filter();
        fields = new ArrayList<>();
        fields.add(nameField);
        fields.add(minPrice);
        fields.add(maxPrice);
        fields.add(brandField);
        fields.add(sellerField);
        checkBoxes = new ArrayList<>();
        checkBoxes.add(inStock);
        checkBoxes.add(hasOff);
        minPrice.setValidators(numberValidator);
        maxPrice.setValidators(numberValidator);

        if (category != null) {
            for (Property property : category.getSpecialProperties()) {
                JFXTextField field = new JFXTextField();
                field.setPromptText(property.getName());
                if (property.isNumber())
                    field.setValidators(numberValidator);
                fields.add(field);
            }
        }

        for (JFXTextField field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (field.validate()) {
                    applyFilters();
                }
            });
        }
        for (JFXCheckBox box : checkBoxes) {
            box.selectedProperty().addListener((observable, oldValue, newValue) -> applyFilters());
        }

        filtersBox.getChildren().clear();
        filtersBox.getChildren().addAll(checkBoxes);
        filtersBox.getChildren().addAll(fields);
        applyFilters();
    }

    private void applyFilters() {
        filter = new Filter();
        for (JFXCheckBox box : checkBoxes) {
            if (box.isSelected())
                filter.addRestriction(new Property(box.getText(), ""));
        }
        for (JFXTextField field : fields) {
            if (!field.getText().trim().isEmpty() && field.validate()) {
                if (field.getValidators().isEmpty())
                    filter.addRestriction(new Property(field.getPromptText(),
                            field.getText()));
                else
                    filter.addRestriction(new Property(field.getPromptText(),
                            Long.parseLong(field.getText().trim())));
            }
        }
        resetProducts(products);
    }

    private void resetProducts(ArrayList<Product> products) {
        this.products = products;
        ArrayList<Product> productsToShow = filter.apply(products);
        int counter = 0;

        gridPane.getChildren().clear();
        int pageSize = 6;
        gridPane.getChildren().add(pageHBox);
        pageFactory.setMax(1 + (productsToShow.size() - 1) / pageSize);
        int pageNumber = Integer.parseInt(pageSpinner.getValue().toString());
        productsToShow.sort(comparator);
        for (Product product : productsToShow) {
            if (counter < pageNumber * pageSize && counter >= (pageNumber - 1) * pageSize) {
                ProductPane pane = new ProductPane(product);
                gridPane.getChildren().add(pane);
                GridPane.setConstraints(pane, counter % 2, (counter - (pageNumber - 1) * pageSize) / 2 + 1);
            }
            counter++;
        }
    }
}