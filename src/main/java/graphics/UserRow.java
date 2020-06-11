package graphics;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.User;

public class UserRow extends RecursiveTreeObject<UserRow> {

    private StringProperty username = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();

    public UserRow() {

    }

    public UserRow(String title) {
        username.setValue(title);
    }

    public UserRow(User user) {
        username.setValue(user.getUsername());
        firstName.setValue(user.getFirstName());
        surname.setValue(user.getSurname());
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public String toString() {
        return getUsername() + getFirstName();
    }
}
