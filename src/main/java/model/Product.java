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
    private ArrayList<String> allSpecialProperties;
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

    public Seller getSeller() {
        return (Seller) Database.getUserById(seller);
    }

    public void setCategory(Category category) {
        this.category = category.getId();
        allSpecialProperties = new ArrayList<>();
        for (Property property : category.getSpecialProperties()) {
            Property clonedProperty = new Property(property);
            allSpecialProperties.add(clonedProperty.getId());
            Database.add(clonedProperty);
        }
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

    public void addComment (Comment comment) {
        allComments.add(comment.getId());
    }

    public boolean hasProperty (Property property) {
        for (String thisProperty : allProperties) {
            if (property.equals(Database.getPropertyById(thisProperty)))
                return true;
        }
        for (String specialProperty : allSpecialProperties) {
            if (property.equals(Database.getPropertyById(specialProperty)))
                return true;
        }
        return false;
    }

    public long getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + this.getSeller().getFullName() + "\t" + price;
    }
}
