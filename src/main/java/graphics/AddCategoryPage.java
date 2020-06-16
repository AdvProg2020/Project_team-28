package graphics;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import controller.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

        for (Property property : category.getSpecialProperties()) {
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

        mainPane.getChildren().add(treeTableView);
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

    public void submitPressed(ActionEvent actionEvent) throws IOException {
        category.setName(name.getText());
        Database.add(category);
        if (category.getParentCategory() != null)
            Database.getCategoryById(category.getParentCategory()).addSubCategory(category);
        Main.setMainStage("Manage Categories", "src/main/resources/fxml/ManageCategories.fxml");
    }

    public void addProperty(String name, String type) {
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
}
