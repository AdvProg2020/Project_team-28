package graphics;

import com.jfoenix.controls.*;
import controller.CustomerController;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import main.Main;
import model.Product;

import java.io.File;
import java.util.*;

public class Cart {
    private CustomerController controller;
    public VBox mainPane;
    private HashMap<Product, Integer> customerCart;
    public JFXTreeTableView<CartRow> tableView;

    public void initialize() {
        controller = Main.customerController;
        customerCart = controller.getCustomerLoggedOn().getCart();
        loadCartProducts();
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
        tableView.setShowRoot(false);
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
                        }
                        else {
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
        TreeTableColumn<CartRow, Void> columnAction = new TreeTableColumn<>("Actions");

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
                        }
                        else {
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

    public void goToPaymentPage() {
        System.out.println("Go to payment page");
    }

    public void closeCart() {
        Main.returnMainStage();
    }
}
