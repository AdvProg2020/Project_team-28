package graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainMenu {
    public void va(ActionEvent actionEvent) {
        System.out.println("YAAY");
    }

    public void profileButtonPressed(ActionEvent actionEvent) throws IOException {
        System.out.println(Main.controller.getUser());
        if (Main.controller.getUser().getFullName().equals("Anonymous User ")) {
            URL url = new File("src/main/resources/fxml/LoginMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Main.popupStage = new Stage();
            Main.popupStage.setScene(new Scene(root, 250, 250));
            //Main.popupStage.initStyle(StageStyle.UNDECORATED);                Cool stuff :)))
            Main.popupStage.initModality(Modality.WINDOW_MODAL);
            Main.popupStage.initOwner(Main.mainStage);
            Main.popupStage.show();
        } else {
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
                    System.out.println("va");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + Main.controller.getPersonalInfo("type"));
            }
            Parent root = FXMLLoader.load(url);
            System.out.println("" + root + "\n");
            Main.mainStage.setScene(new Scene(root, 620, 450));

        }
    }

    public void productsButtonPressed(ActionEvent actionEvent) throws Exception {
        //TODO connect to products section
        System.out.println("Product section");
        throw new Exception("WHY?");
    }
}
