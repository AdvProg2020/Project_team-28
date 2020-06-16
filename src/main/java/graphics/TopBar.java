package graphics;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.exception.DefaultUser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TopBar extends HBox {
    @FXML
    public JFXButton loginButton;
    @FXML
    public JFXButton registerButton;
    @FXML
    public JFXButton cartButton;
    @FXML
    public JFXButton logoutButton;

    public TopBar() throws MalformedURLException {

        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/TopBar.fxml").toURI().toURL());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        if (!(Main.controller.getUser() instanceof DefaultUser)) {
            this.getChildren().remove(registerButton);
            this.getChildren().remove(loginButton);
            if (Main.controller.getUser() != null)
                logoutButton.setText(Main.controller.getUser().getFirstName() + " (logout)");
        } else {
            this.getChildren().remove(logoutButton);
        }
    }

    @FXML
    public void mainMenuPressed() throws IOException {
        if (Main.popupStage != null) Main.popupStage.close();
        Main.setMainStage("Main Menu", "src/main/resources/fxml/MainMenu.fxml");
    }

    @FXML
    public void logoutPressed() throws IOException {
        Main.controller.logout();
        Main.setMainStage("Main Menu", "src/main/resources/fxml/MainMenu.fxml");
    }

    @FXML
    public void loginPressed() throws IOException {
        if (Main.popupStage != null) Main.popupStage.close();
        URL url = new File("src/main/resources/fxml/LoginMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Login");
        Main.popupStage.setScene(new Scene(root, 250, 250));
        Main.popupStage.initModality(Modality.WINDOW_MODAL);
        Main.popupStage.initOwner(Main.mainStage);
        Main.popupStage.show();
    }

    @FXML
    public void registerPressed() throws IOException {
        if (Main.popupStage != null) Main.popupStage.close();
        URL url = new File("src/main/resources/fxml/RegisterMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("register");
        Main.popupStage.setScene(new Scene(root, 250, 450));
        Main.popupStage.initModality(Modality.WINDOW_MODAL);
        Main.popupStage.initOwner(Main.mainStage);
        Main.popupStage.show();
    }

    @FXML
    public void cartPressed() {
        if (Main.popupStage != null) Main.popupStage.close();
        //TODO go to cart
    }
}
