package graphics;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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

public class EditableTextArea extends HBox {
    @FXML
    private JFXTextArea textArea;
    @FXML
    private JFXButton editButton;
    @FXML
    private JFXButton submitButton;
    private ObjectProperty<EventHandler<ActionEvent>> propertyOnAction = new SimpleObjectProperty<EventHandler<ActionEvent>>();

    public EditableTextArea() throws MalformedURLException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/EditableTextArea.fxml").toURI().toURL());
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
        return textArea.textProperty();
    }

    public String getPromptText() {
        return textArea.getPromptText();
    }

    public void setPromptText(String value) {
        textArea.setPromptText(value);
    }


    @FXML
    protected void submitPressed() {
        textArea.setEditable(false);
        editButton.setVisible(true);
        editButton.setManaged(true);
        submitButton.setManaged(false);
        submitButton.setVisible(false);

        propertyOnAction.get().handle(new ActionEvent(textArea, null));
    }

    @FXML
    protected void editPressed() {
        textArea.setEditable(true);
        editButton.setVisible(false);
        editButton.setManaged(false);
        submitButton.setManaged(true);
        submitButton.setVisible(true);
    }
}