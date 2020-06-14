package graphics;

import com.jfoenix.controls.*;
import controller.CustomerController;
import controller.Database;
import controller.ProductController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Category;
import model.Customer;
import model.Product;
import model.Seller;
import java.nio.file.Paths;
import java.util.*;

public class Cart {
    static CustomerController controller;
    public Cart (CustomerController customerController) {
        controller = customerController;
    }

    public static void show () {
        //mockModels();

        Stage cartStage = new Stage();
        Pane pane = new Pane();
        Scene stageScene = new Scene(pane, 600, 400);
        stageScene.getStylesheets().add(Paths.get("src/main/resources/stylesheets/CartStyleSheet.css").toUri().toString());

        pane.getChildren().add(getHeader());

        TableView<Product> cart = getProductsTable();
        cart.setLayoutY(50);
        cart.setPrefHeight(350);
        cart.setPrefWidth(600);
        cart.setId("productsTable");
        pane.getChildren().add(cart);

//        JFXComboBox<String> comboBox = new JFXComboBox<>();
//        comboBox.setLayoutX(200);
//        comboBox.setLayoutY(200);
//        comboBox.getItems().add("ONE");
//        comboBox.getItems().add("TWO");
//        comboBox.getItems().add("THREE");
//        pane.getChildren().add(comboBox);
        cartStage.setScene(stageScene);
        cartStage.show();
    }

    public static void mockModels() {
        //mocking
        Customer customer = new Customer("Taha7900","Taha","Jahani",
                "Taha7900@gmail.com", "09367642209", "7900", 15000);
        Database.add(customer);
        Seller seller = new Seller("OnlySeller", "Ahmad", "Zoghi",
                "AhmadZoghi@gmail.com", "09100000000", "1234", 1000, " ", " ");
        Database.add(seller);
        Category digital = new Category("Digital Devices");
        Category homeAppliance = new Category("Households");
        Database.add(digital);
        Database.add(homeAppliance);
        try {
            controller = new CustomerController(customer, new ProductController());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Product laptop = new Product("Zenbook flip", "Asus",
                "17000000", seller.getId(), digital.getName());
        Product phone = new Product("Galaxy A10", "Samsung",
                "3000000", seller.getId(), digital.getName());
        Product tablet = new Product("Galaxt Tab S2", "Samsung",
                "5000000", seller.getId(), digital.getName());
        Product fridge = new Product("Fridge", "Bosch",
                "25000000", seller.getId(), homeAppliance.getName());
        Product airConditioner = new Product("Air Conditioner",
                "Ogeneral", "13000000", seller.getId(), homeAppliance.getName());
        Product washingMachine = new Product("Washing machine",
                "LG", "5000000", seller.getId(), homeAppliance.getName());

        laptop.setQuantity(10);
        phone.setQuantity(5);
        tablet.setQuantity(18);
        Database.add(laptop);
        Database.add(phone);
        Database.add(tablet);
        Database.add(fridge);
        Database.add(airConditioner);
        Database.add(washingMachine);
        digital.addProduct(laptop);
        digital.addProduct(phone);
        digital.addProduct(tablet);
        homeAppliance.addProduct(fridge);
        homeAppliance.addProduct(airConditioner);
        homeAppliance.addProduct(washingMachine);

        controller.addToCart(laptop.getId());
        controller.addToCart(phone.getId());
        controller.addToCart(tablet.getId());
        controller.addToCart(fridge.getId());
        controller.addToCart(airConditioner.getId());
        controller.addToCart(washingMachine.getId());
        //end of mocking
    }

    public static TableView<Product> getProductsTable () {
        TableView<Product> cart = new TableView<>();

        cart.setItems(getCart());
        cart.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn nameCol = new TableColumn("Name");
        TableColumn brandCol = new TableColumn("Brand");
        TableColumn priceCol = new TableColumn("Price");
        TableColumn quantityCol = new TableColumn("Quantity");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        Callback<TableColumn<Product, String>, TableCell<Product, String>> nameLinks =
                new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
                    @Override
                    public TableCell<Product, String> call(TableColumn<Product, String> productStringTableColumn) {
                        TableCell<Product, String> cell = new TableCell<Product, String>() {
                            @Override
                            protected void updateItem(String s, boolean isEmpty) {
                                super.updateItem(s, isEmpty);
                                if (isEmpty) {
                                    setText(null);
                                }else {
                                    Product item = getTableView().getItems().get(getIndex());
                                    setText(s);
                                    setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            //TODO
                                            System.out.println("go to product " + item.getName());
                                        }
                                    });
                                }
                            }
                        };
                        return cell;
                    }
                };
        nameCol.setCellFactory(nameLinks);

        Callback<TableColumn<Product, String>, TableCell<Product, String>> quantityActions =
                new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
            @Override
            public TableCell<Product, String> call(TableColumn<Product, String> productStringTableColumn) {
                TableCell<Product, String> cell = new TableCell<Product, String>() {
                    @Override
                    protected void updateItem(String s, boolean b) {
                        super.updateItem(s, b);
                        if(b)
                            return;
                        Group group = new Group();
                        Product item = getTableView().getItems().get(getIndex());
                        int quantity = 1;
                        try {
                            quantity = controller.getCustomerLoggedOn().getCart().get(item);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        Label label = new Label(String.valueOf(quantity));
                        label.setId("quantityLabel");
                        JFXButton incrementButton = getIncrementButton();
                        incrementButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (Integer.parseInt(label.getText()) < item.getQuantity())
                                    controller.addToCart(item.getId());
                                cart.refresh();
                            }
                        });
                        JFXButton decrementButton = getDecrementButton();
                        decrementButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                if (Integer.parseInt(label.getText()) != 1)
                                    controller.decreaseNumberOfProduct(item.getId());
                                cart.refresh();
                            }
                        });
                        decrementButton.setLayoutX(0);
                        incrementButton.setLayoutX(40);
                        label.setLayoutX(20);
                        group.getChildren().addAll(decrementButton, label, incrementButton);
                        if (b) {
                            setText(null);
                            setGraphic(null);
                        }else {
                            setGraphic(group);
                        }
                    }
                };
                return cell;
            }
        };
        quantityCol.setCellFactory(quantityActions);

        cart.getColumns().addAll(nameCol,brandCol, priceCol,quantityCol);

        return cart;
    }

    public static JFXButton getIncrementButton () {
        JFXButton incrementButton = new JFXButton("+");
        incrementButton.setId("chaneQuantityButton");
        return incrementButton;
    }

    public static JFXButton getDecrementButton () {
        JFXButton decrementButton = new JFXButton("-");
        decrementButton.setId("chaneQuantityButton");
        return decrementButton;
    }

    public static ObservableList<Product> getCart () {
        try {
            HashMap<Product, Integer> cartReceived = controller.getCustomerLoggedOn().getCart();
            return FXCollections.observableArrayList(cartReceived.keySet());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Label getHeader () {
        Label header = new Label("CART");
        header.setAlignment(Pos.TOP_CENTER);
        header.setLayoutX(250);
        header.setId("header");
        return header;
    }
}
