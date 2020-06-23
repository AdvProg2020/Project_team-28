package graphics;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import model.*;
import model.exception.DefaultUser;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ProductPage {
    public VBox commentArea;
    public TableView<Property> tableView = new TableView<>();
    public VBox specSection;
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

        TableColumn<Property, String> nameColumn = new TableColumn<>("Property");
        TableColumn<Property, String> valueColumn = new TableColumn<>("Value");

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        // TODO set TableView style in css
        tableView.setItems(product.getAllProperties());
        tableView.setMaxWidth(300);
        nameColumn.setPrefWidth(150);
        valueColumn.setPrefWidth(150);

        tableView.setFixedCellSize(50);
        tableView.setMaxHeight(tableView.getFixedCellSize() * (tableView.getItems().size()));
        tableView.getColumns().addAll(nameColumn, valueColumn);

        specSection.getChildren().add(tableView);

        for (Comment comment : product.getAllComments()) {
            if (comment.getDepth() == 0)
                insertComment(comment);
        }
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

    public void cartPressed(ActionEvent actionEvent) throws Exception {
        ((Customer) Main.controller.getUser()).addToCart(product.getId(),
                Integer.parseInt(spinner.getValue().toString()));
        if (Main.customerController == null)
            throw new Exception("Please login first");
        new Cart(Main.customerController).show();
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
        ((AddCommentPage) fxmlLoader.getController()).setComment(comment);
    }

    public class CommentPane extends HBox {
        int leftSize = 60;
        int rightSize = 440;

        public CommentPane(Comment comment) {
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
