package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import main.Main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ProfilePage {

    public ImageView profilePicture;
    public EditableTextField firstName;
    public EditableTextField surName;
    public EditableTextField about;
    public EditableTextField surname;
    public JFXDatePicker birthDate;
    public JFXComboBox<String> gender;
    public VBox sellerBox;
    public EditableTextField companyName;
    public EditableTextArea companyInfo;
    public EditableTextField email;
    public EditableTextField phoneNumber;
    public EditablePasswordField password;
    public EditableTextField username;
    public EditableTextArea address;
    public EditableTextField credit;
    public JFXButton discountButton;
    public JFXButton historyButton;
    private FileChooser fileChooser = new FileChooser();

    public String user = Main.controller.getPersonalInfo("username");


    public void initialize() {
        if (Main.controller.getUser().getType().equals("seller")) {
            sellerBox.setVisible(true);
            sellerBox.setManaged(true);
        }

        if (!Main.controller.getUser().getType().equals("customer")) {
            discountButton.setManaged(false);
            discountButton.setVisible(false);
            historyButton.setManaged(false);
            historyButton.setVisible(false);
        }

        firstName.setText(Main.controller.getPersonalInfo(user, "firstName"));
        surname.setText(Main.controller.getPersonalInfo(user, "surname"));
        companyName.setText(Main.controller.getPersonalInfo(user, "companyName"));
        companyInfo.setText(Main.controller.getPersonalInfo(user, "companyInfo"));
        email.setText(Main.controller.getPersonalInfo(user, "email"));
        phoneNumber.setText(Main.controller.getPersonalInfo(user, "phoneNumber"));
        password.setText(Main.controller.getPersonalInfo(user, "password"));
        username.setText(Main.controller.getPersonalInfo(user, "username"));
        address.setText(Main.controller.getPersonalInfo(user, "address"));
        credit.setText(Main.controller.getPersonalInfo(user, "credit"));

        try {
            gender.setValue(gender.getItems().get(gender.getItems().indexOf(Main.controller.getPersonalInfo(user, "gender"))));
            birthDate.setValue(LocalDate.parse(Main.controller.getPersonalInfo(user, "birthDate").replaceAll("/", "-")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!Main.controller.getPersonalInfo(user, "profilePictureAddress").equals("")) {
            Image image = new Image(Main.controller.getPersonalInfo(user, "profilePictureAddress"));
            profilePicture.imageProperty().setValue(image);
        }
    }

    public void chooseProfilePicture(ActionEvent actionEvent) throws Exception {
        fileChooser.setTitle("Choose new profile picture...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(Main.popupStage);
        if (selectedFile != null) {
            //TODO maintain a decent way to save image
            Image image = new Image(selectedFile.toURI().toURL().toString());
            Main.controller.changePersonalInfo(user, "profilePictureAddress", selectedFile.toURI().toURL().toString());
            System.out.println(selectedFile.toURI().toURL().toString());
            profilePicture.imageProperty().setValue(image);
        }
    }

    public void propertyChanged(ActionEvent actionEvent) throws Exception {
        switch (actionEvent.getSource().getClass().getName()) {
            case "com.jfoenix.controls.JFXDatePicker":
                System.out.println(((JFXDatePicker) actionEvent.getSource()).getValue().toString());
                Main.controller.changePersonalInfo(user, "birthDate", ((JFXDatePicker) actionEvent.getSource()).getValue().toString());
                break;
            case "com.jfoenix.controls.JFXComboBox":
                System.out.println(((JFXComboBox) actionEvent.getSource()).getValue().toString());
                Main.controller.changePersonalInfo(user, "gender", (String) ((JFXComboBox) actionEvent.getSource()).getValue());
                break;
            default:
                System.out.println(((TextInputControl) actionEvent.getSource()).getPromptText() + ".setText(Main.controller.getPersonalInfo(\"" + ((TextInputControl) actionEvent.getSource()).getPromptText() + "\");");
                if (((TextInputControl) actionEvent.getSource()).getPromptText().equals("credit") )
                    makeTransaction(Long.parseLong(((TextInputControl) actionEvent.getSource()).getText()));
                Main.controller.changePersonalInfo(user, ((TextInputControl) actionEvent.getSource()).getPromptText(), ((TextInputControl) actionEvent.getSource()).getText());
        }
    }

    private void makeTransaction(long newValue) throws Exception {
        long oldValue = Main.controller.getUser().getCredit();
        System.out.println("OldVal= " + oldValue);
        System.out.println("newVal= " + newValue);
        if (newValue < oldValue) {
            String result = Main.controller.depositCredit(oldValue - newValue);
            System.out.println(result);
        }else if (newValue > oldValue) {
            String result = Main.controller.withdrawCredit(newValue - oldValue);
            System.out.println(result);
        }
    }

    public void viewDiscountsPressed(ActionEvent actionEvent) throws IOException {
        ManageDiscountsPage.show(Main.controller.getUser());
    }

    public void viewLogsPressed(ActionEvent actionEvent) throws IOException {
        if (Main.controller.getUser().getType().equals("customer")) {
            new PurchaseLogList().show(Main.customerController);
        }
    }
}
