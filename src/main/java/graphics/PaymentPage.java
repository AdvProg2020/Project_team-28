package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import controller.CustomerController;
import controller.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import model.Discount;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PaymentPage {
    public JFXTextArea address;
    public JFXButton changeAddress;
    public JFXTextField discountCode;
    public JFXButton addDiscountButton;
    public JFXButton removeDiscountButton;
    public Label totalPriceLabel;
    public JFXButton payButton;
    public JFXRadioButton bankButton;
    public JFXRadioButton walletButton;
    private CustomerController controller;
    private ToggleGroup radioGroup = new ToggleGroup();
    private String paymentMethod;

    public void show(CustomerController controller) throws Exception {
        URL url = new File("src/main/resources/fxml/PaymentPage.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ((PaymentPage) fxmlLoader.getController()).controller = controller;
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Payment page");
        Main.popupStage.setScene(new Scene(root, 330, 400));
        Main.popupStage.show();
        ((PaymentPage) fxmlLoader.getController()).refresh();
        ((PaymentPage) fxmlLoader.getController()).checkAddress();
        ((PaymentPage) fxmlLoader.getController()).toggleButtons();
    }

    private void toggleButtons() {
        walletButton.setToggleGroup(radioGroup);
        bankButton.setToggleGroup(radioGroup);
        bankButton.setSelectedColor(Color.rgb(177, 66, 255));
        walletButton.setSelectedColor(Color.rgb(177, 66, 255));
    }

    private void checkAddress() throws Exception {
        if (controller.getAddress() == null || controller.getAddress().isEmpty()) {
            openProfilePage();
            throw new Exception("You should set your address");
        }
        address.setText(controller.getAddress());
    }

    public void openProfilePage() throws IOException {
        Main.popupStage.close();
        SoundPlayer.stopBackground();
        String fxmlPath;
        fxmlPath = "src/main/resources/fxml/Profile.fxml";
        Main.setMainStage("", fxmlPath);
    }

    public void addDiscount(ActionEvent actionEvent) throws Exception {
        controller.useDiscountCode(discountCode.getText());
        addDiscountButton.setDisable(true);
        removeDiscountButton.setVisible(true);
        discountCode.setDisable(true);
        refresh();
    }

    public void removeDiscount(ActionEvent actionEvent) {
        removeDiscountButton.setVisible(false);
        addDiscountButton.setDisable(false);
        discountCode.setDisable(false);
        controller.removeDiscountCode();
        refresh();
    }

    private void refresh() {
        try {
            totalPriceLabel.setText(String.valueOf(controller.getTotalPrice()));
        } catch (Exception ignored) {
        }
    }

    public void finishPayment(ActionEvent actionEvent) throws Exception {
        long totalPrice = controller.purchase(paymentMethod);
        Discount gift;
        if (totalPrice >= 5100 && totalPrice <= 10000) {
            gift = Discount.getGiftDiscount(controller.getCustomerLoggedOn(), (int) ((totalPrice - 5000)/100));
            showGiftMessage(gift);
        }else if (totalPrice > 10000) {
            gift = Discount.getGiftDiscount(controller.getCustomerLoggedOn(), 50);
            showGiftMessage(gift);
        }

        Main.popupStage.close();
    }

    private void showGiftMessage(Discount discount) {
        Alert message = new Alert(Alert.AlertType.CONFIRMATION);
        message.getButtonTypes().clear();
        message.getButtonTypes().add(ButtonType.OK);
        message.setHeaderText(null);
        message.setContentText("Congratulations!\n" +
                "You got a " + discount.getDiscountPercent() + "% discount code:\n" +
                "Code: " + discount.getCode());
        message.showAndWait();
    }

    public void updatePaymentMethod(ActionEvent actionEvent) {
        paymentMethod = ((JFXRadioButton)radioGroup.getSelectedToggle()).getText();
    }
}
