package graphics;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoginMenu {
    public JFXTextField username;
    public JFXPasswordField password;

    public void SignInPressed(ActionEvent actionEvent) throws Exception {
        Main.controller.loginUser(username.getText(), password.getText());
        Main.popupStage.close();
    }

    public void createAccountPressed(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/resources/fxml/RegisterMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage.setScene(new Scene(root, 300, 400));
    }
}
