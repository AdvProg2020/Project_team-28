package graphics;

import com.jfoenix.controls.JFXButton;
import controller.CustomerController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.Main;
import model.exception.DefaultUser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TopBar extends HBox {
    @FXML
    public JFXButton homeButton;
    @FXML
    public JFXButton loginButton;
    @FXML
    public JFXButton registerButton;
    @FXML
    public JFXButton cartButton;
    @FXML
    public JFXButton logoutButton;
    @FXML
    public JFXButton backButton;
    @FXML
    public Text userType;
    @FXML
    public Text username;

    public TopBar() throws MalformedURLException {

        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/TopBar.fxml").toURI().toURL());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        ImageView homeImageView = new ImageView(new Image(new File("src/main/resources/images/home-icon.png").toURI().toString()));
        homeImageView.setFitHeight(40);
        homeImageView.setPreserveRatio(true);
        homeButton.setGraphic(homeImageView);

        ImageView cartImageView = new ImageView(new Image(new File("src/main/resources/images/cart-icon.png").toURI().toString()));
        cartImageView.setFitHeight(40);
        cartImageView.setPreserveRatio(true);
        cartButton.setGraphic(cartImageView);

        Sprite loginSprite = new Sprite(40, 7, 6, 41, new File("src/main/resources/images/portal-sprite-sheet.png"), 10);
        ImageView loginSpriteImageView = loginSprite.getImageView();
        loginButton.setGraphic(loginSpriteImageView);

        Sprite logoutSprite = new Sprite(40, 1, 12, 12, new File("src/main/resources/images/door-sprite-sheet-2.png"), 10);
        ImageView logoutSpriteImageView = logoutSprite.getImageView();
        logoutButton.setGraphic(logoutSpriteImageView);

        Sprite registerSprite = new Sprite(40, 1, 9, 9, new File("src/main/resources/images/chest-sprite-sheet.png"), 10);
        ImageView registerSpriteImageView = registerSprite.getImageView();
        registerButton.setGraphic(registerSpriteImageView);

        ImageView backImageView = new ImageView(new Image(new File("src/main/resources/images/back.png").toURI().toString()));
        backImageView.setFitHeight(40);
        backImageView.setFitWidth(40);
        cartImageView.setPreserveRatio(true);
        backButton.setGraphic(backImageView);



        if (!(Main.controller.getUser() instanceof DefaultUser)) {
            this.getChildren().remove(registerButton);
            this.getChildren().remove(loginButton);
            if (Main.controller.getUser() != null) {
                username.setText(Main.controller.getUser().getUsername());
                userType.setText(Main.controller.getUser().getType());
            }

        } else {
            if (Main.controller.getUser() != null) {
                username.setText("Not registered");
                userType.setText(Main.controller.getUser().getType());
            }
            this.getChildren().remove(logoutButton);
        }

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.017),                // 60 FPS
                ae -> {
                    loginSprite.update();
                    logoutSprite.update();
                    registerSprite.update();
                });

        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    @FXML
    public void mainMenuPressed() throws IOException {
        if (Main.popupStage != null) Main.popupStage.close();
        Main.setMainStage("Main Menu", "src/main/resources/fxml/MainMenu.fxml");
    }

    @FXML
    public void logoutPressed() throws Exception {
        SoundPlayer.playSound("door_creak_closing.wav");
        Main.controller.logout();
        Main.sellerController = null;
        Main.managerController = null;
        Main.customerController = new CustomerController(Main.controller.getUser(), Main.productController);
        Main.setMainStage("Main Menu", "src/main/resources/fxml/MainMenu.fxml");
    }

    @FXML
    public void loginPressed() throws IOException {
        SoundPlayer.playSound("electric_door_opening_1.wav");
        if (Main.popupStage != null) Main.popupStage.close();
        URL url = new File("src/main/resources/fxml/LoginMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Login");
        Main.popupStage.setScene(new Scene(root, 300, 300));
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
        Main.popupStage.setScene(new Scene(root, 300, 450));
        Main.popupStage.initModality(Modality.WINDOW_MODAL);
        Main.popupStage.initOwner(Main.mainStage);
        Main.popupStage.show();
    }

    @FXML
    public void backPressed() {
        Main.returnMainStage();
    }

    @FXML
    public void cartPressed() {
        SoundPlayer.stopBackground();
        if (Main.popupStage != null) Main.popupStage.close();
        //TODO go to cart
    }
}
