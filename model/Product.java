package model;

import java.util.ArrayList;

public class Product {
    private String productId;
    private enum productStatus  {A, B}
    private String name;
    private String brand;
    private String price;
    private Seller seller;
    private boolean inStock;
    private Category category;
    private String description;
    private ArrayList<Score> allScores;
    private ArrayList<Comment> allComments;
    private ArrayList<Property> allProperties;

    public double getAverageScore() {
        return 0;
    }

    public void addProperty(Property property) {

    }
}
