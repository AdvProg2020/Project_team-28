package graphics;

import com.jfoenix.controls.JFXTextField;
import controller.Database;
import javafx.event.ActionEvent;

public class SetConstantsMenu {
    public JFXTextField wage;
    public JFXTextField minimumAmount;

    public void setConstantsPressed(ActionEvent actionEvent) throws Exception {
        Database.sendGET("constant?wage=" + wage.getText() + "&minimumCredit=" + minimumAmount.getText());
    }
}
