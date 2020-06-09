package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class EditableTextField extends HBox {
    @FXML
    private JFXTextField textField;
    @FXML
    private JFXButton editButton;
    @FXML
    private JFXButton submitButton;
    private ObjectProperty<EventHandler<ActionEvent>> propertyOnAction = new SimpleObjectProperty<EventHandler<ActionEvent>>();

    public EditableTextField() throws MalformedURLException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/EditableTextField.fxml").toURI().toURL());
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public final ObjectProperty<EventHandler<ActionEvent>> onActionProperty() {
        return propertyOnAction;
    }

    public final EventHandler<ActionEvent> getOnAction() {
        return propertyOnAction.get();
    }

    public final void setOnAction(EventHandler<ActionEvent> handler) {
        propertyOnAction.set(handler);
    }

    public String getText() {
        return textProperty().get();
    }

    public void setText(String value) {
        textProperty().set(value);
    }

    public StringProperty textProperty() {
        return textField.textProperty();
    }

    public String getPromptText() {
        return textField.getPromptText();
    }

    public void setPromptText(String value) {
        textField.setPromptText(value);
    }

    @FXML
    protected void submitPressed() {
        textField.setEditable(false);
        editButton.setVisible(true);
        editButton.setManaged(true);
        submitButton.setManaged(false);
        submitButton.setVisible(false);

        propertyOnAction.get().handle(new ActionEvent(textField, null));
    }

    @FXML
    protected void editPressed() {
        textField.setEditable(true);
        editButton.setVisible(false);
        editButton.setManaged(false);
        submitButton.setManaged(true);
        submitButton.setVisible(true);
    }
}