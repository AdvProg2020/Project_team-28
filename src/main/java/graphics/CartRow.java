package graphics;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;
import model.Product;

public class CartRow extends RecursiveTreeObject<CartRow> {
    StringProperty name = new SimpleStringProperty();
    StringProperty price = new SimpleStringProperty();
    StringProperty quantity = new SimpleStringProperty();
    StringProperty totalPrice = new SimpleStringProperty();
    ImageView productImage;
    Product product;

    public CartRow (String name) {
        this.name.setValue(name);
    }

    public CartRow(Product product, int quantity) {
        name.setValue(product.getBrand() + "\t" + product.getName());
        price.setValue(String.valueOf(product.getPrice()));
        this.quantity.setValue(String.valueOf(quantity));
        totalPrice.setValue(String.valueOf(product.getPrice() * quantity));
        productImage = new ImageView(product.getProductImage());
        productImage.setFitWidth(50);
        productImage.setPreserveRatio(true);
        this.product = product;
    }

    public void update () {
        totalPrice.setValue(String.valueOf(Integer.parseInt(quantity.getValue()) * Integer.parseInt(price.getValue())));
    }

    public Product getProduct() {
        return product;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPrice() {
        return price.get();
    }

    public StringProperty priceProperty() {
        return price;
    }

    public String getQuantity() {
        return quantity.get();
    }

    public StringProperty quantityProperty() {
        return quantity;
    }

    public String getTotalPrice() {
        return totalPrice.get();
    }

    public StringProperty totalPriceProperty() {
        return totalPrice;
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public void setProductImage(ImageView productImage) {
        this.productImage = productImage;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
