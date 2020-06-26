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

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ManagerPage {

    public MediaView backgroundMediaView;

    public void initialize() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(new File("src/main/resources/video/output.mp4").toURI().toString()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.cycleCountProperty().set(Integer.MAX_VALUE);
        backgroundMediaView.mediaPlayerProperty().set(mediaPlayer);
    }

    public void profilePageButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        Main.setMainStage("Profile Page", "src/main/resources/fxml/Profile.fxml");
    }

    public void manageRequestsButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        Main.setMainStage("Manage Requests",
                "src/main/resources/fxml/ManagerRequestsPage.fxml");
    }

    public void addManagerButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        URL url = new File("src/main/resources/fxml/AddManagerMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Add new manager");
        Main.popupStage.setScene(new Scene(root, 250, 250));
        Main.popupStage.initModality(Modality.WINDOW_MODAL);
        Main.popupStage.initOwner(Main.mainStage);
        Main.popupStage.show();
    }

    public void addDiscountButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        // TODO connect to discount manager menu
        backgroundMediaView.getMediaPlayer().stop();
        System.out.println("Manager Controller: " + Main.managerController);
        new ManageDiscountsPage().show(null);
    }

    public void manageUsersButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        Main.setMainStage("Manage Users", "src/main/resources/fxml/UserViewer.fxml");
    }

    public void removeProductPressed(ActionEvent actionEvent) throws IOException {
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

    public void manageCategoriesButtonPressed(ActionEvent actionEvent) throws IOException {
        backgroundMediaView.getMediaPlayer().stop();
        Main.setMainStage("Manage Categories", "src/main/resources/fxml/ManageCategories.fxml");
    }
}
