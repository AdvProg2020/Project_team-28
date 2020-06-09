package graphics;

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
import java.net.MalformedURLException;
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
    private FileChooser fileChooser = new FileChooser();

    public void initialize() {
        if (Main.controller.getUser().getType().equals("seller")) {
            sellerBox.setVisible(true);
            sellerBox.setManaged(true);
        }
        firstName.setText(Main.controller.getPersonalInfo("firstName"));
        surname.setText(Main.controller.getPersonalInfo("surname"));
        companyName.setText(Main.controller.getPersonalInfo("companyName"));
        companyInfo.setText(Main.controller.getPersonalInfo("companyInfo"));
        email.setText(Main.controller.getPersonalInfo("email"));
        phoneNumber.setText(Main.controller.getPersonalInfo("phoneNumber"));
        password.setText(Main.controller.getPersonalInfo("password"));
        username.setText(Main.controller.getPersonalInfo("username"));
        address.setText(Main.controller.getPersonalInfo("address"));
        credit.setText(Main.controller.getPersonalInfo("credit"));

        gender.setValue(gender.getItems().get(gender.getItems().indexOf(Main.controller.getPersonalInfo("gender"))));
        birthDate.setValue(LocalDate.parse(Main.controller.getPersonalInfo("birthDate").replaceAll("/", "-")));
        if (!Main.controller.getPersonalInfo("profilePictureAddress").equals("")) {
            Image image = new Image(Main.controller.getPersonalInfo("profilePictureAddress"));
            profilePicture.imageProperty().setValue(image);
        }
    }

    public void chooseProfilePicture(ActionEvent actionEvent) throws MalformedURLException {
        fileChooser.setTitle("Choose new profile picture...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(Main.popupStage);
        if (selectedFile != null) {
            //TODO maintain a decent way to save image
            Image image = new Image(selectedFile.toURI().toURL().toString());
            Main.controller.changePersonalInfo("profilePictureAddress", selectedFile.toURI().toURL().toString());
            System.out.println(selectedFile.toURI().toURL().toString());
            profilePicture.imageProperty().setValue(image);
        }
    }

    public void propertyChanged(ActionEvent actionEvent) {
        switch (actionEvent.getSource().getClass().getName()) {
            case "com.jfoenix.controls.JFXDatePicker":
                System.out.println(((JFXDatePicker) actionEvent.getSource()).getValue().toString());
                Main.controller.changePersonalInfo("birthDate", ((JFXDatePicker) actionEvent.getSource()).getValue().toString());
                break;
            case "com.jfoenix.controls.JFXComboBox":
                System.out.println(((JFXComboBox) actionEvent.getSource()).getValue().toString());
                Main.controller.changePersonalInfo("gender", (String) ((JFXComboBox) actionEvent.getSource()).getValue());
                break;
            default:
                System.out.println(((TextInputControl) actionEvent.getSource()).getPromptText() + ".setText(Main.controller.getPersonalInfo(\"" + ((TextInputControl) actionEvent.getSource()).getPromptText() + "\");"); // TODO text area and password
                Main.controller.changePersonalInfo(((TextInputControl) actionEvent.getSource()).getPromptText(), ((TextInputControl) actionEvent.getSource()).getText());
        }

    }
}
