package graphics;

import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import main.Main;

import java.time.LocalDateTime;

public class SaleRequestPage {
    public JFXDatePicker startDate;
    public JFXTimePicker startTime;
    public JFXDatePicker endDate;
    public JFXTimePicker endTime;
    public JFXTextField percentage;
    public JFXTextField maxAmount;
    public JFXTextField productId;

    public JsonElement jsonElement;

    public void setJsonElement(JsonElement jsonElement) {
        this.jsonElement = jsonElement;
        startDate.setValue(LocalDateTime.parse(jsonElement.getAsJsonObject().get("startTime").getAsString()).toLocalDate());
        startTime.setValue(LocalDateTime.parse(jsonElement.getAsJsonObject().get("startTime").getAsString()).toLocalTime());
        endDate.setValue(LocalDateTime.parse(jsonElement.getAsJsonObject().get("finishTime").getAsString()).toLocalDate());
        endTime.setValue(LocalDateTime.parse(jsonElement.getAsJsonObject().get("finishTime").getAsString()).toLocalTime());
        percentage.setText(jsonElement.getAsJsonObject().get("percentage").getAsString());
        maxAmount.setText(jsonElement.getAsJsonObject().get("maxAmount").getAsString());
        productId.setText(jsonElement.getAsJsonObject().get("product ids").getAsString());
    }

    public void acceptPressed(ActionEvent actionEvent) throws Exception {
        (Main.managerController).evaluateRequest(jsonElement.getAsJsonObject().get("id").getAsString(), true);
        Main.setMainStage("Manage Requests",
                "src/main/resources/fxml/ManagerRequestsPage.fxml");
    }

    public void rejectPressed(ActionEvent actionEvent) throws Exception {
        (Main.managerController).evaluateRequest(jsonElement.getAsJsonObject().get("id").getAsString(), false);
        Main.setMainStage("Manage Requests",
                "src/main/resources/fxml/ManagerRequestsPage.fxml");
    }
}
