package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import controller.Database;
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
import model.Category;

import java.io.File;
import java.util.ArrayList;

public class ManageCategories {
    public VBox mainPane;
    JFXTreeTableView<CategoryRow> treeTableView;

    public void initialize() {
        treeTableView = new JFXTreeTableView<CategoryRow>();
        TreeItem<CategoryRow> categories = new TreeItem<>(new CategoryRow("all categories"));

        for (Category category : Database.getAllCategories()) {
            if (category.getParentCategory() == null) {
                TreeItem<CategoryRow> treeItem = new TreeItem<>(new CategoryRow(category));
                for (String subCategory : category.getSubCategories()) {
                    treeItem.getChildren().add(new TreeItem<>(new CategoryRow(Database.getCategoryById(subCategory))));
                }
                categories.getChildren().add(treeItem);
            }
        }
        TreeTableColumn<CategoryRow, String> columnTitle = new TreeTableColumn<>("Name");
        TreeTableColumn<CategoryRow, String> columnProperty = new TreeTableColumn<>("Properties");

        columnTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
        columnProperty.setCellValueFactory(new TreeItemPropertyValueFactory<>("properties"));

        columnTitle.setPrefWidth(125);
        columnProperty.setPrefWidth(250);

        treeTableView.getColumns().add(columnTitle);
        treeTableView.getColumns().add(columnProperty);

        treeTableView.setRoot(categories);

        addButtonToTable();

        mainPane.getChildren().add(treeTableView);
    }

    private void addButtonToTable() {
        TreeTableColumn<CategoryRow, Void> colBtn = new TreeTableColumn<>("Button Column");

        Callback<TreeTableColumn<CategoryRow, Void>, TreeTableCell<CategoryRow, Void>> cellFactory = new Callback<TreeTableColumn<CategoryRow, Void>, TreeTableCell<CategoryRow, Void>>() {
            @Override
            public TreeTableCell<CategoryRow, Void> call(final TreeTableColumn<CategoryRow, Void> param) {
                final TreeTableCell<CategoryRow, Void> cell = new TreeTableCell<CategoryRow, Void>() {

                    private final JFXButton add = new JFXButton("add subcategory");
                    private final JFXButton remove = new JFXButton("remove");

                    {
                        add.setOnAction((ActionEvent event) -> {
                            try {
                                CategoryRow categoryRow = treeTableView.getTreeItem(getIndex()).getValue();
                                FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/AddCategoryPage.fxml").toURI().toURL());
                                Parent root = fxmlLoader.load();
                                ((AddCategoryPage) fxmlLoader.getController()).setParentCategory(categoryRow.category);
                                Main.mainStage.setScene(new Scene(root, 620, 500));
                            } catch (Exception e) {
                                Main.showErrorDialog(e);
                            }
                        });
                        remove.setOnAction((ActionEvent event) -> {
                            CategoryRow categoryRow = treeTableView.getTreeItem(getIndex()).getValue();
                            for (String category : (ArrayList<String>) categoryRow.category.getSubCategories().clone()) {
                                Database.remove(Database.getCategoryById(category));
                            }
                            Database.remove(categoryRow.category);
                            mainPane.getChildren().remove(treeTableView);
                            initialize();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        CategoryRow row = new CategoryRow(":|");
                        if (!empty) {
                            row = treeTableView.getTreeItem(getIndex()).getValue();
                        }
                        if (empty) {
                            setGraphic(null);
                        } else if (row.category == null) {
                            setGraphic(add);
                        } else if (row.category.getParentCategory() == null) {
                            setGraphic(new HBox(add, remove));
                        } else {
                            setGraphic(remove);
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
