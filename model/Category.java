package model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList<Property> specialProperties;
    private ArrayList<Product> commodities;

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialProperties(ArrayList<Property> specialProperties) {
        this.specialProperties = specialProperties;
    }

    public void setCommodities(ArrayList<Product> commodities) {
        this.commodities = commodities;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Property> getSpecialProperties() {
        return specialProperties;
    }

    public ArrayList<Product> getCommodities() {
        return commodities;
    }
}
