package graphics;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Category;
import model.Property;

public class CategoryRow extends RecursiveTreeObject<CategoryRow> {
    public Category category;
    private StringProperty title = new SimpleStringProperty();
    private StringProperty properties = new SimpleStringProperty();

    public CategoryRow(String type) {
        title.setValue(type);
    }

    public CategoryRow(Category category) {
        this.category = category;
        title.setValue(category.getName());
        for (Property property : category.getSpecialProperties()) {
            if (properties.get() == null) {
                properties.setValue(property.getName());
            }
            properties.setValue(properties.get() + "," + property.getName());
        }
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getProperties() {
        return properties.get();
    }

    public void setProperties(String properties) {
        this.properties.set(properties);
    }

    public StringProperty propertiesProperty() {
        return properties;
    }
}
