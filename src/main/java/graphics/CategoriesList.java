package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import controller.Database;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.Category;

public class CategoriesList {
    public Stage stage;
    public JFXListView<String> mainList;
    public JFXButton finish;
    private String selectedCategory;

    public void initialize () {
        for (Category category : Database.getAllCategories()) {
            mainList.getItems().add(category.getName());
        }
    }

    public void finishSelection(ActionEvent actionEvent) {
        selectedCategory = mainList.getSelectionModel().getSelectedItem();
        AddProductPage.popupStage.close();
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }
}
