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
            //TODO connect to profile page
            System.out.println("Product section");
        }
    }

    public void productsButtonPressed(ActionEvent actionEvent) throws Exception {
        //TODO connect to products section
        System.out.println("Product section");
        throw new Exception("WHY?");
    }
}
