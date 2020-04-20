package model;

import java.util.ArrayList;

public class Filter {
    private ArrayList<Property> properties;
    private Category filteredCategory;

    public boolean isValid(Product product) {
        return true;
    }

    public void addRestriction(Category category) {

    }

    public void addRestriction(ArrayList<Property> properties) {

    }
}
