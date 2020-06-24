package graphics;

import com.jfoenix.controls.JFXListView;
import controller.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;
import model.SellLog;
import model.Seller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SellLogList {
    private SellerController controller;
    private Seller seller;
    public JFXListView<SellLog> mainList;
    public static Stage secondPopupStage;

    public void show (SellerController controller) throws IOException {
        URL url = new File("src/main/resources/fxml/SellLogList.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ((SellLogList) fxmlLoader.getController()).controller = controller;
        ((SellLogList) fxmlLoader.getController()).seller = (Seller) controller.getUser();
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Sell Logs");
        Main.popupStage.setScene(new Scene(root, 300, 450));
        Main.popupStage.show();
        ((SellLogList) fxmlLoader.getController()).loadLogs();
    }

    private void loadLogs () {
        mainList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        for (SellLog sellLog : seller.getSalesHistory()) {
            mainList.getItems().add(sellLog);
        }
    }


    public void showLog(ActionEvent actionEvent) throws Exception {
        SellLog log = mainList.getSelectionModel().getSelectedItem();
        if (log != null)
            new SingleSellLogPage().show(log);
    }

    public void closeStage(ActionEvent actionEvent) {
        Main.popupStage.close();
    }
}
