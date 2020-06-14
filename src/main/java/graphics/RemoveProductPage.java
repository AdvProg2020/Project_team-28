package graphics;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import main.Main;

public class RemoveProductPage {
    public JFXTextField productId;

    public void removeProductPressed(ActionEvent actionEvent) throws Exception {
        Main.controller.removeProduct(productId.getText());
    }
}
