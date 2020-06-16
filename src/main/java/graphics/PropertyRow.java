package graphics;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Property;

public class PropertyRow extends RecursiveTreeObject<PropertyRow> {
    StringProperty name = new SimpleStringProperty();
    StringProperty type = new SimpleStringProperty();

    public PropertyRow(String title) {
        name.setValue(title);
    }

    public PropertyRow(Property property) {
        name.setValue(property.getName());
        type.setValue(property.isNumber() ? "Integer" : "String");
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }
}
