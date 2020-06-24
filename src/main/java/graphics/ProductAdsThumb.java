package graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ProductAdsThumb extends VBox {
    public ImageView off;
    public ImageView image;
    public Label name;
    public Label price;
    public ImageView offImage;
    public Label offLabel;
    private Product product;

    public ProductAdsThumb (Product product) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/ProductAdsThumb.fxml").toURI().toURL());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.<ProductAdsThumb>getController().product = product;
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void initialize () {
        Image productImage = product.getProductImage();
        image.setImage(productImage);
        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        if (product.hasOff()) {
            offImage.setImage(new Image(Paths.get("src/main/resources/images/offImage.png").toUri().toString()));
            offLabel.setText(getOffPercentage() + "% OFF!");
            offLabel.setVisible(true);
            offImage.setVisible(true);
        }
    }
    
    private int getOffPercentage () {
        if (product.hasOff()) {
            return product.getOff().getPercentage();
        }
        return 0;
    }
    
    
}
