package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Main;
import model.Category;
import model.Property;

import java.io.File;
import java.io.IOException;

public class AddCategoryPage {
    public VBox mainPane;
    public JFXTextField name;

    JFXTreeTableView<PropertyRow> treeTableView;
    Category category = new Category();

    public void initialize() {
        treeTableView = new JFXTreeTableView<PropertyRow>();
        TreeItem<PropertyRow> properties = new TreeItem<>(new PropertyRow("all properties"));

        for (Property property : category.getOnlyThisProperties()) {
            properties.getChildren().add(new TreeItem<>(new PropertyRow(property)));
        }

        TreeTableColumn<PropertyRow, String> columnTitle = new TreeTableColumn<>("Name");
        TreeTableColumn<PropertyRow, String> columnType = new TreeTableColumn<>("Type");

        columnTitle.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        columnType.setCellValueFactory(new TreeItemPropertyValueFactory<>("type"));

        columnTitle.setPrefWidth(125);
        columnType.setPrefWidth(250);

        treeTableView.getColumns().add(columnTitle);
        treeTableView.getColumns().add(columnType);

        treeTableView.setRoot(properties);

        addButtonToTable();

        mainPane.getChildren().add(treeTableView);
    }

    private void addButtonToTable() {
        TreeTableColumn<PropertyRow, Void> colBtn = new TreeTableColumn<>("Button Column");

        Callback<TreeTableColumn<PropertyRow, Void>, TreeTableCell<PropertyRow, Void>> cellFactory = new Callback<TreeTableColumn<PropertyRow, Void>, TreeTableCell<PropertyRow, Void>>() {
            @Override
            public TreeTableCell<PropertyRow, Void> call(final TreeTableColumn<PropertyRow, Void> param) {
                final TreeTableCell<PropertyRow, Void> cell = new TreeTableCell<PropertyRow, Void>() {

                    private final JFXButton remove = new JFXButton("");

                    {

                        ImageView removeImage = new ImageView(new File("src/main/resources/images/remove-icon.png").toURI().toString());
                        removeImage.setFitHeight(20);
                        removeImage.setPreserveRatio(true);
                        remove.setGraphic(removeImage);

                        remove.setOnAction((ActionEvent actionEvent) -> {
                            Property property = treeTableView.getTreeItem(getIndex()).getValue().getProperty();
                            //Database.remove(property); TODO?
                            category.removeProperty(property);
                            mainPane.getChildren().remove(treeTableView);
                            initialize();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || treeTableView.getTreeItem(getIndex()).getValue().getProperty() == null) {
                            System.out.println(getIndex());
                            setGraphic(null);
                        } else {
                            System.out.println(getIndex());
                            setGraphic(remove);
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


    public void newPropertyPressed(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/PropertyPage.fxml").toURI().toURL());
        Parent root = fxmlLoader.load();
        ((PropertyPage) fxmlLoader.getController()).categoryPage = this;
        Main.popupStage = new Stage();
        Main.popupStage.setScene(new Scene(root, 250, 250));
        //Main.popupStage.initStyle(StageStyle.UNDECORATED);                Cool stuff :)))
        Main.popupStage.initModality(Modality.WINDOW_MODAL);
        Main.popupStage.initOwner(Main.mainStage);
        Main.popupStage.show();
    }

    public void submitPressed(ActionEvent actionEvent) throws Exception {
        if (category.getName() == null) {
            category.setName(name.getText());
            Database.add(category);
            if (category.getParentCategory() != null) {
                Category parentCategory = Database.getCategoryById(category.getParentCategory());
                parentCategory.addSubCategory(category);
                Database.getAllCategories().remove(parentCategory);
                Database.add(parentCategory
                );
            }
        }
        Main.setMainStage("Manage Categories", "src/main/resources/fxml/ManageCategories.fxml");
    }

    public void addProperty(String name, String type) throws Exception {
        Property property = new Property();
        property.setNumber(type.equals("Integer"));
        property.setName(name);
        Database.add(property);
        category.addProperty(property);
        mainPane.getChildren().remove(treeTableView);
        initialize();
    }

    public void setParentCategory(Category category) {
        if (category != null)
            this.category.setParentCategory(category.getId());
    }

    public void fillFields(Category category) {
        this.category = category;
        mainPane.getChildren().remove(treeTableView);
        initialize();
    }
}
