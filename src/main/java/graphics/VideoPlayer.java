package graphics;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import main.Main;

public class VideoPlayer {
    public StackPane root;

    public void setVideo(String videoAddress) {

        Media media = new Media(videoAddress);
        MediaPlayer player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);

        mediaView.setFitWidth(500.0);
        mediaView.setPreserveRatio(true);

        root.getChildren().add(mediaView);
        Main.setPopupStageSize(500, 500);
        Main.popupStage.setOnCloseRequest(event -> player.stop());


        player.play();
    }

    @FXML
    public void exitApplication() {

    }
}
