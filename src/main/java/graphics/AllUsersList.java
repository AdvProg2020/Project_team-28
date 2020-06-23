package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import controller.Database;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.DepthTest;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import model.Category;
import model.User;

import java.util.ArrayList;

public class AllUsersList {
    public Stage stage;
    public JFXListView<String> mainList;
    public JFXButton finish;
    private ObservableList<String> selectedUsers;

    public void initialize () {
        for (User user : Database.getAllUsers()) {
            mainList.getItems().add(user.getUsername());
        }
        mainList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mainList.setDepthTest(DepthTest.ENABLE);
    }

    public void finishSelection(ActionEvent actionEvent) {
        selectedUsers = mainList.getSelectionModel().getSelectedItems();
        AddDiscountCodePage.secondPopupStage.close();
    }

    public ObservableList<String> getSelectedUsers() {
        return selectedUsers;
    }
}
