package graphics;

import com.jfoenix.controls.JFXButton;
import controller.CustomerController;
import controller.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.Main;
import model.Off;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ManageSalesPage {
    static CustomerController controller;
    static TableView<Off> tableView;
    private static User user;
    public VBox mainPane;


    public static void show(User user) throws IOException {
        URL url = new File("src/main/resources/fxml/ManageSalesPage.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ManageSalesPage page = fxmlLoader.getController();
        setUser(user);
        page.mainPane.getChildren().add(getHeader());

        TableView<Off> table = getOffsTable();
        table.setLayoutY(50);
        table.setPrefHeight(350);

        table.setPrefWidth(600);
        table.setId("productsTable");
        page.mainPane.getChildren().add(table);

        Main.mainStage.setTitle("Manage off codes");
        Main.mainStage.setScene(new Scene(root, 620, 550));
        Main.mainStage.show();
    }

    public static void setUser(User user) {
        ManageSalesPage.user = user;
        //tableView.refresh();
    }

    public static TableView<Off> getOffsTable() {
        tableView = new TableView<>();

        tableView.setItems(getOffs());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn codeCol = new TableColumn("Code");
        TableColumn startCol = new TableColumn("Start");
        TableColumn endCol = new TableColumn("End");
        TableColumn actionCol = new TableColumn("Action");
        //codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("finishTime"));

        Callback<TableColumn<Off, String>, TableCell<Off, String>> offActions =
                new Callback<TableColumn<Off, String>, TableCell<Off, String>>() {
                    @Override
                    public TableCell<Off, String> call(TableColumn<Off, String> productStringTableColumn) {
                        TableCell<Off, String> cell = new TableCell<Off, String>() {
                            @Override
                            protected void updateItem(String s, boolean b) {
                                super.updateItem(s, b);
                                if (b)
                                    return;
                                Group group = new Group();
                                Off item = getTableView().getItems().get(getIndex());

                                JFXButton editButton = getEditButton();
                                editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        try {
                                            AddSalePage.show(item);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                setGraphic(editButton);
                            }
                        };
                        return cell;
                    }
                };
        actionCol.setCellFactory(offActions);

        tableView.getColumns().addAll(codeCol, startCol, endCol, actionCol);

        return tableView;
    }

    private static ObservableList<Off> getOffs() {
        ArrayList<Off> result = new ArrayList<>();
        for (Off off : Database.getAllOffs()) {
            if (off.getSellerId().equals(Main.controller.getUser().getId())) {
                result.add(off);
            }
        }
        return FXCollections.observableArrayList(result);
    }

    public static JFXButton getEditButton() {
        ImageView editImage = new ImageView(new File("src/main/resources/images/edit-icon.png").toURI().toString());
        editImage.setFitHeight(20);
        editImage.setPreserveRatio(true);
        JFXButton editButton = new JFXButton("", editImage);
        editButton.setId("editButton");
        return editButton;
    }

    public static JFXButton getRemoveButton() {
        ImageView removeImage = new ImageView(new File("src/main/resources/images/remove-icon.png").toURI().toString());
        removeImage.setFitHeight(20);
        removeImage.setPreserveRatio(true);
        JFXButton removeButton = new JFXButton("", removeImage);
        removeButton.setId("removeButton");
        return removeButton;
    }

    public static Label getHeader() {
        Label header = new Label("Offs");
        header.setAlignment(Pos.TOP_CENTER);
        header.setLayoutX(250);
        header.setId("header");
        return header;
    }
}
