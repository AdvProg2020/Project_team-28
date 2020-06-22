package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import controller.Database;
import javafx.event.ActionEvent;
import model.Product;

public class ProductsList {
    public JFXListView<Product> mainList;
    public JFXButton select;
    public Product selectedProduct;

    public void initialize () {
        for (Product product : Database.getAllProducts()) {
            mainList.getItems().add(product);
        }
    }

    public void finishSelection(ActionEvent actionEvent) {
        selectedProduct = mainList.getSelectionModel().getSelectedItem();
        AddProductPage.popupStage.close();
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }
}
