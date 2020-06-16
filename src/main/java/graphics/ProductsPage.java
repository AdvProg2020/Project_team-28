package graphics;

import controller.Database;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import main.Main;
import model.Product;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ProductsPage {
    public GridPane gridPane;

    @FXML
    public void initialize() {

        //Main.setMainStageSize(800, 600);
        gridPane.setPadding(new Insets(30));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // TODO : change it!
        Label header = new Label("UNDER CONSTRUCTION");
        header.setStyle("-fx-font-size: 20px; -fx-alignment: center");
        GridPane.setConstraints(header, 1, 0, 2, 1);
        gridPane.getChildren().add(header);

        ArrayList<Product> products = Database.getAllProducts();
        int counter = 0;
        for (Product product : products) {
            ProductPane pane = new ProductPane(product);

            gridPane.getChildren().add(pane);
            GridPane.setConstraints(pane, counter % 3, 1 + counter / 3);
            counter++;

            pane.setOnMouseClicked(event -> {
                // TODO : connect to product page
                System.out.println("Let's buy this product");
            });
        }
    }

    public static class ProductPane extends VBox {

        final int prefSize = 170;

        public ProductPane(Product product) {
            //this.setMaxSize(prefSize, prefSize);
            this.setPrefSize(prefSize, prefSize);
            this.setPadding(new Insets(10));

            ImageView imageView = product.getProductImage();
            imageView.setFitWidth(prefSize - 20);
            imageView.setFitHeight(prefSize - 20);

            this.getChildren().add(imageView);

            Label productLabel = new Label(product.getName());
            Label priceLabel = new Label(Long.toString(product.getPrice()) + " $");
            Label scoreLabel = new Label(Double.toString(product.getAverageScore()));
            productLabel.setId("productName");
            priceLabel.setId("price");
            scoreLabel.setId("score");
            this.setId("productPane");

            this.getChildren().addAll(productLabel, priceLabel, scoreLabel);
        }
    }
}
