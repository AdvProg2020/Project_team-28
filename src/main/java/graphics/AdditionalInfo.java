package graphics;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import main.Main;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;

public class AdditionalInfo {
    public JFXTextField email;
    public JFXComboBox gender;
    public JFXDatePicker birthDate;
    public JFXTextArea address;
    public ImageView profilePicture;
    private FileChooser fileChooser = new FileChooser();


    public void submitButtonPressed(ActionEvent actionEvent) {
        HashMap<String, String> data = new HashMap<>();
        data.put("email", email.getText());
        data.put("gender", gender.getValue().toString());
        data.put("birthDate", birthDate.getValue().toString());
        data.put("address", address.getText());
        Main.controller.changePersonalInfo(data);
        Main.popupStage.close();
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
            Main.controller.changePersonalInfo("profilePictureAddress", selectedFile.toURI().toURL().toString());
            profilePicture.imageProperty().setValue(image);
        }
    }
}
