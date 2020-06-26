package graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.Seller;

import java.io.File;
import java.io.IOException;
import java.net.URL;


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

    public void manageSalesButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        ManageSalesPage.show(Main.controller.getUser());
    }

    public void viewSellLogsPressed(ActionEvent actionEvent) {
        backgroundMediaView.getMediaPlayer().stop();
        try {
            new SellLogList().show(Main.sellerController);
        } catch (IOException ignored) {
        }
    }

    static void showRemovePage(MediaView backgroundMediaView) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        URL url = new File("src/main/resources/fxml/RemoveProductPage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Remove Product");
        Main.popupStage.setScene(new Scene(root, 250, 250));
        Main.popupStage.initModality(Modality.WINDOW_MODAL);
        Main.popupStage.initOwner(Main.mainStage);
        Main.popupStage.show();
    }

    public void removeProductButtonPressed(ActionEvent actionEvent) throws IOException {
        showRemovePage(backgroundMediaView);
    }

    public void advertisementPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        URL url = new File("src/main/resources/fxml/AdvertiseProductPage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Advertisement");
        Main.popupStage.setScene(new Scene(root, 250, 250));
        Main.popupStage.initModality(Modality.WINDOW_MODAL);
        Main.popupStage.initOwner(Main.mainStage);
        Main.popupStage.show();
    }
}
