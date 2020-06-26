package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Main;
import model.Category;
import model.Product;
import model.Property;
import model.Seller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class AddProductPage {
    public JFXButton selectCategoryButton;
    public JFXTextField quantity;
    public VBox mainBox;
    private SellerController controller;
    public RequiredFieldValidator requiredVal;
    public RegexValidator numberValid;
    public JFXTextField name;
    public JFXTextField brand;
    public JFXTextField price;
    public JFXTextArea description;
    public RegexValidator priceValid;
    public FileChooser fileChooser;
    public Label imagePath;
    public Label videoPath;
    public ImageView productImage;
    public JFXButton imageButton;
    public JFXButton videoButton;
    public Image selectedImage;
    public Label categoryName;
    public VBox categoryBox;
    public static Stage popupStage;
    private ArrayList<JFXTextField> allTextFields;
    private Product loadedProduct;
    private String selectedCategory;
    private String imageUri;
    private String videoUri;

    public AddProductPage show(SellerController controller) throws IOException {
        System.out.println("show: " + controller);
        setController(controller);
        URL url = new File("src/main/resources/fxml/AddProductPage.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ((AddProductPage) fxmlLoader.getController()).setController(controller);
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Add Product Page");
        Main.popupStage.setScene(new Scene(root, 250, 350));
        Main.popupStage.show();
        return (AddProductPage) fxmlLoader.getController();
    }

    public void setController(SellerController controller) {
        this.controller = controller;
    }

    public void initialize() {
        controller = Main.sellerController;
        priceValid.setRegexPattern("\\d+");
        numberValid.setRegexPattern("\\d+");
        allTextFields = new ArrayList<>();
        allTextFields.add(price);
        allTextFields.add(name);
        allTextFields.add(brand);

        price.getValidators().add(priceValid);
        price.getValidators().add(requiredVal);
        name.getValidators().add(requiredVal);
        brand.getValidators().add(requiredVal);
        quantity.getValidators().add(numberValid);

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
        quantity.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue)
                quantity.validate();
        });
    }

    public void browseForImage() throws MalformedURLException {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files",
                "*.png", "*.jpeg", "*.jpg", "*.bmp"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            imageUri = file.toURI().toURL().toString();
            selectedImage = new Image(imageUri);
            imagePath.setText(file.getAbsolutePath());
            productImage.setImage(selectedImage);
        }
    }

    public void browseForVideo() throws MalformedURLException {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Video Files",
                "*.mp4"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            videoUri = file.toURI().toURL().toString();
            videoPath.setText(file.getAbsolutePath());
        }
    }

    public void loadProduct() throws Exception {
        FXMLLoader loader = new ProductsList().show((Seller) controller.getUser());
        ProductsList list = loader.getController();
        loadedProduct = list.getSelectedProduct();
        if (loadedProduct != null)
            loadProductFields();
    }

    private void loadProductFields() {
        price.setText(String.valueOf(loadedProduct.getPrice()));
        name.setText(loadedProduct.getName());
        brand.setText(loadedProduct.getBrand());
        description.setText(loadedProduct.getDescription());
        selectedCategory = loadedProduct.getCategory().getName();
        addCategoryFields();
        categoryName.setText(selectedCategory);
        for (Node child : categoryBox.getChildren()) {
            if (!(child instanceof JFXTextField))
                continue;
            Property thisProperty = loadedProduct.getSpecialPropertyByName(((JFXTextField) child).getPromptText());
            if (thisProperty != null)
                ((JFXTextField) child).setText(thisProperty.getValue());
        }
        if (loadedProduct.getImageAddress() != null) {
            productImage.setImage(loadedProduct.getProductImage());
            imageUri = loadedProduct.getImageAddress();
        }
    }

    public void setLoadedProduct(Product product) {
        loadedProduct = product;
        loadProductFields();
    }

    public void disableNodes(Pane pane) {
        if (pane == null) {
            pane = mainBox;
        }
        for (Node child : pane.getChildren()) {
            if (child instanceof Pane)
                disableNodes((Pane) child);
            else
                child.setDisable(true);
        }
    }

    private void disableTextFields() {
        for (JFXTextField field : allTextFields) {
            field.setEditable(false);
            field.setDisable(true);
        }
    }

    public void selectCategory(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/resources/fxml/CategoriesList.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        popupStage = new Stage();
        popupStage.setTitle("Select A Category");
        popupStage.setScene(new Scene(root, 300, 400));
        popupStage.showAndWait();

        CategoriesList list = loader.getController();
        selectedCategory = list.getSelectedCategory();
        if (selectedCategory == null)
            return;
        categoryName.setText(selectedCategory);
        System.out.println(categoryBox);
        addCategoryFields();
    }

    private void addCategoryFields() {
        categoryBox.getChildren().clear();
        Category category = Database.getCategoryByName(selectedCategory);
        if (category.getSpecialProperties() == null)
            return;
        for (Property property : category.getSpecialProperties()) {
            JFXTextField thisProperty = new JFXTextField();
            thisProperty.setMaxWidth(250);
            thisProperty.setPromptText(property.getName());
            thisProperty.setLabelFloat(true);
            categoryBox.getChildren().add(thisProperty);
            allTextFields.add(thisProperty);
            if (property.isNumber()) {
                thisProperty.getValidators().add(numberValid);
                thisProperty.focusedProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue)
                        thisProperty.validate();
                });
            }
        }
    }

    public void submitProduct() throws Exception {
        boolean allInputs = name.validate() && brand.validate() && price.validate() && quantity.validate();
        if (!allInputs) {
            return;
        }
        Product product = new Product(name.getText(), brand.getText(),
                price.getText(), description.getText(), controller.getUser().getId(), categoryName.getText());
        product.setQuantity(Integer.parseInt(quantity.getText()));
        product.setImageAddress(imageUri);
        product.setVideoAddress(videoUri);
        for (Node child : categoryBox.getChildren()) {
            if (child instanceof JFXTextField) {
                Property property = new Property(Database.getPropertyByName(((JFXTextField) child).getPromptText()));
                if (((JFXTextField) child).getText().matches("\\d+")) {
                    property.setNumber(true);
                    property.setValueLong(Long.parseLong(((JFXTextField) child).getText()));
                } else {
                    property.setNumber(false);
                    property.setValueString(((JFXTextField) child).getText());
                }
                Database.add(property);
                product.addSpecialProperty(property);
            }
        }
        if (loadedProduct != null) {
            controller.editProduct(loadedProduct.getId(), product);
        } else
            controller.addProduct(product);
//            if (!loadedProduct.getSellers().contains(controller.getUser()))
//                loadedProduct.addSeller(controller.getUser());
//            loadedProduct.setQuantity(loadedProduct.getQuantity() + Integer.parseInt(quantity.getText()));

        Main.popupStage.close();
    }
}
