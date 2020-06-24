package graphics;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import controller.Database;
import controller.ManagerController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import model.Discount;
import model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class AddDiscountCodePage {
    public JFXListView allUsers;
    public VBox root;
    private ManagerController controller;
    public static Stage secondPopupStage;

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
    public ObservableList<String> allSelectedUsers;

    public void show(ManagerController controller) throws IOException {
        URL url = new File("src/main/resources/fxml/AddDiscountCodePage.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        ((AddDiscountCodePage) fxmlLoader.getController()).controller = controller;
        Main.popupStage = new Stage();
        Main.popupStage.setTitle("Add discount code page");
        Main.popupStage.setScene(new Scene(root, 250, 350));
        Main.popupStage.show();
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

        allUsers.setDisable(true);

    }

    public void generateRandomCode(ActionEvent actionEvent) {
        String random = Discount.generateRandomCode();
        code.setText(random);
    }

    public void finishPage(ActionEvent actionEvent) throws Exception {
        checkFields();
        LocalDateTime fullStartDate = LocalDateTime.of(startDate.getValue(), startTime.getValue());
        LocalDateTime fullFinishDate = LocalDateTime.of(finishDate.getValue(), finishTime.getValue());
        Discount discount = new Discount();
        discount.setCode(code.getText());
        discount.setDiscountPercent(Integer.parseInt(percent.getText()));
        discount.setStartTime(fullStartDate);
        discount.setFinishTime(fullFinishDate);
        discount.setMaximumAmount(Integer.parseInt(maxAmount.getText()));
        discount.setRepetitionNumber(Integer.parseInt(reuseNumber.getText()));
        for (Object item : allUsers.<String>getItems()) {
            discount.addUser(Database.getUserByUsername((String) item));
        }
        controller.createDiscount(discount);
        Main.popupStage.close();
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
        if (allUsers.getItems().isEmpty())
            throw new Exception("Select at least one user");
        if (!Discount.isCodeUnique(code.getText())) {
            throw new Exception("Code is not unique");
        }
    }

    public void openUsersList(ActionEvent actionEvent) throws IOException {
        URL url = new File("src/main/resources/fxml/AllUsersList.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        secondPopupStage = new Stage();
        secondPopupStage.setTitle("Select Users");
        secondPopupStage.setScene(new Scene(root, 300, 400));
        secondPopupStage.showAndWait();

        AllUsersList list = loader.getController();
        allSelectedUsers = list.getSelectedUsers();
        emptyAllUsers();
        if (allSelectedUsers != null)
            allUsers.getItems().addAll(allSelectedUsers);
    }
    private void emptyAllUsers () {
        for (int i = allUsers.getItems().size() - 1 ; i >= 0 ; i --) {
            allUsers.getItems().remove(i);
        }
    }

    public void loadDiscountFields (Discount loadedDiscount) {
        code.setText(loadedDiscount.getCode());
        percent.setText(String.valueOf(loadedDiscount.getDiscountPercent()));
        startDate.setValue(LocalDate.from(loadedDiscount.getStartTime()));
        finishDate.setValue(LocalDate.from(loadedDiscount.getFinishTime()));
        startTime.setValue(LocalTime.from(loadedDiscount.getStartTime()));
        finishTime.setValue(LocalTime.from(loadedDiscount.getFinishTime()));
        maxAmount.setText(String.valueOf(loadedDiscount.getMaximumAmount()));
        reuseNumber.setText(String.valueOf(loadedDiscount.getRepetitionNumber()));
        for (User user : loadedDiscount.getUsers()) {
            allUsers.getItems().add(user.getUsername());
        }
        disableNodes(root);

    }

    private void disableNodes (Pane pane) {
        for (Node child : pane.getChildren()) {
            if (child instanceof Pane)
                disableNodes((Pane) child);
            else
                child.setDisable(true);
        }
    }
}
