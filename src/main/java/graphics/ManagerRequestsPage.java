package graphics;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.Main;
import model.Product;

import java.io.File;

public class ManagerRequestsPage {
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

        for (JsonElement json : (Main.managerController).viewRequests()) {
            if (json.getAsJsonObject().get("requestStatus").getAsString().equals("pending")) {
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

        addButtonToTable();

        mainPane.getChildren().add(treeTableView);
    }

    private void addButtonToTable() {
        TreeTableColumn<RequestRow, Void> colBtn = new TreeTableColumn<>("Button Column");

        Callback<TreeTableColumn<RequestRow, Void>, TreeTableCell<RequestRow, Void>> cellFactory = new Callback<TreeTableColumn<RequestRow, Void>, TreeTableCell<RequestRow, Void>>() {
            @Override
            public TreeTableCell<RequestRow, Void> call(final TreeTableColumn<RequestRow, Void> param) {
                final TreeTableCell<RequestRow, Void> cell = new TreeTableCell<RequestRow, Void>() {

                    private final JFXButton accept = new JFXButton("");
                    private final JFXButton reject = new JFXButton("");
                    private final JFXButton view = new JFXButton("");

                    {
                        ImageView acceptImage = new ImageView(new Image(new File("src/main/resources/images/check-icon.png").toURI().toString()));
                        acceptImage.setFitWidth(20);
                        acceptImage.setPreserveRatio(true);
                        ImageView rejectImage = new ImageView(new Image(new File("src/main/resources/images/remove-icon.png").toURI().toString()));
                        rejectImage.setFitWidth(20);
                        rejectImage.setPreserveRatio(true);
                        ImageView viewImage = new ImageView(new Image(new File("src/main/resources/images/view-icon.png").toURI().toString()));
                        viewImage.setFitWidth(20);
                        viewImage.setPreserveRatio(true);

                        accept.setGraphic(acceptImage);
                        reject.setGraphic(rejectImage);
                        view.setGraphic(viewImage);

                        accept.setOnAction((ActionEvent event) -> {
                            RequestRow requestRow = treeTableView.getTreeItem(getIndex()).getValue();
                            try {
                                (Main.managerController).evaluateRequest(requestRow.jsonElement.getAsJsonObject().get("id").getAsString(), true);
                                mainPane.getChildren().remove(treeTableView);
                                initialize();
                            } catch (Exception e) {
                                Main.showErrorDialog(e);
                            }
                        });
                        reject.setOnAction((ActionEvent event) -> {
                            RequestRow requestRow = treeTableView.getTreeItem(getIndex()).getValue();
                            try {
                                (Main.managerController).evaluateRequest(requestRow.jsonElement.getAsJsonObject().get("id").getAsString(), false);
                                mainPane.getChildren().remove(treeTableView);
                                initialize();
                            } catch (Exception e) {
                                Main.showErrorDialog(e);
                            }
                        });
                        view.setOnAction((ActionEvent actionEvent) -> {
                            RequestRow requestRow = treeTableView.getTreeItem(getIndex()).getValue();
                            switch (requestRow.jsonElement.getAsJsonObject().get("request-type").getAsString()) {
                                case ("edit off"):
                                case ("add off"):
                                    try {
                                        FXMLLoader fxmlLoader = Main.setMainStage("Sale Request", "src/main/resources/fxml/SaleRequestPage.fxml");
                                        ((SaleRequestPage) fxmlLoader.getController()).setJsonElement(requestRow.jsonElement);
                                    } catch (Exception e) {
                                        Main.showErrorDialog(e);
                                    }
                                    break;
                                case "add product":
                                case "edit product":
                                case "remove product":
                                    try {
                                        AddProductPage controller = new AddProductPage().show(Main.sellerController);
                                        controller.setLoadedProduct(new Gson().fromJson(requestRow.jsonElement.getAsJsonObject().get("product").getAsJsonObject(), Product.class));
                                        controller.disableNodes(null);
                                    } catch (Exception e) {
                                        Main.showErrorDialog(e);
                                    }
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || treeTableView.getTreeItem(getIndex()).getValue().getOwner() == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(accept, reject, view));
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        colBtn.setPrefWidth(250);

        treeTableView.getColumns().add(colBtn);
    }
}