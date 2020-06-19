package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.skins.JFXDatePickerContent;
import com.jfoenix.skins.JFXTimePickerContent;
import com.jfoenix.validation.RegexValidator;
import controller.ManagerController;
import controller.SellerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Discount;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AddDiscountCodePage {
    private ManagerController controller;
    private Stage stage;

    public RegexValidator percentValid;
    public JFXTextField percent;
    public JFXTextField reuseNumber;
    public JFXTextField maxAmount;
    public JFXButton generateButton;
    public JFXTextField code;
    public JFXTimePicker startTime;
    public JFXTimePicker finishTime;
    public JFXDatePicker startDate;
    public JFXDatePicker finishDate;
    public ArrayList<JFXTextField> allTexts = new ArrayList<>();

    public void show(ManagerController controller) throws IOException {
        this.controller = controller;
        URL url = new File("src/main/resources/fxml/AddDiscountCodePage.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage = new Stage();
        stage.setTitle("Add discount code page");
        stage.setScene(new Scene(root, 250, 350));
        stage.show();
    }

    public void initialize () {
        allTexts.add(code);
        allTexts.add(percent);
        allTexts.add(reuseNumber);
        allTexts.add(maxAmount);

        percentValid.setRegexPattern("\\d+");
        percent.getValidators().add(percentValid);
        reuseNumber.getValidators().add(percentValid);
        maxAmount.getValidators().add(percentValid);

        percent.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                percent.validate();
        });
        reuseNumber.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                reuseNumber.validate();
        });
        maxAmount.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                maxAmount.validate();
        });

    }

    public void generateRandomCode(ActionEvent actionEvent) {
        String random = Discount.generateRandomCode();
        code.setText(random);
    }

    public void finishPage(ActionEvent actionEvent) {
        try {
            checkFields();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        LocalDateTime fullStartDate = LocalDateTime.of(startDate.getValue(), startTime.getValue());
        LocalDateTime fullFinishDate = LocalDateTime.of(finishDate.getValue(), finishTime.getValue());
        Discount discount = new Discount();
        discount.setCode(code.getText());
        discount.setDiscountPercent(Integer.parseInt(percent.getText()));
        discount.setStartTime(fullStartDate);
        discount.setFinishTime(fullFinishDate);
        discount.setMaximumAmount(Integer.parseInt(maxAmount.getText()));
        discount.setRepetitionNumber(Integer.parseInt(reuseNumber.getText()));
        controller.createDiscount(discount);
        stage.close();
    }

    public void checkFields () throws Exception {
        for (JFXTextField text : allTexts) {
            if (text.getText() == null)
                throw new Exception("Fill all the fields");
        }
        if (startTime.getValue() == null || startDate.getValue() == null ||
            finishTime.getValue() == null || finishDate.getValue() == null) {
            throw new Exception("Select all the dates and times");
        }
        if (!Discount.isCodeUnique(code.getText())) {
            throw new Exception("Code is not unique");
        }
    }
}
