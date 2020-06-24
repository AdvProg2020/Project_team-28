package graphics;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.PurchaseLog;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SinglePurchaseLogPage {
    public Label date;
    public Label paid;
    public Label discount;
    public Label product;
    public Label status;
    public static Stage billPopup;
    private PurchaseLog log;

    public void show (PurchaseLog log) throws Exception {
        URL url = new File("src/main/resources/fxml/SinglePurchaseLogPage.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ((SinglePurchaseLogPage) fxmlLoader.getController()).log = log;
        PurchaseLogList.secondPopupStage = new Stage();
        PurchaseLogList.secondPopupStage.setTitle("Log " + log.getDate().toLocalDate().toString());
        PurchaseLogList.secondPopupStage.setScene(new Scene(root, 300, 200));
        PurchaseLogList.secondPopupStage.show();
        ((SinglePurchaseLogPage) fxmlLoader.getController()).loadLog();
    }

    public void loadLog () {
        date.setText(log.getDate().toLocalDate().toString());
        paid.setText(String.valueOf(log.getAmountPaid()));
        if (log.getDiscount() != null)
            discount.setText(String.valueOf(log.getDiscount().getDiscountPercent()));
        status.setText(log.getStatus());
    }

    public void closeStage(ActionEvent actionEvent) {
        PurchaseLogList.secondPopupStage.close();
    }

    public void openBill(ActionEvent actionEvent) throws IOException {
        new Bill().show(log.getProducts());
    }
}
