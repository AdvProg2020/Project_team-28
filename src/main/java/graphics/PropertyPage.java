package graphics;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import main.Main;

public class PropertyPage {
    public JFXTextField name;
    public JFXComboBox type;

    public AddCategoryPage categoryPage;

    public void submitPressed(ActionEvent actionEvent) {
        categoryPage.addProperty(name.getText(), type.getValue().toString());
        Main.popupStage.close();
    }
}
