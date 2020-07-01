package main;

import controller.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Customer;
import model.Discount;
import model.Seller;
import view.Menu;
import view.userstuff.RegisterLoginMenu;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;
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
        customerController = new CustomerController((Customer) controller.getUser(), Menu.productController);
        productController = new ProductController();

        makeRandomDiscounts();

        launch(args);
        new RegisterLoginMenu(new UserController(null, Menu.productController));
    }

    private static void makeRandomDiscounts() {
        Timeline workStuff = new Timeline();
        workStuff.setCycleCount(Timeline.INDEFINITE);
        Random random = new Random();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(5),                // 1 FPS
                ae -> {
                    if (random.nextDouble() < 0.1) {
                        int userIndex = random.nextInt(Database.getAllUsers().size());
                        Discount discount = new Discount();
                        discount.setCode(Discount.generateRandomCode());
                        discount.setDiscountPercent(random.nextInt(50) + 1);
                        discount.setStartTime(LocalDateTime.now());
                        discount.setFinishTime(LocalDateTime.now().plusDays(1));
                        discount.setMaximumAmount(random.nextInt(11) + 10);
                        discount.setRepetitionNumber(1);
                        discount.addUser(Database.getAllUsers().get(userIndex));
                        System.out.println(Database.getAllUsers().get(userIndex).getUsername());
                        Database.add(discount);
                    }
                });

        workStuff.getKeyFrames().add(kf);
        workStuff.play();
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        e.printStackTrace();
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
            System.err.println("An unexpected error occurred in " + t);
        } else {
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
            return null;
        }
    }

    public static void returnMainStage() {
        if (sceneStack.size() < 2)
            return;
        sceneStack.pop();
        Main.mainStage.setScene(sceneStack.peek());
    }

    public static void clearStack() {
        sceneStack.clear();
    }

    public static void setMainStageSize(int width, int height) {
        mainStage.setWidth(width);
        mainStage.setHeight(height);
    }

    public static FXMLLoader setPopupStage(String title, String fxmlPath) {
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();
            Main.popupStage = new Stage();
            Main.popupStage.setTitle(title);
            Main.popupStage.setScene(new Scene(root, 250, 350));
            Main.popupStage.initModality(Modality.WINDOW_MODAL);
            Main.popupStage.initOwner(Main.mainStage);
            Main.popupStage.show();
            return fxmlLoader;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setPopupStageSize(double width, double height) {
        popupStage.setWidth(width);
        popupStage.setHeight(height);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(Main::showError);
//        Database.addProductToAds(Database.getProductById("111388d0-ac14-4a24-b54f-fda183df6c2d"));
//        Database.addProductToAds(Database.getProductById("efe8e6bf-4b3c-4699-b3cf-16c6fd89c483"));
        mainStage = primaryStage;
        Main.setMainStage("", "src/main/resources/fxml/MainMenu.fxml");
        primaryStage.show();
    }
}
