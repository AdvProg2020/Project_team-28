package graphics;

import com.jfoenix.controls.*;
import controller.CustomerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Product;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class Cart {
    private CustomerController controller;
    public VBox mainPane;
    private Stage cartStage;
    private HashMap<Product, Integer> customerCart;
    public JFXTreeTableView<CartRow> tableView;

    public void show(CustomerController controller) throws IOException {
        URL url = new File("src/main/resources/fxml/Cart.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        try {
            ((Cart) fxmlLoader.getController()).customerCart = controller.getCustomerLoggedOn().getCart();
        } catch (Exception ignored) {
        }

        ((Cart) fxmlLoader.getController()).controller = controller;
        ((Cart) fxmlLoader.getController()).cartStage = new Stage();
        root.getStylesheets().add(Paths.get("src/main/resources/stylesheets/CartStyleSheet.css").toUri().toString());
        ((Cart) fxmlLoader.getController()).cartStage.setTitle("Cart");
        ((Cart) fxmlLoader.getController()).cartStage.setScene(new Scene(root, 600, 400));
        ((Cart) fxmlLoader.getController()).cartStage.show();
        ((Cart) fxmlLoader.getController()).loadCartProducts();
    }

    private void loadCartProducts() {
        TreeItem<CartRow> allProducts = new TreeItem<>(new CartRow("All products"));
        for (Product product : customerCart.keySet()) {
            allProducts.getChildren().add(new TreeItem<>(new CartRow(product, customerCart.get(product))));
        }
        TreeTableColumn<CartRow, String> columnName = new TreeTableColumn<>("Product Name");
        TreeTableColumn<CartRow, String> columnPrice = new TreeTableColumn<>("Unit Price");
        TreeTableColumn<CartRow, Void> columnQuantity = new TreeTableColumn<>("Quantity");
        TreeTableColumn<CartRow, String> columnTotalPrice = new TreeTableColumn<>("Total Price");

        columnName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        columnPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("price"));
        columnQuantity.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        columnTotalPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("totalPrice"));

        allProducts.expandedProperty().setValue(true);
        tableView.setRoot(allProducts);
        addImageColumn();
        tableView.getColumns().add(columnName);
        tableView.getColumns().add(columnPrice);
        tableView.getColumns().add(columnQuantity);
        tableView.getColumns().add(columnTotalPrice);
        addActionColumn();
        //addQuantityColumn(columnQuantity);
//        mainPane.getChildren().add(tableView);
    }

    private void addImageColumn() {
        TreeTableColumn<CartRow, Void> columnImage = new TreeTableColumn<>("Product Image");

        Callback<TreeTableColumn<CartRow, Void>, TreeTableCell<CartRow, Void>> cellFactory = new Callback<TreeTableColumn<CartRow, Void>, TreeTableCell<CartRow, Void>>() {
            @Override
            public TreeTableCell<CartRow, Void> call(final TreeTableColumn<CartRow, Void> param) {
                final TreeTableCell<CartRow, Void> cell = new TreeTableCell<CartRow, Void>() {

                    private ImageView image = new ImageView();

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || tableView.getTreeItem(getIndex()).getValue().getName().equals("All products")) {
                            setGraphic(null);
                        } else {
                            image = (tableView.getTreeItem(getIndex()).getValue().getProductImage());
                            setGraphic(image);
                        }
                    }
                };
                return cell;
            }
        };

        columnImage.setCellFactory(cellFactory);

        tableView.getColumns().add(columnImage);

    }

    private void addActionColumn() {
        TreeTableColumn<CartRow, Void> columnAction = new TreeTableColumn<>("Quantity");

        Callback<TreeTableColumn<CartRow, Void>, TreeTableCell<CartRow, Void>> cellFactory = new Callback<TreeTableColumn<CartRow, Void>, TreeTableCell<CartRow, Void>>() {
            @Override
            public TreeTableCell<CartRow, Void> call(final TreeTableColumn<CartRow, Void> param) {
                final TreeTableCell<CartRow, Void> cell = new TreeTableCell<CartRow, Void>() {

                    private JFXButton increase = new JFXButton("");
                    private JFXButton decrease = new JFXButton("");

                    {
                        ImageView increaseImage = new ImageView(new Image(new File("src/main/resources/images/Plus.png").toURI().toString()));
                        increaseImage.setFitWidth(20);
                        increaseImage.setPreserveRatio(true);
                        ImageView decreaseImage = new ImageView(new Image(new File("src/main/resources/images/Minus.png").toURI().toString()));
                        decreaseImage.setFitWidth(20);
                        decreaseImage.setPreserveRatio(true);

                        increase.setGraphic(increaseImage);
                        decrease.setGraphic(decreaseImage);

                        increase.setOnAction((ActionEvent event) -> {
                            CartRow row = tableView.getTreeItem(getIndex()).getValue();
                            Product product = row.getProduct();
                            controller.getCustomerLoggedOn().increaseNumberInCart(product.getId());
                            row.setQuantity(String.valueOf(controller.getQuantityInCart(product)));
                            row.update();
                            tableView.refresh();
                        });
                        decrease.setOnAction((ActionEvent event) -> {
                            CartRow row = tableView.getTreeItem(getIndex()).getValue();
                            Product product = row.getProduct();
                            controller.getCustomerLoggedOn().decreaseNumberInCart(product.getId());
                            row.setQuantity(String.valueOf(controller.getQuantityInCart(product)));
                            row.update();
                            tableView.refresh();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || tableView.getTreeItem(getIndex()).getValue().getName().equals("All products")) {
                            setGraphic(null);
                        } else {
                            setGraphic(new HBox(increase, decrease));
                        }
                    }
                };
                return cell;
            }
        };

        columnAction.setCellFactory(cellFactory);

        tableView.getColumns().add(columnAction);
    }

    public void goToPaymentPage(ActionEvent actionEvent) {
        System.out.println("Go to payment page");
    }

    public void closeCart(ActionEvent actionEvent) {
        cartStage.close();
    }
}
