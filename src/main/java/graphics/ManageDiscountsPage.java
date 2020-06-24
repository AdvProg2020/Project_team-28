package graphics;

import com.jfoenix.controls.JFXButton;
import controller.CustomerController;
import controller.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import model.Discount;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ManageDiscountsPage {
    static CustomerController controller;
    static TableView<Discount> tableView;
    private static User user;
    public VBox mainPane;
    public JFXButton addButton;


    public static void show(User user) throws IOException {
        URL url = new File("src/main/resources/fxml/ManageDiscountsPage.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ManageDiscountsPage page = fxmlLoader.getController();
        setUser(user);
        if (user != null) {
            page.addButton.setManaged(false);
            page.addButton.setVisible(false);
        }
        page.mainPane.getChildren().add(getHeader());

        TableView<Discount> table = getDiscountsTable();
        table.setLayoutY(50);
        table.setPrefHeight(350);

        table.setPrefWidth(600);
        table.setId("productsTable");
        page.mainPane.getChildren().add(table);

        Main.mainStage.setTitle("Manage discount codes");
        Main.mainStage.setScene(new Scene(root, 620, 550));
        Main.mainStage.show();
    }

    public static void setUser(User user) {
        ManageDiscountsPage.user = user;
        //tableView.refresh();
    }

    public static TableView<Discount> getDiscountsTable() {
        tableView = new TableView<>();

        tableView.setItems(getDiscounts());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn codeCol = new TableColumn("Code");
        TableColumn startCol = new TableColumn("Start");
        TableColumn endCol = new TableColumn("End");
        TableColumn actionCol = new TableColumn("Action");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("finishTime"));

        Callback<TableColumn<Discount, String>, TableCell<Discount, String>> discountActions =
                new Callback<TableColumn<Discount, String>, TableCell<Discount, String>>() {
                    @Override
                    public TableCell<Discount, String> call(TableColumn<Discount, String> productStringTableColumn) {
                        TableCell<Discount, String> cell = new TableCell<Discount, String>() {
                            @Override
                            protected void updateItem(String s, boolean b) {
                                super.updateItem(s, b);
                                if (b)
                                    return;
                                Group group = new Group();
                                Discount item = getTableView().getItems().get(getIndex());

                                JFXButton editButton = getEditButton();
                                editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        try {
                                            AddDiscountCodePage.show(Main.managerController).loadDiscountFields(item, false);
                                            tableView.refresh();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                JFXButton viewButton = getViewButton();
                                viewButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent event) {
                                        try {
                                            AddDiscountCodePage.show(Main.managerController).loadDiscountFields(item, true);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        tableView.refresh();
                                    }
                                });
                                viewButton.setLayoutX(0);
                                editButton.setLayoutX(40);
                                if (user == null) {
                                    group.getChildren().addAll(viewButton, editButton);
                                } else {
                                    group.getChildren().addAll(viewButton);
                                }
                                if (b) {
                                    setText(null);
                                    setGraphic(null);
                                } else {
                                    setGraphic(group);
                                }
                            }
                        };
                        return cell;
                    }
                };
        actionCol.setCellFactory(discountActions);

        tableView.getColumns().addAll(codeCol, startCol, endCol, actionCol);

        return tableView;
    }

    public static JFXButton getEditButton() {
        ImageView editImage = new ImageView(new File("src/main/resources/images/edit-icon.png").toURI().toString());
        editImage.setFitHeight(20);
        editImage.setPreserveRatio(true);
        JFXButton editButton = new JFXButton("", editImage);
        editButton.setId("editButton");
        return editButton;
    }

    public static JFXButton getViewButton() {
        ImageView viewImage = new ImageView(new File("src/main/resources/images/view-icon.png").toURI().toString());
        viewImage.setFitHeight(20);
        viewImage.setPreserveRatio(true);
        JFXButton viewButton = new JFXButton("", viewImage);
        viewButton.setId("viewButton");
        return viewButton;
    }

    public static ObservableList<Discount> getDiscounts() {
        try {
            ArrayList<Discount> discounts = Database.getAllDiscountCodes();

            if (user != null) {
                ArrayList<Discount> userDiscounts = new ArrayList<>();
                for (Discount discount : discounts) {
                    if (discount.getUsers().contains(user)) {
                        userDiscounts.add(discount);
                    }
                }
                return FXCollections.observableArrayList(userDiscounts);
            }
            return FXCollections.observableArrayList(discounts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Label getHeader() {
        Label header = new Label("Discounts");
        header.setAlignment(Pos.TOP_CENTER);
        header.setLayoutX(250);
        header.setId("header");
        return header;
    }

    public void newDiscountPressed(ActionEvent actionEvent) throws IOException {
        AddDiscountCodePage.show(Main.managerController);
    }

    public void refreshPressed(ActionEvent actionEvent) {
        tableView.refresh();
    }
}
