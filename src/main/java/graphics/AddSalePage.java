package graphics;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.Main;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;

public class AddSalePage {
    public JFXDatePicker startDate;
    public JFXTimePicker startTime;
    public JFXDatePicker endDate;
    public JFXTimePicker endTime;
    public JFXTextField percentage;
    public JFXTextField maxAmount;
    public JFXTextField productId;

    public void submitPressed(ActionEvent actionEvent) throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("startTime", LocalDateTime.of(startDate.getValue(), startTime.getValue()).toString());
        hashMap.put("finishTime", LocalDateTime.of(endDate.getValue(), endTime.getValue()).toString());
        hashMap.put("percentage", percentage.getText());
        hashMap.put("maxAmount", maxAmount.getText());
        hashMap.put("productIds", productId.getText());
        Main.sellerController.addOff(hashMap);
        URL url = new File("src/main/resources/fxml/SellerPage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Main.mainStage.setScene(new Scene(root, 620, 450));
    }
}
