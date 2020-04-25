package model;

import java.util.ArrayList;
import java.util.UUID;

public class Category {
    private String name;
    private ArrayList<String> specialProperties;
    private ArrayList<String> products;
    private String id;

    public Category() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialProperties(ArrayList<Property> specialProperties) {
        this.specialProperties = new ArrayList<>();
        for (Property specialProperty : specialProperties) {
            this.specialProperties.add(specialProperty.getId());
        }
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = new ArrayList<>();
        for (Product product : products) {
            this.products.add(product.getId());
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<Property> getSpecialProperties() {
        return null;
    }

    public ArrayList<Product> getProducts() {
        return null;
    }
}
