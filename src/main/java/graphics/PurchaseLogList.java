package graphics;

import com.jfoenix.controls.JFXListView;
import controller.CustomerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import main.Main;
import model.Customer;
import model.PurchaseLog;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PurchaseLogList {
    private CustomerController controller;
    private Customer customer;
    public JFXListView<PurchaseLog> mainList;
    public static Stage secondPopupStage;

    public void show (CustomerController controller) throws IOException {
        URL url = new File("src/main/resources/fxml/PurchaseLogList.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ((PurchaseLogList) fxmlLoader.getController()).controller = controller;
        ((PurchaseLogList) fxmlLoader.getController()).customer = (Customer) controller.getUser();
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Purchase Logs");
        Main.popupStage.setScene(new Scene(root, 300, 450));
        Main.popupStage.show();
        ((PurchaseLogList) fxmlLoader.getController()).loadLogs();
    }

    private void loadLogs () {
        mainList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        for (PurchaseLog log : customer.getPurchaseHistory()) {
            mainList.getItems().add(log);
        }
    }


    public void showLog(ActionEvent actionEvent) throws Exception {
        PurchaseLog log = mainList.getSelectionModel().getSelectedItem();
        if (log != null)
            new SinglePurchaseLogPage().show(log);
    }

    public void closeStage(ActionEvent actionEvent) {
        Main.popupStage.close();
    }
}
