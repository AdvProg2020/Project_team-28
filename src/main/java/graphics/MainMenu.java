package graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.exception.DefaultUser;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainMenu {
    public void va() {
        System.out.println("YAAY");
    }

    public void initialize() {
        SoundPlayer.playInfinite("paganini.wav");
    }

    public void profileButtonPressed() throws IOException {
        System.out.println(Main.controller.getUser());
        if (Main.controller.getUser() instanceof DefaultUser) {
            URL url = new File("src/main/resources/fxml/LoginMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Main.popupStage = new Stage();
            Main.popupStage.setScene(new Scene(root, 250, 250));
            //Main.popupStage.initStyle(StageStyle.UNDECORATED);                Cool stuff :)))
            Main.popupStage.initModality(Modality.WINDOW_MODAL);
            Main.popupStage.initOwner(Main.mainStage);
            Main.popupStage.show();
        } else {
            SoundPlayer.stopBackground();
            String fxmlPath;
            switch (Main.controller.getPersonalInfo("type")) {
                case "customer":
                    fxmlPath =  "src/main/resources/fxml/Profile.fxml";
                    break;
                case "seller":
                    fxmlPath = "src/main/resources/fxml/SellerPage.fxml";
                    break;
                case "manager":
                    fxmlPath = "src/main/resources/fxml/ManagerPage.fxml";
                    break;
                case "supporter":
                    fxmlPath = "src/main/resources/fxml/SupporterPage.fxml";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Main.controller.getPersonalInfo("type"));
            }
            Main.setMainStage("Profile", fxmlPath);

        }
    }

    public void productsButtonPressed() {
        SoundPlayer.stopBackground();
        System.out.println("Product section");
        Main.setMainStage("Products", "src/main/resources/fxml/ProductsPage.fxml");
    }
}
