package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import controller.Database;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.Category;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
