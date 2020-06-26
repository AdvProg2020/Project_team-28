package graphics;


import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Main;
import model.Product;

public class ProductPane extends VBox {

    public ProductPane(Product product) {
        int prefSize = 170;
        this.setPrefSize(prefSize, prefSize);
        this.setPadding(new Insets(10));

        ImageView imageView = product.setImageView(new ImageView());
        imageView.setFitWidth(prefSize - 20);
        imageView.setFitHeight(prefSize - 20);
        Pane imagePane = new Pane();
        imagePane.getChildren().add(imageView);
        imagePane.setMinSize(imageView.getFitWidth(), imageView.getFitHeight());
        Label productLabel = new Label(product.getName());

        this.getChildren().addAll(imagePane, productLabel);

        if (product.hasOff()) {
            Text oldPrice = new Text(product.getPurePrice() + "$");
            oldPrice.setId("oldPrice");
            this.getChildren().add(oldPrice);
            Label offLabel = new Label(100 - 100 * product.getPrice() / product.getPurePrice() + "%");
            offLabel.setId("offLabel");
            imagePane.getChildren().add(offLabel);
        }
        Label priceLabel = new Label(product.getPrice() + " $");

        Rating rating = new Rating();
        rating.setRate(product).setSize(18);
        productLabel.setId("productName");
        priceLabel.setId("price");
        this.getStyleClass().add("bobble");

        this.getChildren().addAll(priceLabel, rating);

        setOnMouseClicked(event -> {
            FXMLLoader fxmlLoader;
            fxmlLoader = Main.setMainStage(product.getName(), "src/main/resources/fxml/ProductPage.fxml");
            assert fxmlLoader != null;
            ((ProductPage) fxmlLoader.getController()).setProduct(product);
        });
    }
}