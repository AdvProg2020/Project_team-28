package graphics;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import controller.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.Main;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

public class EditSalePage {
    public JFXDatePicker startDate;
    public JFXTimePicker startTime;
    public JFXDatePicker endDate;
    public JFXTimePicker endTime;
    public JFXTextField percentage;
    public JFXTextField maxAmount;
    public JFXTextField productId;

    public String offId;

    public void setOffId(String offId) throws NoSuchFieldException, IllegalAccessException {
        this.offId = offId;
        startDate.setValue(LocalDate.parse(((SellerController) Main.controller).getOffItem(offId, "startDate")));
        startTime.setValue(LocalTime.parse(((SellerController) Main.controller).getOffItem(offId, "startTime")));
        endDate.setValue(LocalDate.parse(((SellerController) Main.controller).getOffItem(offId, "endDate")));
        endTime.setValue(LocalTime.parse(((SellerController) Main.controller).getOffItem(offId, "endTime")));
        percentage.setText(((SellerController) Main.controller).getOffItem(offId, "percentage"));
        maxAmount.setText(((SellerController) Main.controller).getOffItem(offId, "maxAmount"));
        productId.setText(((SellerController) Main.controller).getOffItem(offId, "productIds"));
    }

    public void submitPressed(ActionEvent actionEvent) throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("startTime", LocalDateTime.of(startDate.getValue(), startTime.getValue()).toString());
        hashMap.put("finishTime", LocalDateTime.of(endDate.getValue(), endTime.getValue()).toString());
        hashMap.put("percentage", percentage.getText());
        hashMap.put("maxAmount", maxAmount.getText());
        hashMap.put("productIds", productId.getText());
        hashMap.put("offId", offId);
        Main.sellerController.editOff(hashMap);
        Main.setMainStage("Seller Page", "src/main/resources/fxml/SellerPage.fxml");
    }
}
