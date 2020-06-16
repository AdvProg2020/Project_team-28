package graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AccountPage {
    public void initialize() throws IOException {
        URL url;
        String fxmlPath;
        switch (Main.controller.getPersonalInfo("type")) {
            case "customer":
                fxmlPath = "src/main/resources/fxml/Profile.fxml";
                break;
            case "seller":
                fxmlPath = "src/main/resources/fxml/SellerPage.fxml";
                break;
            case "manager":
                fxmlPath = "src/main/resources/fxml/ManagerPage.fxml";
                fxmlPath = "src/main/resources/fxml/MainMenu.fxml";
                System.out.println("va");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Main.controller.getPersonalInfo("type"));
        }

        Main.setMainStage("", fxmlPath);
    }
}
