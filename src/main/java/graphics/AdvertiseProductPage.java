package graphics;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jfoenix.controls.JFXTextField;
import controller.Database;
import javafx.event.ActionEvent;
import main.Main;

import java.util.UUID;

public class AdvertiseProductPage {
    public JFXTextField productId;

    public void removeProductPressed(ActionEvent actionEvent) throws Exception {

        Gson request = new Gson();
        JsonElement jsonElement = new JsonObject();
        jsonElement.getAsJsonObject().addProperty("requestStatus", "pending"); //pending, accepted, rejected
        jsonElement.getAsJsonObject().addProperty("request-type", "advertisement");
        jsonElement.getAsJsonObject().addProperty("owner", Main.controller.getUser().getId());
        jsonElement.getAsJsonObject().addProperty("id", UUID.randomUUID().toString());
        jsonElement.getAsJsonObject().add("product", request.toJsonTree(Database.getProductById(productId.getText())));
        if (Main.controller.getUser().getCredit() >= 10) {
            Main.controller.getUser().addToCredit(-10);
        } else {
            throw new Exception("Not Enough Money");
        }
        System.out.println("Request Sent:\n" + jsonElement);
        Database.add(jsonElement.getAsJsonObject());

        Main.popupStage.close();
    }
}
