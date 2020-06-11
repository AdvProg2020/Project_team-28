package graphics;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import main.Main;

public class AddManagerMenu {
    public JFXTextField username;

    public void addManagerPressed(ActionEvent actionEvent) throws Exception {
        Main.controller.addManager(username.getText());
        Main.popupStage.close();
    }
}
