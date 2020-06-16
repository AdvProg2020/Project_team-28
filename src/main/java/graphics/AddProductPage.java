package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import controller.Database;
import controller.ProductController;
import controller.SellerController;
import controller.UserController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Product;
import model.Seller;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AddProductPage {
    private SellerController controller;
    public RequiredFieldValidator requiredVal;
    public JFXTextField name;
    public JFXTextField brand;
    public JFXTextField price;
    public RegexValidator priceValid;
    public ImageView redX;
    public ImageView greenCheck;
    public Stage stage;
    public FileChooser fileChooser;
    public Label filePath;
    public ImageView productImage;
    public JFXButton browseButton;
    public Image selectedImage;
    public Label categoryName;

    public void show(SellerController controller) throws IOException {
        setController(controller);
        URL url = new File("src/main/resources/fxml/AddProductPage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage = new Stage();
        stage.setTitle("Add Product Page");
        stage.setScene(new Scene(root, 250, 350));
        stage.show();
    }

    public void setController (SellerController controller) {
        this.controller = controller;
    }

    public void initialize () {
        priceValid.setRegexPattern("\\d+");
        price.getValidators().add(priceValid);
        price.getValidators().add(requiredVal);
        name.getValidators().add(requiredVal);
        brand.getValidators().addAll(requiredVal);

        name.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                name.validate();
        });
        brand.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                brand.validate();
        });
        price.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                price.validate();
            }
        });
    }

    public void browseForFile(ActionEvent actionEvent) {
        fileChooser = new FileChooser();
        String[] extensions = {"png", "jpeg", "jpg", "bmp"};
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files",
                "*.png", "*.jpeg", "*.jpg", "*.bmp"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            selectedImage = new Image(Paths.get(file.getAbsolutePath()).toUri().toString());
            filePath.setText(file.getAbsolutePath());
            productImage.setImage(selectedImage);
        }
    }

    public void selectCategory(ActionEvent actionEvent) {
        System.out.println("Open categories page");
    }

    public void submitProduct(ActionEvent actionEvent) {
        boolean allInputs = name.validate() && brand.validate() && price.validate();
        if (!allInputs) {
            return;
        }
        Product product = new Product(name.getText(), brand.getText(),
                price.getText(), controller.getUser().getId(), categoryName.getText());
        controller.addProduct(product);
    }
}
