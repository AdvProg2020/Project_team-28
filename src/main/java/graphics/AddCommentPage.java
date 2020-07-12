package graphics;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.Main;
import model.Comment;
import model.Product;
import model.Score;

import java.io.IOException;

public class AddCommentPage {
    public JFXTextArea commentText;
    public HBox hbox;
    public JFXTextField commentTitle;
    public Label inReply;
    public Spinner rateSpinner;
    Comment comment;
    Product product;

    @FXML
    public void initialize() {
        if (comment == null)
            return;
        hbox.getChildren().add(new ProductPane(product));
        if (comment.getParent() != null)
            inReply.setText("In reply to \"" + comment.getParent().getTitle() + "\"");
        if (!comment.hasBought())
            rateSpinner.setDisable(true);
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        try {
            this.product = comment.getProduct();
        } catch (Exception ignored) {
        }
        initialize();
    }

    public void backToProduct() throws IOException {
        FXMLLoader fxml = Main.setMainStage("Product", "src/main/resources/fxml/ProductPage.fxml");
        assert fxml != null;
        ((ProductPage) fxml.getController()).setProduct(product);
    }

    public void addCommentPressed() {
        try {
            comment.setTitle(commentTitle.getText());
            comment.setText(commentText.getText());
            Database.add(comment);
            product.addComment(comment);
            Comment parent = comment.getParent();
            if (parent != null) {
                comment.getParent().addChild(comment);
                Database.update(parent, parent.getId());
            }
            if (comment.hasBought()) {
                Score score = new Score(comment.getUser()
                        , Integer.parseInt(rateSpinner.getValue().toString())
                        , comment.getProduct());
                product.addScore(score);
                Database.add(score);
            }
            Database.update(product, product.getId());
            backToProduct();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
