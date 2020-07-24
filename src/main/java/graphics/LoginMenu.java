package graphics;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.CustomerController;
import controller.FileSender;
import controller.ManagerController;
import controller.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.Main;
import model.Customer;
import model.Manager;
import model.Seller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoginMenu {
    public JFXTextField username;
    public JFXPasswordField password;

    public void SignInPressed(ActionEvent actionEvent) throws Exception {
        Main.controller.loginUser(username.getText(), password.getText());
        if (Main.controller.getUser().getClass() == Customer.class) {
            Main.customerController = new CustomerController((Customer)Main.controller.getUser(), Main.productController);
        }
        if (Main.controller.getUser().getClass() == Seller.class) {
            Main.sellerController = new SellerController(Main.controller.getUser(), Main.productController);
            new FileSender().start();
        }
        if (Main.controller.getUser().getClass() == Manager.class) {
            Main.managerController = new ManagerController(Main.controller.getUser(), Main.productController);
        }
        Main.popupStage.close();
        System.out.println(Main.sellerController);
        Main.setMainStage("Main Menu", "src/main/resources/fxml/MainMenu.fxml");
    }

    public void createAccountPressed(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/resources/fxml/RegisterMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage.setScene(new Scene(root, 300, 400));
    }
}
