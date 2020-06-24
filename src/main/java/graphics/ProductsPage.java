package graphics;

import controller.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import main.Main;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProductsPage {
    public GridPane gridPane;

    @FXML
    public void initialize() {

        //Main.setMainStageSize(800, 600);
        gridPane.setPadding(new Insets(30));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        ArrayList<Product> products = Database.getAllProducts();
        int counter = 0;
        for (Product product : products) {
            ProductPane pane = new ProductPane(product);

            gridPane.getChildren().add(pane);
            GridPane.setConstraints(pane, counter % 3, counter / 3);
            counter++;

            pane.setOnMouseClicked(event -> {
                // TODO : connect to product page
                FXMLLoader fxmlLoader;
                fxmlLoader = Main.setMainStage("Product","src/main/resources/fxml/ProductPage.fxml");
                ((ProductPage) fxmlLoader.getController()).setProduct(pane.getProduct());

                System.out.println("Let's buy this product");
            });
        }
    }

    public static class ProductPane extends VBox {

        private final int prefSize = 170;
        private Product product;

        public Product getProduct() {
            return product;
        }

        public ProductPane(Product product) {
            this.product = product;
            this.setPrefSize(prefSize, prefSize);
            this.setPadding(new Insets(10));

            ImageView imageView = new ImageView(product.getProductImage());
            imageView.setFitWidth(prefSize - 20);
            imageView.setFitHeight(prefSize - 20);

            this.getChildren().add(imageView);

            Label productLabel = new Label(product.getName());
            Label priceLabel = new Label(product.getPrice() + " $");
            Rating rating = new Rating();
            rating.setRate(product).setSize(18);
            productLabel.setId("productName");
            priceLabel.setId("price");
            this.setId("productPane");

            this.getChildren().addAll(productLabel, priceLabel, rating);
        }
    }
}
