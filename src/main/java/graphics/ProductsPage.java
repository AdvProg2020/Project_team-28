package graphics;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.IntegerValidator;
import controller.Database;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
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
    public JFXTextField nameField;
    public JFXTextField minPrice;
    public JFXTextField maxPrice;
    public JFXTextField brandField;
    public JFXTextField sellerField;
    public IntegerValidator numberValidator;
    public ChoiceBox<String> sortBox;
    Comparator<Product> comparator;
    ArrayList<Product> products = Database.getAllProducts();
    ArrayList<JFXTextField> fields = new ArrayList<>();
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
                    System.out.println("hey");
                    applyFilters();
                }
            });
        }
        filtersBox.getChildren().clear();
        filtersBox.getChildren().addAll(fields);
        applyFilters();
    }

    private void applyFilters() {
        filter = new Filter();
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
        productsToShow.sort(comparator);
        for (Product product : productsToShow) {
            ProductPane pane = new ProductPane(product);

            gridPane.getChildren().add(pane);
            GridPane.setConstraints(pane, counter % 2, counter / 2);
            counter++;

            pane.setOnMouseClicked(event -> {
                // TODO : connect to product page
                FXMLLoader fxmlLoader;
                fxmlLoader = Main.setMainStage(pane.product.getName(), "src/main/resources/fxml/ProductPage.fxml");
                assert fxmlLoader != null;
                ((ProductPage) fxmlLoader.getController()).setProduct(pane.getProduct());
            });
        }
    }


    public static class ProductPane extends VBox {

        private final Product product;

        public Product getProduct() {
            return product;
        }

        public ProductPane(Product product) {
            this.product = product;
            int prefSize = 170;
            this.setPrefSize(prefSize, prefSize);
            this.setPadding(new Insets(10));

            ImageView imageView = product.setImageView(new ImageView());
            imageView.setFitWidth(prefSize - 20);
            imageView.setFitHeight(prefSize - 20);

            this.getChildren().add(imageView);

            Label productLabel = new Label(product.getName());
            Label priceLabel = new Label(product.getPrice() + " $");
            Rating rating = new Rating();
            rating.setRate(product).setSize(18);
            productLabel.setId("productName");
            priceLabel.setId("price");
            this.getStyleClass().add("bobble");

            this.getChildren().addAll(productLabel, priceLabel, rating);
        }
    }
}
