package model;


import java.util.ArrayList;
import java.util.UUID;

public class Filter {
    private ArrayList<Property> properties;
    private Category filteredCategory;
    private String id;

    public Filter() {
        this.id = UUID.randomUUID().toString();
    }

    public boolean isValid(Product product) {
        return validateCategory(product) && validateProperties(product);
    }

    public boolean validateProperties (Product product) {
        boolean result = true;
        for (Property property : properties) {
            if (!product.hasProperty(property)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public boolean validateCategory (Product product) {
        if (filteredCategory != null)
            return this.filteredCategory.equals(product.getCategory());
        return true;
    }

    public void addRestriction(Category category) {
        this.filteredCategory = category;
    }

    public void addRestriction(String property, boolean isNumber) {
        this.properties.add(Property.createPropertyFromString(property, isNumber));
    }
}
