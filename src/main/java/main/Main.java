package main;

import controller.Database;
import controller.UserController;
import graphics.ErrorController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.Menu;
import view.userstuff.RegisterLoginMenu;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

public class Main extends Application {
    public static Stage mainStage;
    public static Stage popupStage;
    public static UserController controller;

    public Main() {

    }

    public static void main(String[] args) {
        Database.loadAllData();
        controller = new UserController(null, Menu.productController);
        launch(args);
        new RegisterLoginMenu(new UserController(null, Menu.productController));
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in " + t);
        }
    }

    public static void showErrorDialog(Throwable e) {
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        try {
            URL url = new File("src/main/resources/Error.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            ((ErrorController) loader.getController()).setErrorText(errorMsg.toString());
            dialog.setScene(new Scene(root, 250, 400));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(Main::showError);

        mainStage = primaryStage;
        URL url = new File("src/main/resources/fxml/MainMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(new Scene(root, 620, 450));
        primaryStage.show();
    }
}
