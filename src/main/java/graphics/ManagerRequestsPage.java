package graphics;

import com.google.gson.JsonElement;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.Main;

import java.io.File;

public class ManagerRequestsPage {
    public VBox mainPane;

    JFXTreeTableView<RequestRow> treeTableView;

    public void initialize() {
        treeTableView = new JFXTreeTableView<RequestRow>();
        TreeItem<RequestRow> addOff = new TreeItem<>(new RequestRow("add off"));
        TreeItem<RequestRow> editOff = new TreeItem<>(new RequestRow("edit off"));

        for (JsonElement json : (Main.managerController).viewRequests()) {
            switch (json.getAsJsonObject().get("request-type").getAsString()) {
                case "add off":
                    addOff.getChildren().add(new TreeItem<>(new RequestRow(json)));
                    break;
                case "edit off":
                    editOff.getChildren().add(new TreeItem<>(new RequestRow(json)));
                    break;
            }
        }
        TreeTableColumn<RequestRow, String> columnTitle = new TreeTableColumn<>("Title");
        TreeTableColumn<RequestRow, String> columnOwner = new TreeTableColumn<>("Owner");

        columnTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        columnOwner.setCellValueFactory(new TreeItemPropertyValueFactory<>("owner"));

        columnTitle.setPrefWidth(125);
        columnOwner.setPrefWidth(125);

        treeTableView.getColumns().add(columnTitle);
        treeTableView.getColumns().add(columnOwner);

        TreeItem<RequestRow> requests = new TreeItem<>(new RequestRow("requests"));
        requests.getChildren().addAll(addOff, editOff);
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

                    private final JFXButton accept = new JFXButton("accept");
                    private final JFXButton reject = new JFXButton("reject");
                    private final JFXButton view = new JFXButton("view");

                    {
                        accept.setOnAction((ActionEvent event) -> {
                            RequestRow requestRow = treeTableView.getTreeItem(getIndex()).getValue();
                            try {
                                (Main.managerController).evaluateRequest(requestRow.jsonElement.getAsJsonObject().get("id").getAsString(), true);
                            } catch (Exception e) {
                                Main.showErrorDialog(e);
                            }
                        });
                        reject.setOnAction((ActionEvent event) -> {
                            RequestRow requestRow = treeTableView.getTreeItem(getIndex()).getValue();
                            try {
                                (Main.managerController).evaluateRequest(requestRow.jsonElement.getAsJsonObject().get("id").getAsString(), false);
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
                                        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/SaleRequestPage.fxml").toURI().toURL());
                                        Parent root = fxmlLoader.load();
                                        ((SaleRequestPage) fxmlLoader.getController()).setJsonElement(requestRow.jsonElement);
                                        Main.mainStage.setScene(new Scene(root, 620, 450));
                                        Main.mainStage.show();
                                    } catch (Exception e) {
                                        Main.showErrorDialog(e);
                                    }
                                    break;
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

        treeTableView.getColumns().add(colBtn);
    }
}