package graphics;

import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import main.Main;

import java.io.File;
import java.io.IOException;


public class SellerPage {

    public MediaView backgroundMediaView;

    public void initialize() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/video/chorus-video.mp4").toURI().toString()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.cycleCountProperty().set(Integer.MAX_VALUE);
        backgroundMediaView.mediaPlayerProperty().set(mediaPlayer);
        backgroundMediaView.setFitWidth(Main.mainStage.getWidth());
        backgroundMediaView.setPreserveRatio(true);
    }

    public void profilePageButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        Main.setMainStage("Profile", "src/main/resources/fxml/Profile.fxml");
    }

    public void newProductButtonPressed(ActionEvent actionEvent) {
        try {
            new AddProductPage().show(Main.sellerController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newSaleButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        Main.setMainStage("Add Sale", "src/main/resources/fxml/AddSalePage.fxml");
    }

    public void manageRequestsButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        Main.setMainStage("Manage Requests",
                "src/main/resources/fxml/SellerRequestsPage.fxml");
    }

    public void manageProductsButtonPressed(ActionEvent actionEvent) {
        //TODO connect to add product manage page
    }

    public void manageSalesButtonPressed(ActionEvent actionEvent) {
        //TODO connect to add sale manage page
    }
}
