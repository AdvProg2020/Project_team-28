package graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Main;
import model.SellLog;
import model.Seller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SingleSellLogPage {
    public Label date;
    public Label received;
    public Label reduced;
    public Label product;
    public Label customer;
    public Label status;
    private SellLog log;

    public void show (SellLog log) throws Exception {
        URL url = new File("src/main/resources/fxml/SingleSellLogPage.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ((SingleSellLogPage) fxmlLoader.getController()).log = log;
        SellLogList.secondPopupStage = new Stage();
        SellLogList.secondPopupStage.setTitle("Log " + log.getDate().toLocalDate().toString());
        SellLogList.secondPopupStage.setScene(new Scene(root, 300, 200));
        SellLogList.secondPopupStage.show();
        ((SingleSellLogPage) fxmlLoader.getController()).loadLog();
    }

    public void loadLog () {
        date.setText(log.getDate().toLocalDate().toString());
        received.setText(String.valueOf(log.getAmountReceived()));
        reduced.setText(String.valueOf(log.getAmountReduced()));
        try {
            product.setText(log.getSoldProduct().toString());
        } catch (Exception ignored) {
        }
        customer.setText(log.getCustomer().getUsername());
        status.setText(log.getStatus());
    }

    public void closeStage(ActionEvent actionEvent) {
        SellLogList.secondPopupStage.close();
    }
}
