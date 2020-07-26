package graphics;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import controller.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import model.User;

import java.io.File;
import java.util.ArrayList;

public class UserViewer {
    public VBox mainPane;

    JFXTreeTableView<UserRow> treeTableView;

    public void initialize() {
        treeTableView = new JFXTreeTableView<UserRow>();
        TreeItem<UserRow> customers = new TreeItem<>(new UserRow("customers"));
        TreeItem<UserRow> sellers = new TreeItem<>(new UserRow("sellers"));
        TreeItem<UserRow> supporters = new TreeItem<>(new UserRow("supporters"));
        TreeItem<UserRow> managers = new TreeItem<>(new UserRow("managers"));

        for (User user : Main.controller.getAllUsers()) {
            switch (user.getType()) {
                case "customer":
                    customers.getChildren().add(new TreeItem<>(new UserRow(user)));
                    break;
                case "seller":
                    sellers.getChildren().add(new TreeItem<>(new UserRow(user)));
                    break;
                case "supporter":
                    supporters.getChildren().add(new TreeItem<>(new UserRow(user)));
                    break;
                case "manager":
                    managers.getChildren().add(new TreeItem<>(new UserRow(user)));
                    break;
            }
        }
        TreeTableColumn<UserRow, String> columnUsername = new TreeTableColumn<>("Username");
        TreeTableColumn<UserRow, String> columnFirstName = new TreeTableColumn<>("First name");
        TreeTableColumn<UserRow, String> columnSurname = new TreeTableColumn<>("Sur name");
        TreeTableColumn<UserRow, String> columnStatus = new TreeTableColumn<>("Status");

        columnUsername.setCellValueFactory(new TreeItemPropertyValueFactory<>("username"));
        columnFirstName.setCellValueFactory(new TreeItemPropertyValueFactory<>("firstName"));
        columnSurname.setCellValueFactory(new TreeItemPropertyValueFactory<>("surname"));

        ArrayList<String> onlineUsers = Database.getOnlineUsers();

        for (String user : onlineUsers) {
            System.out.println(user);
        }

        columnStatus.setCellValueFactory(dataFeatures -> {
            TreeItem<UserRow> item = dataFeatures.getValue();
            UserRow person = item.getValue();

            if (person.getId() == null)
                return new SimpleStringProperty();

            return new SimpleStringProperty(onlineUsers.contains(person.getId()) ? "ONLINE" : "offline");
        });

        columnUsername.setPrefWidth(125);
        columnFirstName.setPrefWidth(125);
        columnSurname.setPrefWidth(125);
        columnStatus.setPrefWidth(75);

        treeTableView.getColumns().add(columnUsername);
        treeTableView.getColumns().add(columnFirstName);
        treeTableView.getColumns().add(columnSurname);
        treeTableView.getColumns().add(columnStatus);

        TreeItem<UserRow> users = new TreeItem<>(new UserRow("users"));
        users.getChildren().addAll(customers, sellers, supporters, managers);
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

                    private final JFXButton view = new JFXButton("");
                    private final JFXButton remove = new JFXButton("");

                    {
                        ImageView viewImage = new ImageView(new Image(new File("src/main/resources/images/view-icon.png").toURI().toString()));
                        viewImage.setFitWidth(20);
                        viewImage.setPreserveRatio(true);

                        ImageView removeImage = new ImageView(new Image(new File("src/main/resources/images/remove-icon.png").toURI().toString()));
                        removeImage.setFitWidth(20);
                        removeImage.setPreserveRatio(true);

                        view.setGraphic(viewImage);
                        remove.setGraphic(removeImage);

                        view.setOnAction((ActionEvent event) -> {
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
                            setGraphic(new HBox(view, remove));
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

