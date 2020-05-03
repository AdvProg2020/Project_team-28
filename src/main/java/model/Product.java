package model;

import java.util.ArrayList;
import java.util.UUID;

public class Product {
    private enum productStatus  {A, B}
    private String name;
    private String brand;
    private long price;
    private String seller;
    private boolean inStock;
    private String category;
    private String description;
    private ArrayList<String> allScores;
    private ArrayList<String> allComments;
    private ArrayList<String> allProperties;
    private String id;

    public Product() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public double getAverageScore() {
        return 0;
    }

    public void addProperty(Property property) {

    }

    public long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + price;
    }
}
