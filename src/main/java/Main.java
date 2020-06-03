import controller.Database;
import controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.Menu;
import view.userstuff.RegisterLoginMenu;

import java.io.File;
import java.net.URL;

public class Main extends Application {
    public static Stage mainStage;

    public Main() {

    }

    public static void main(String[] args) {
        Database.loadAllData();
        launch(args);
        new RegisterLoginMenu(new UserController(null, Menu.productController));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        URL url = new File("src/main/resources/MainMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(new Scene(root, 600, 550));
        primaryStage.show();
    }
}