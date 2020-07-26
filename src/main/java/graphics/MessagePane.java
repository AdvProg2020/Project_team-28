package graphics;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.Main;
import model.ChatMessage;
import model.User;
import model.exception.DefaultUser;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class MessagePane extends HBox {

    int width = 400 - 30;

    public MessagePane(ChatMessage message) {
        boolean isSender = message.getSenderId().equals(Main.controller.getUser().getId());

        this.setPrefWidth(width);

        VBox textBox = new VBox();
        VBox voidBox = new VBox();

        textBox.setPrefWidth(width);
        textBox.setPadding(new Insets(10));
        textBox.setAlignment(Pos.CENTER_LEFT);

        voidBox.setMinWidth(50);
        voidBox.setMaxWidth(50);

        //User user = new DefaultUser();
        //ImageView userImage = new ImageView(user.getProfilePictureAddress());


        Label messageText = new Label(message.getText());
        messageText.setWrapText(true);

        textBox.getChildren().addAll(messageText);


        String textStyle = "-fx-border-radius: 5;\n" +
                "-fx-border-width: 1;\n" +
                "-fx-border-color: #BDBDBD;\n" +
                "-fx-spacing: 10px;\n" +
                "-fx-alignment: center-left;";

        if (isSender) {
            textStyle += "-fx-background-color: #EFFDDE";
            this.getChildren().addAll(voidBox, textBox);
        } else {
            textStyle += "-fx-background-color: white";
            this.getChildren().addAll(textBox, voidBox);
        }
        textBox.setStyle(textStyle);
    }
}
