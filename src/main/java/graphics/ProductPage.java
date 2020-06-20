package graphics;

import controller.CustomerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import main.Main;
import model.Customer;
import model.Product;

public class ProductPage {
    private Product product;

    public Rating rating;
    public Spinner spinner;
    public Label productInfo;
    public ImageView productImage;
    public Label productName;

    @FXML
    public void initialize() {
        if (product == null)
            return;
        productName.setText(product.getName());
        productInfo.setText(product.getDescription());
        productImage.setImage(product.getProductImage());
        rating.setRate(product);
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void cartPressed(ActionEvent actionEvent) throws Exception {
        ((Customer)Main.controller.getUser()).addToCart(product.getId(),
                Integer.parseInt(spinner.getValue().toString()));
        if (Main.customerController == null)
            throw new Exception("Please login first");
        new Cart(Main.customerController).show();
    }
}
