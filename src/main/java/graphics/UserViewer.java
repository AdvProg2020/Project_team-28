package graphics;

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
import model.User;

import java.io.File;

public class UserViewer {
    public VBox mainPane;

    JFXTreeTableView<UserRow> treeTableView;

    public void initialize() {
        treeTableView = new JFXTreeTableView<UserRow>();
        TreeItem<UserRow> customers = new TreeItem<>(new UserRow("customers"));
        TreeItem<UserRow> sellers = new TreeItem<>(new UserRow("sellers"));
        TreeItem<UserRow> managers = new TreeItem<>(new UserRow("managers"));

        for (User user : Main.controller.getAllUsers()) {
            switch (user.getType()) {
                case "customer":
                    customers.getChildren().add(new TreeItem<>(new UserRow(user)));
                    break;
                case "seller":
                    sellers.getChildren().add(new TreeItem<>(new UserRow(user)));
                    break;
                case "manager":
                    managers.getChildren().add(new TreeItem<>(new UserRow(user)));
                    break;
            }
        }
        TreeTableColumn<UserRow, String> columnUsername = new TreeTableColumn<>("Username");
        TreeTableColumn<UserRow, String> columnFirstName = new TreeTableColumn<>("First name");
        TreeTableColumn<UserRow, String> columnSurname = new TreeTableColumn<>("Sur name");

        columnUsername.setCellValueFactory(new TreeItemPropertyValueFactory<>("username"));
        columnFirstName.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstName"));
        columnSurname.setCellValueFactory(new TreeItemPropertyValueFactory<>("surname"));

        columnUsername.setPrefWidth(125);
        columnFirstName.setPrefWidth(125);
        columnSurname.setPrefWidth(125);

        treeTableView.getColumns().add(columnUsername);
        treeTableView.getColumns().add(columnFirstName);
        treeTableView.getColumns().add(columnSurname);

        TreeItem<UserRow> users = new TreeItem<>(new UserRow("users"));
        users.getChildren().addAll(customers, sellers, managers);
        treeTableView.setRoot(users);

        addButtonToTable();

        mainPane.getChildren().add(treeTableView);
    }

    private void addButtonToTable() {
        TreeTableColumn<UserRow, Void> colBtn = new TreeTableColumn<>("Button Column");

        Callback<TreeTableColumn<UserRow, Void>, TreeTableCell<UserRow, Void>> cellFactory = new Callback<TreeTableColumn<UserRow, Void>, TreeTableCell<UserRow, Void>>() {
            @Override
            public TreeTableCell<UserRow, Void> call(final TreeTableColumn<UserRow, Void> param) {
                final TreeTableCell<UserRow, Void> cell = new TreeTableCell<UserRow, Void>() {

                    private final JFXButton btn = new JFXButton("Action");
                    private final JFXButton remove = new JFXButton("remove");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            UserRow userRow = treeTableView.getTreeItem(getIndex()).getValue();
                            System.out.println("selectedUserRow: " + userRow);

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/Profile.fxml").toURI().toURL());
                                Parent root = fxmlLoader.load();
                                ((ProfilePage) fxmlLoader.getController()).user = userRow.getUsername();
                                ((ProfilePage) fxmlLoader.getController()).initialize();
                                Main.mainStage.setScene(new Scene(root, 620, 450));
                                Main.mainStage.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        remove.setOnAction((ActionEvent event) -> {
                            UserRow userRow = treeTableView.getTreeItem(getIndex()).getValue();
                            Main.controller.removeUser(userRow.getUsername());
                            treeTableView.getTreeItem(getIndex()).valueProperty().set(null);
                            mainPane.getChildren().remove(treeTableView);
                            initialize();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || treeTableView.getTreeItem(getIndex()).getValue().getFirstName() == null) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(btn, remove));
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

