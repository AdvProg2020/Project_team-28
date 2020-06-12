package model;

import controller.Database;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.UUID;

public class Category {
    private String name;
    private ArrayList<String> specialProperties;
    private ArrayList<String> products;
    private String id;

    public Category(String name) {
        this.name = name;
        products = new ArrayList<>();
        specialProperties = new ArrayList<>();
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
        ArrayList<Property> finalList = new ArrayList<>();
        for (String property : specialProperties) {
            finalList.add(Database.getPropertyById(property));
        }
        return finalList;
    }

    public ArrayList<Product> getProducts() throws Exception{
        ArrayList<Product> finalList = new ArrayList<>();
        for (String product : products) {
            finalList.add(Database.getProductById(product));
        }
        return finalList;
    }

    public void addProduct (Product product) {
        this.products.add(product.getId());
    }

    public void addProperty (Property property) {
        this.specialProperties.add(property.getId());
    }
    @Override
    public boolean equals (Object object) {
        if (object instanceof Category)
            return ((Category) object).getName().equals(this.name);
        else if (object instanceof String)
            return object.equals(this.name);
        return false;
    }
}
