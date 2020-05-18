package model;

import controller.Database;

import java.util.ArrayList;
import java.util.UUID;

public class Product {
    private enum productStatus  {A, B}
    private String name;
    private String brand;
    private long price;
    private ArrayList<String> sellers;
    private boolean inStock;
    private String category;
    private String off;
    private String description;
    private int viewed = 0;
    private ArrayList<String> allScores;
    private ArrayList<String> allComments;
    private ArrayList<String> allProperties;
    private ArrayList<String> allSpecialProperties;
    private ArrayList<String> allBuyers;
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

    public ArrayList<Seller> getSellers() {
        ArrayList<Seller> allSellers = new ArrayList<>();
        for (String seller : this.sellers) {
            allSellers.add((Seller) Database.getUserById(seller));
        }
        return allSellers;
    }

    public void addViewed () {
        this.viewed += 1;
    }

    public void addBuyer (Customer customer) {
        allBuyers.add(customer.getId());
    }

    public int getViewed() {
        return viewed;
    }

    public ArrayList<Customer> getAllBuyers () {
        ArrayList<Customer> result = new ArrayList<>();
        for (String buyer : allBuyers) {
            result.add((Customer) Database.getUserById(buyer));
        }
        return result;
    }

    public Off getOff() {
        return Database.getOffById(off);
    }

    public void setOff(String off) {
        this.off = off;
    }

    public ArrayList<Property> getAllProperties() {
        ArrayList<Property> result = new ArrayList<>();
        for (String property : this.allProperties) {
            result.add(Database.getPropertyById(property));
        }
        return result;
    }

    public ArrayList<Property> getAllSpecialProperties() {
        ArrayList<Property> result = new ArrayList<>();
        for (String property : allSpecialProperties) {
            result.add(Database.getPropertyById(property));
        }
        return result;
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

    public Category getCategory() {
        return Database.getCategoryById(this.category);
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
        switch (property.getName()) {
            case "category":
                return this.category.equals(property.getValueString());
            case "name":
                return this.name.contains(property.getValueString());
            case "inStock":
                return (this.inStock ? 1 : 0) == property.getValueLong();
            case "brand":
                return this.brand.equals(property.getValueString());
            case "maxPrice":
                return this.price <= property.getValueLong();
            case "minPrice":
                return this.price >= property.getValueLong();
            case "hasOff":
                return ((this.hasOff()) ? 1 : 0) == property.getValueLong();
        }
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

    public void setMainSeller (Seller seller) {
        this.sellers.add(0,seller.getId());
    }

    public long getPrice() {
        if (this.hasOff())
            return price * (100 - this.getOff().getDiscountAmount())/100;
        else
            return price;
    }

    public boolean hasOff () {
        return this.off != null;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + this.getSellers().get(0).getFullName() + "\t" + price;
    }
}