package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import controller.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Product;
import model.Seller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ProductsList {
    public JFXListView<Product> mainList;
    public JFXButton select;
    public Product selectedProduct;
    private Seller seller;

    public FXMLLoader show (Seller seller) throws Exception {
        URL url = new File("src/main/resources/fxml/ProductsList.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        ((ProductsList)loader.getController()).seller = seller;
        ((ProductsList) loader.getController()).loadProducts();
        AddProductPage.popupStage = new Stage();
        AddProductPage.popupStage.setTitle("Select A Product");
        AddProductPage.popupStage.setScene(new Scene(root, 300, 400));
        AddProductPage.popupStage.showAndWait();
        return loader;
    }

    public void loadProducts () throws Exception {
        for (Product product : Database.getAllProducts()) {
            if (product.getSellers().contains(seller))
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
