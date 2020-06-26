package graphics;

import com.jfoenix.controls.JFXTextField;
import controller.Database;
import controller.ProductController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Main;
import model.*;
import model.exception.DefaultUser;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;

public class ProductPage {
    public HBox similarBox;
    private Product product;

    public VBox mediaBox;
    public Pane imagePane;
    public ImageView productImage;
    public Button imageButton;
    public Button videoButton;

    public VBox infoBox;
    public Label productName;
    public Label productBrand;
    public Label productPrice;
    public Label productInfo;
    public Rating rating;
    public Spinner spinner;
    public SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory;
    public Button cartButton;

    public VBox specSection;
    public TableView<Property> tableView = new TableView<>();

    public VBox commentArea;

    public JFXTextField anotherId;
    public VBox compareArea;

    @FXML
    public void initialize() {
        if (product == null)
            return;
        productName.setText(product.getName());
        productBrand.setText(product.getBrand());
        productPrice.setText(product.getPrice() + " $");
        productInfo.setText(product.getDescription());
        rating.setRate(product);
        product.setImageView(productImage);
        if (product.hasOff()) {
            if (product.hasOff()) {
                Label offLabel = new Label(100 - 100 * product.getPrice() / product.getPurePrice() + "%");
                offLabel.setId("offLabel");
                imagePane.getChildren().add(offLabel);
            }
        }

        checkMedia();

        checkQuantity();

        if (!(Main.controller.getUser() instanceof Customer)) {
            spinner.setDisable(true);
            cartButton.setDisable(true);
            infoBox.getChildren().add(new Label("Only customers can offer products"));
        }

        showSpecs();

        showSimilar();

        showComments();
    }

    private void showSimilar() {
        ArrayList<Product> products = new ArrayList<>(Database.getAllProducts());
        products.remove(product);
        products.sort(Comparator.comparingDouble(o -> ProductController.similarity(product, (Product) o)).reversed());
        for (int i = 0; i < 3 && i < products.size(); i++)
            similarBox.getChildren().add(new ProductPane(products.get(i)));
    }

    private void showComments() {
        for (Comment comment : product.getAllComments()) {
            if (comment.getDepth() == 0)
                insertComment(comment);
        }
    }

    private void showSpecs() {
        TableColumn<Property, String> nameColumn = new TableColumn<>("Property");
        TableColumn<Property, String> valueColumn = new TableColumn<>("Value");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableView.setItems(product.getAllProperties());
        tableView.setItems(product.getAllSpecialProperties());

        tableView.setMaxWidth(300);
        nameColumn.setPrefWidth(145);
        valueColumn.setPrefWidth(145);

        tableView.setFixedCellSize(50);
        tableView.setMaxHeight(tableView.getFixedCellSize() * (tableView.getItems().size()));
        tableView.getColumns().addAll(nameColumn, valueColumn);
        tableView.setPlaceholder(new Label("No specification available"));

        specSection.getChildren().add(tableView);
    }

    private void checkMedia() {
        if (product.getImageAddress() == null || product.getImageAddress().contains("no-product")) {
            imageButton.setDisable(true);
        }
        if (product.getVideoAddress() == null) {
            videoButton.setDisable(true);
        }
    }

    private void checkQuantity() {
        if (product.getQuantity() <= 0) {
            spinner.setDisable(true);
            cartButton.setDisable(true);
            infoBox.getChildren().add(new Label("Sorry, product is out of stock"));
        }
        else if (product.getQuantity()
                <= Main.customerController.getCustomerLoggedOn().getProductInCart(product.getId())) {
            spinner.setDisable(true);
            cartButton.setDisable(true);
            infoBox.getChildren().add(new Label("You cannot offer this product any more"));
        } else {
            spinner.setDisable(false);
            cartButton.setDisable(false);
        }
        valueFactory.setMax(product.getQuantity()
                - Main.customerController.getCustomerLoggedOn().getProductInCart(product.getId()));
    }

    private void insertComment(Comment comment) {
        commentArea.getChildren().add(new CommentPane(comment));
        for (Comment child : comment.getChildren()) {
            insertComment(child);
        }
    }

    public void setProduct(Product product) {
        this.product = product;
        initialize();
    }

    public void cartPressed() {

        checkQuantity();

        Customer customer = (Customer) Main.controller.getUser();
        if (customer.addToCart(product, Integer.parseInt(spinner.getValue().toString())))
            Main.setMainStage("Cart", "src/main/resources/fxml/Cart.fxml");

        checkQuantity();
    }

    public void addCommentPressed() {
        goToCommentPage(null);
    }

    private void goToCommentPage(Comment parent) {
        if (Main.controller.getUser() instanceof DefaultUser) {
            Main.showErrorDialog(new Exception("You have to login"));
            return;
        }
        Comment comment = new Comment(parent, Main.controller.getUser(), product, "", "");
        FXMLLoader fxmlLoader = Main.setMainStage("Add Comment", "src/main/resources/fxml/AddCommentPage.fxml");
        assert fxmlLoader != null;
        ((AddCommentPage) fxmlLoader.getController()).setComment(comment);
    }

    public void imagePressed() {
        FXMLLoader fxml = Main.setPopupStage("Image Zoom", "src/main/resources/fxml/ZoomPage.fxml");
        assert fxml != null;
        ((ZoomPage) fxml.getController()).show(product.getName(), product.getImageAddress());
        //Main.setPopupStageSize((productImage.getFitWidth())+70,(productImage.getFitHeight())+150);
    }

    public void videoPressed() {
        FXMLLoader fxml = Main.setPopupStage("Video Player", "src/main/resources/fxml/VideoPlayer.fxml");
        ((VideoPlayer)fxml.getController()).setVideo(product.getVideoAddress());

    }

    public void comparePressed() {
        try {
            Product anotherProduct = Database.getProductById(anotherId.getText());
            if (product.equals(anotherProduct))
                throw new Exception("Choose a different product");
            FXMLLoader fxml = Main.setMainStage("Compare Page", "src/main/resources/fxml/ComparePage.fxml");
            ((ComparePage)fxml.getController()).setProducts(product, anotherProduct);

        } catch (Exception e) {
            Main.showErrorDialog(e);
        }
    }

    public void copyPressed() {
        StringSelection stringSelection = new StringSelection(product.getId());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        compareArea.getChildren().add(new Label("product id copied into clipboard"));
    }

    public class CommentPane extends HBox {
        int leftSize = 60;
        int rightSize = 440;

        public CommentPane(Comment comment) {
            if (comment == null)
                System.out.println("sadadfdfsd");
            rightSize -= comment.getDepth() * leftSize;

            this.setMinWidth(leftSize + rightSize);
            this.setMaxWidth(leftSize + rightSize);
            this.setPadding(new Insets(10));

            VBox userBox = new VBox();
            VBox commentBox = new VBox();

            User user = comment.getUser();
            if (user == null)
                user = new DefaultUser();
            ImageView userImage = new ImageView(user.getProfilePictureAddress());
            userImage.setFitWidth(leftSize);
            userImage.setFitHeight(leftSize);
            Label userLabel = new Label(user.getUsername());
            Label dateLabel = new Label(comment.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
            Hyperlink reply = new Hyperlink("reply");
            reply.setOnAction(event -> goToCommentPage(comment));

            userBox.getChildren().addAll(userImage, userLabel, dateLabel);
            if (comment.getDepth() < 3)
                userBox.getChildren().addAll(reply);
            userBox.setMinWidth(leftSize);
            userBox.setMaxWidth(leftSize);
            userBox.setAlignment(Pos.CENTER);

            Label commentTitle = new Label(comment.getTitle());
            commentTitle.setStyle("-fx-font-weight: bold");
            Label commentText = new Label(comment.getText());
            commentText.setWrapText(true);

            commentBox.getChildren().addAll(commentTitle, new Separator(), commentText);

            this.getChildren().addAll(userBox, commentBox);
            this.setId("commentPane");
        }
    }
}
