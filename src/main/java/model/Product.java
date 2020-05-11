package model;

import controller.Database;

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
        allScores = new ArrayList<>();
        allComments = new ArrayList<>();
        allProperties = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getSeller() {
        return seller;
    }

    public boolean isInStock() {
        return inStock;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageScore() {
        float averageResult = 0;
        for (String score : allScores)
            averageResult += Database.getScoreById(score).getScore();
        return averageResult/allScores.size();
    }

    public void addProperty(Property property) {

    }

    public void addScore (Score score) {
        allScores.add(score.getId());
    }

    public long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + price;
    }
}
