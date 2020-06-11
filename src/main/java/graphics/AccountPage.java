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
        switch (Main.controller.getPersonalInfo("type")) {
            case "customer":
                url = new File("src/main/resources/fxml/Profile.fxml").toURI().toURL();
                break;
            case "seller":
                url = new File("src/main/resources/fxml/SellerPage.fxml").toURI().toURL();
                break;
            case "manager":
                url = new File("src/main/resources/fxml/ManagerPage.fxml").toURI().toURL();
                url = new File("src/main/resources/fxml/MainMenu.fxml").toURI().toURL();
                System.out.println("va");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + Main.controller.getPersonalInfo("type"));
        }
        Parent root = FXMLLoader.load(url);
        Main.mainStage.setScene(new Scene(root, 620, 450));
    }
}
