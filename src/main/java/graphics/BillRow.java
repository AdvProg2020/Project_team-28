package graphics;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Product;

public class BillRow extends RecursiveTreeObject<BillRow> {
    StringProperty name = new SimpleStringProperty();
    StringProperty price = new SimpleStringProperty();
    StringProperty quantity = new SimpleStringProperty();
    StringProperty totalPrice = new SimpleStringProperty();

    public BillRow (String name) {
        this.name.setValue(name);
    }

    public BillRow (Product product, int quantity) {
        name.setValue(product.getBrand() + "\t" + product.getName());
        price.setValue(String.valueOf(product.getPrice()));
        this.quantity.setValue(String.valueOf(quantity));
        totalPrice.setValue(String.valueOf(product.getPrice() * quantity));
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
}
