package graphics;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main;
import model.Off;

import java.io.File;
import java.io.IOException;
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
    private Off off;

    public static AddSalePage show(Off off) throws IOException {
        URL url = new File("src/main/resources/fxml/AddSalePage.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ((AddSalePage) fxmlLoader.getController()).setOff(off);
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Sale Page");
        Main.popupStage.setScene(new Scene(root, 620, 450));
        Main.popupStage.show();
        return ((AddSalePage) fxmlLoader.getController());
    }

    public void setOff(Off off) {
        this.off = off;
        startDate.setValue(off.getStartTime().toLocalDate());
        startTime.setValue(off.getStartTime().toLocalTime());
        endDate.setValue(off.getFinishTime().toLocalDate());
        endTime.setValue(off.getFinishTime().toLocalTime());
        percentage.setText(((Integer) off.getPercentage()).toString());
        maxAmount.setText(((Integer) off.getMaxAmount()).toString());
        StringBuilder productIds = new StringBuilder();
        for (String product : off.getProducts()) {
            productIds.append(product);
        }
        productId.setText(productIds.toString());
    }

    public void submitPressed(ActionEvent actionEvent) throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("startTime", LocalDateTime.of(startDate.getValue(), startTime.getValue()).toString());
        hashMap.put("finishTime", LocalDateTime.of(endDate.getValue(), endTime.getValue()).toString());
        hashMap.put("percentage", percentage.getText());
        hashMap.put("maxAmount", maxAmount.getText());
        hashMap.put("productIds", productId.getText());
        if (off == null) {
            Main.sellerController.addOff(hashMap);
        } else {
            hashMap.put("offId", off.getId());
            Main.sellerController.editOff(hashMap);
        }
        Main.setMainStage("Seller Page", "src/main/resources/fxml/SellerPage.fxml");
    }
}
