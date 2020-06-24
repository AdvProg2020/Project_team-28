package main;

import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import view.Menu;
import view.userstuff.RegisterLoginMenu;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;

public class Main extends Application {
    public static Stage mainStage;
    public static Stage popupStage;
    public static UserController controller;
    public static SellerController sellerController;
    public static ManagerController managerController;
    public static CustomerController customerController;
    public static ProductController productController;
    private static Stack<Scene> sceneStack = new Stack<>();

    public Main() {

    }

    public static void main(String[] args) {
        Database.loadAllData();
        controller = new UserController(null, Menu.productController);
        productController = new ProductController();
        launch(args);
        new RegisterLoginMenu(new UserController(null, Menu.productController));
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        e.printStackTrace();
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
            System.err.println("An unexpected error occurred in " + t);
        }
        else {
            System.err.println("An unexpected error occurred in " + t);
        }
    }

    public static void showErrorDialog(Throwable e) {
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText(null);
        Throwable cause = e;
        while (cause.getCause() != null)
            cause = cause.getCause();
        error.setContentText(cause.getMessage());
        error.show();
//        Popup popup = new Popup();
//        Stage dialog = new Stage();
//        dialog.initModality(Modality.APPLICATION_MODAL);
//        try {
//            URL url = new File("src/main/resources/fxml/Error.fxml").toURI().toURL();
//            FXMLLoader loader = new FXMLLoader(url);
//            Parent root = loader.load();
//            ((ErrorController) loader.getController()).setErrorText(errorMsg.toString());
//            /*popup.getScene().setRoot(root);
//            popup.show(popupStage);*/
//            dialog.setScene(new Scene(root, 250, 400));
//            dialog.show();
//        } catch (IOException exc) {
//            exc.printStackTrace();
//        }
    }

    public static FXMLLoader setMainStage(String title, String fxmlPath) {
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader fxml = new FXMLLoader(url);
            Scene scene = new Scene(fxml.load(), 620, 450);
            sceneStack.push(scene);
            Main.mainStage.setScene(scene);
            mainStage.setTitle(title);
            return fxml;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void returnMainStage() {
        if (sceneStack.size() < 2)
            return;
        sceneStack.pop();
        Main.mainStage.setScene(sceneStack.peek());
    }

    public static void setMainStageSize(int width, int height) {
        mainStage.setWidth(width);
        mainStage.setHeight(height);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(Main::showError);
        mainStage = primaryStage;
        Main.setMainStage("", "src/main/resources/fxml/MainMenu.fxml");
        primaryStage.show();
    }
}
