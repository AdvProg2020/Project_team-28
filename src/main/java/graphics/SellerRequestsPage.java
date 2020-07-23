package graphics;

import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;
import main.Main;
import model.Request;

public class SellerRequestsPage {
    public VBox mainPane;

    JFXTreeTableView<RequestRow> treeTableView;

    public void initialize() {
        treeTableView = new JFXTreeTableView<RequestRow>();
        TreeItem<RequestRow> addOff = new TreeItem<>(new RequestRow("add off"));
        TreeItem<RequestRow> editOff = new TreeItem<>(new RequestRow("edit off"));
        TreeItem<RequestRow> addProduct = new TreeItem<>(new RequestRow("add product"));
        TreeItem<RequestRow> editProduct = new TreeItem<>(new RequestRow("edit product"));
        TreeItem<RequestRow> removeProduct = new TreeItem<>(new RequestRow("remove product"));
        TreeItem<RequestRow> ads = new TreeItem<>(new RequestRow("advertisement"));

        for (Request request : Main.sellerController.viewRequests()) {
            JsonElement json = request.getJsonObject();
            if (!json.getAsJsonObject().get("owner").getAsString().equals(Main.controller.getUser().getId())) {
                continue;
            }
            switch (json.getAsJsonObject().get("request-type").getAsString()) {
                case "add off":
                    addOff.getChildren().add(new TreeItem<>(new RequestRow(json)));
                    break;
                case "edit off":
                    editOff.getChildren().add(new TreeItem<>(new RequestRow(json)));
                    break;
                case "add product":
                    addProduct.getChildren().add(new TreeItem<>(new RequestRow(json)));
                    break;
                case "edit product":
                    editProduct.getChildren().add(new TreeItem<>(new RequestRow(json)));
                    break;
                case "remove product":
                    removeProduct.getChildren().add(new TreeItem<>(new RequestRow(json)));
                    break;
                case "advertisement":
                    ads.getChildren().add(new TreeItem<>(new RequestRow(json)));
                    break;
            }
        }
        TreeTableColumn<RequestRow, String> columnTitle = new TreeTableColumn<>("Title");
        TreeTableColumn<RequestRow, String> columnOwner = new TreeTableColumn<>("Owner");
        TreeTableColumn<RequestRow, String> columnStatus = new TreeTableColumn<>("Status");

        columnTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        columnOwner.setCellValueFactory(new TreeItemPropertyValueFactory<>("owner"));
        columnStatus.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));

        columnTitle.setPrefWidth(125);
        columnOwner.setPrefWidth(125);
        columnStatus.setPrefWidth(125);

        treeTableView.getColumns().add(columnTitle);
        treeTableView.getColumns().add(columnOwner);
        treeTableView.getColumns().add(columnStatus);

        TreeItem<RequestRow> requests = new TreeItem<>(new RequestRow("requests"));
        requests.getChildren().addAll(addOff, editOff, addProduct, editProduct, removeProduct, ads);
        treeTableView.setRoot(requests);


        mainPane.getChildren().add(treeTableView);
    }
}