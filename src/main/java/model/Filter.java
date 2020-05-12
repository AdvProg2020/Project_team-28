package model;

import controller.Database;

import java.util.ArrayList;
import java.util.UUID;

public class Filter {
    private ArrayList<String> properties;
    private Category filteredCategory;
    private String id;

    public Filter() {
        this.id = UUID.randomUUID().toString();
    }

    public boolean isValid(Product product) {
        boolean result = true;
        for (String property : properties) {
            if (!product.hasProperty(Database.getPropertyById(property))) {
                result = false;
                break;
            }
        }
        return result;
    }

    public void addRestriction(Category category) {

    }

    public void addRestriction(ArrayList<Property> properties) {

    }
}
