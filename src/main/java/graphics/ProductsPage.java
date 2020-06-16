package graphics;

import controller.Database;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import model.Product;

import java.io.File;
import java.util.ArrayList;

public class ProductsPage {
    public GridPane gridPane;

    @FXML
    public void initialize() {

        gridPane.setPadding(new Insets(40));

        ArrayList<Product> products = Database.getAllProducts();
        int counter = 0;
        for (Product product : products) {
            ProductPane pane = new ProductPane(product);
            gridPane.getChildren().add(pane);
            GridPane.setConstraints(pane, counter % 4, counter / 4);
            counter++;
        }
    }

    public static class ProductPane extends VBox {

        final int prefSize = 100;

        public ProductPane(Product product) {
            this.setPrefSize(prefSize, prefSize);
            this.setPadding(new Insets(10));

            File file = new File("../resources/images/no-product.png");
            //ImageView imageView = new ImageView(file.toURI().toString());
            //this.getChildren().add(imageView);

            Label productLabel = new Label(product.getName());
            Label priceLabel = new Label(Long.toString(product.getPrice()));
            Label scoreLabel = new Label(Double.toString(product.getAverageScore()));
            productLabel.setId("productName");
            priceLabel.setId("price");
            scoreLabel.setId("score");
            this.setId("productPane");

            this.getChildren().addAll(productLabel, priceLabel, scoreLabel);
        }
    }
}
