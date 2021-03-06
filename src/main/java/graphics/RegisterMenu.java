package graphics;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.ManagerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import main.Main;

import java.io.File;
import java.net.URL;
import java.util.HashMap;

public class RegisterMenu {
    public JFXComboBox<String> accountType;
    public JFXTextField firstName;
    public JFXTextField lastName;
    public JFXTextField phoneNumber;
    public JFXTextField username;
    public JFXPasswordField password;
    public VBox mainBox;
    public VBox sellerBox;
    public VBox managerBox;
    public JFXTextField companyName;
    public JFXTextArea companyInfo;


    public void registerPressed() throws Exception {
        registerAccount();
        URL url = new File("src/main/resources/fxml/LoginMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage.setScene(new Scene(root, 250, 250));
        Main.popupStage.show();
    }

    private void registerAccount() throws Exception {
        String errorText = "Please provide following fields: \n";

        if (accountType.getValue() == null) {
            errorText += accountType.promptTextProperty().getValue() + "\n";
        }
        if (firstName.getText().equals("")) {
            errorText += firstName.promptTextProperty().getValue() + "\n";
        }
        if (lastName.getText().equals("")) {
            errorText += lastName.promptTextProperty().getValue() + "\n";
        }
        if (phoneNumber.getText().equals("")) {
            errorText += phoneNumber.promptTextProperty().getValue() + "\n";
        }
        if (username.getText().equals("")) {
            errorText += username.promptTextProperty().getValue() + "\n";
        }
        if (password.getText().equals("")) {
            errorText += password.promptTextProperty().getValue() + "\n";
        }
        if (!errorText.equals("Please provide following fields: \n")) {
            throw (new Exception(errorText));
        }
        HashMap<String, String> data = new HashMap<>();
        data.put("type", accountType.getValue());
        data.put("name", firstName.getText());
        data.put("surname", lastName.getText());
        data.put("phoneNumber", phoneNumber.getText());
        data.put("username", username.getText());
        data.put("password", password.getText());
        data.put("companyName", companyName.getText());
        data.put("companyInfo", companyInfo.getText());
        data.put("credit", "0");

        if (!ManagerController.managerExists() && !accountType.getValue().equals("Manager")) {
            throw (new Exception("Please register a manager first"));
        }
        if (Main.controller.canBeManager(username.getText())) {
            Main.controller.registerAccount(data, true);
            return;
        }
        Main.controller.registerAccount(data, false);
    }

    public void additionalInfoPressed() throws Exception {
        registerAccount();
        URL url = new File("src/main/resources/fxml/AdditionalInfo.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.popupStage.setScene(new Scene(root, 300, 400));
    }

    public void accountTypeChanged() {
        System.out.println("called");
        System.out.println();
        if (accountType.getValue().equals("Seller")) {
            sellerBox.setManaged(true);
            sellerBox.setVisible(true);
            managerBox.setManaged(false);
            managerBox.setVisible(false);
        }
        if (accountType.getValue().equals("Manager")) {
            sellerBox.setManaged(false);
            sellerBox.setVisible(false);
            managerBox.setManaged(true);
            managerBox.setVisible(true);
        }
        if (accountType.getValue().equals("Supporter") || accountType.getValue().equals("Customer")) {
            sellerBox.setManaged(false);
            sellerBox.setVisible(false);
            managerBox.setManaged(false);
            managerBox.setVisible(false);
        }
    }
}
