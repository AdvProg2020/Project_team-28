package graphics;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import main.Main;

public class AddSupporterMenu {
    public JFXTextField username;

    public void addSupporterPressed() throws Exception {
        Main.controller.addSupporter(username.getText());
        Main.popupStage.close();
    }
}
