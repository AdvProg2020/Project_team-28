package graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;


public class SellerPage {

    public MediaView backgroundMediaView;

    public void initialize() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/video/output.mp4").toURI().toString()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.cycleCountProperty().set(Integer.MAX_VALUE);
        backgroundMediaView.mediaPlayerProperty().set(mediaPlayer);
    }

    public void profilePageButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        URL url = new File("src/main/resources/fxml/Profile.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.mainStage.setScene(new Scene(root, 620, 450));
    }

    public void newProductButtonPressed(ActionEvent actionEvent) {
        //TODO connect to add product page
    }

    public void newSaleButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        URL url = new File("src/main/resources/fxml/AddSalePage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.mainStage.setScene(new Scene(root, 620, 450));
    }

    public void manageRequestsButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        URL url = new File("src/main/resources/fxml/SellerRequestsPage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.mainStage.setScene(new Scene(root, 620, 450));
    }

    public void manageProductsButtonPressed(ActionEvent actionEvent) {
        //TODO connect to add product manage page
    }

    public void manageSalesButtonPressed(ActionEvent actionEvent) {
        //TODO connect to add sale manage page
    }
}
