package graphics;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import model.Customer;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Bill {
    public VBox mainPane;
    private HashMap<Product, Integer> cart;
    private JFXTreeTableView<BillRow> treeTableView;

    public void show (HashMap<Product, Integer> cart) throws IOException {
        URL url = new File("src/main/resources/fxml/Bill.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ((Bill) fxmlLoader.getController()).cart = cart;
        SinglePurchaseLogPage.billPopup = new Stage();
        SinglePurchaseLogPage.billPopup.setTitle("Your Bill");
        SinglePurchaseLogPage.billPopup.setScene(new Scene(root, 300, 450));
        SinglePurchaseLogPage.billPopup.show();
        ((Bill) fxmlLoader.getController()).loadTable();
    }

    private void loadTable() {
        treeTableView = new JFXTreeTableView<>();
        TreeItem<BillRow> allProducts = new TreeItem<>(new BillRow("All products"));
        for (Product product : cart.keySet()) {
            allProducts.getChildren().add(new TreeItem<>(new BillRow(product, cart.get(product))));
        }

        TreeTableColumn<BillRow, String> columnName = new TreeTableColumn<>("Product Name");
        TreeTableColumn<BillRow, String> columnPrice = new TreeTableColumn<>("Unit Price");
        TreeTableColumn<BillRow, String> columnQuantity = new TreeTableColumn<>("Quantity");
        TreeTableColumn<BillRow, String> columnTotalPrice = new TreeTableColumn<>("Total Price");

        columnName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        columnPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        columnQuantity.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        columnTotalPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("totalPrice"));

        treeTableView.getColumns().add(columnName);
        treeTableView.getColumns().add(columnPrice);
        treeTableView.getColumns().add(columnQuantity);
        treeTableView.getColumns().add(columnTotalPrice);
        treeTableView.setRoot(allProducts);
        mainPane.getChildren().add(treeTableView);
    }
}
