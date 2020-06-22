package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import controller.Database;
import controller.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.Category;
import model.Product;
import model.Property;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class AddProductPage {
    private SellerController controller;
    public RequiredFieldValidator requiredVal;
    public JFXTextField name;
    public JFXTextField brand;
    public JFXTextField price;
    public RegexValidator priceValid;
    public FileChooser fileChooser;
    public Label filePath;
    public ImageView productImage;
    public JFXButton browseButton;
    public Image selectedImage;
    public Label categoryName;
    public VBox categoryBox;
    public static Stage popupStage;

    private String selectedCategory;

    public void show(SellerController controller) throws IOException {
        System.out.println("show: " + controller);
        setController(controller);
        URL url = new File("src/main/resources/fxml/AddProductPage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Add Product Page");
        Main.popupStage.setScene(new Scene(root, 250, 350));
        Main.popupStage.show();
    }

    public void setController (SellerController controller) {
        this.controller = controller;
    }

    public void initialize () {
        controller = Main.sellerController;
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

    public void selectCategory(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/resources/fxml/CategoriesList.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        popupStage = new Stage();
        popupStage.setTitle("Select A Category");
        popupStage.setScene(new Scene(root, 500, 600));
        popupStage.showAndWait();

        CategoriesList list =loader.<CategoriesList>getController();
        selectedCategory = list.getSelectedCategory();
        categoryName.setText(selectedCategory);
        System.out.println(categoryBox);
        addCategoryFields();
    }

    private void addCategoryFields() {
        Category category = Database.getCategoryByName(selectedCategory);
        if (category.getSpecialProperties() == null)
            return;
        for (Property property : category.getSpecialProperties()) {
            JFXTextField thisProperty = new JFXTextField();
            thisProperty.setMaxWidth(250);
            thisProperty.setPromptText(property.getName());
            thisProperty.setLabelFloat(true);
            categoryBox.getChildren().add(thisProperty);
            if (property.isNumber()) {
                thisProperty.getValidators().add(priceValid);
                thisProperty.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue)
                        thisProperty.validate();
                });
            }
        }
    }

    public void submitProduct(ActionEvent actionEvent) {
        System.out.println("submit: " + controller);
        boolean allInputs = name.validate() && brand.validate() && price.validate();
        if (!allInputs) {
            return;
        }
        Product product = new Product(name.getText(), brand.getText(),
                price.getText(), controller.getUser().getId(), categoryName.getText());
        for (Node child : categoryBox.getChildren()) {
            if (child instanceof JFXTextField) {
                Property property = new Property(Database.getPropertyByName(((JFXTextField) child).getPromptText()));
                if (((JFXTextField) child).getText().matches("\\d+")) {
                    property.setNumber(true);
                    property.setValueLong(Long.parseLong(((JFXTextField) child).getText()));
                }else {
                    property.setNumber(false);
                    property.setValueString(((JFXTextField) child).getText());
                }
                Database.add(property);
                product.addSpecialProperty(property);
            }
        }
        controller.addProduct(product);
        Main.popupStage.close();
    }
}
