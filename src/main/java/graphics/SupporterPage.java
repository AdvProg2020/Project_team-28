package graphics;

import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import main.Main;

import java.io.File;
import java.io.IOException;

public class SupporterPage {

    public MediaView backgroundMediaView;

    public void initialize() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/video/chorus-video.mp4").toURI().toString()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.cycleCountProperty().set(Integer.MAX_VALUE);
        backgroundMediaView.mediaPlayerProperty().set(mediaPlayer);
        backgroundMediaView.setFitWidth(Main.mainStage.getWidth());
        backgroundMediaView.setPreserveRatio(true);
    }

    public void profilePageButtonPressed() {
        backgroundMediaView.getMediaPlayer().stop();
        Main.setMainStage("Profile", "src/main/resources/fxml/Profile.fxml");
    }

    public void supportPressed() {
        backgroundMediaView.getMediaPlayer().stop();
        Main.setMainStage("Support Area", "src/main/resources/fxml/SupportArea.fxml");
    }
}
