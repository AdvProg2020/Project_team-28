package controller;

import model.*;

import java.util.ArrayList;
import java.util.Comparator;

public class ProductController extends UserController {
    private static Product currentProduct;
    private static Category currentCategory;
    private static Comparator<Product> comparator;
    private static ArrayList<Comparator<Product>> allComparators;
    private static Filter currentFilter = new Filter();

    public ProductController () {
        allComparators = new ArrayList<>();
        allComparators.add(new sortByViewed());
        allComparators.add(new sortByTime());
        allComparators.add(new sortByScore());
    }
    public ProductController (Product currentProduct) {
        ProductController.currentProduct = currentProduct;
        allComparators = new ArrayList<>();
        allComparators.add(new sortByViewed());
        allComparators.add(new sortByTime());
        allComparators.add(new sortByScore());
    }

    public static String showProduct(String productId) {
        Product product = Database.getProductById(productId);
        assert product != null;
        product.addViewed();
        Seller seller = product.getSeller();
        assert seller != null;
        return "Product name:\t" + product.getName() + "\n" +
                "Price:\t" + product.getPrice() + "\n" +
                "Seller Company:\t" + seller.getCompanyName() + "\n" +
                "Average Score:\t" + product.getAverageScore() +
                "Description:\n" + product.getDescription();
    }

    public static String showProducts() {
        StringBuilder result = new StringBuilder();
        result.append("id\tname\tseller\tprice\n");
        for (Product product : Database.getAllProducts()) {
            result.append(product.toString()).append("\n");
        }
        return result.toString();
    }

    public static String digest() {
        return showProduct(currentProduct.getId());
    }

    public void addReview(String title, String text, boolean hasBought) {
        Comment thisComment = new Comment(UserController.getUser(), currentProduct, title, text, hasBought);
        Database.add(thisComment);
        currentProduct.addComment(thisComment);
    }

    public ArrayList<String> viewCategories() {
        ArrayList<Category> allCategories = Database.getAllCategories();
        ArrayList<String> categoryNames = new ArrayList<>();
        for (Category category : allCategories) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }

    public String showAvailableFilters () {
        setCategoryForFilter();
        StringBuilder result = new StringBuilder();
        result.append("Name\nBrand\nIn stock\nPrice\n");
        for (Property property : currentCategory.getSpecialProperties()) {
            result.append(property.getName()).append("\n");
        }
        return result.toString();
    }

    public void setCategoryForFilter () {
        for (Property property : currentFilter.getProperties()) {
            if (property.getName().equals("category"))
                currentCategory = Database.getCategoryByName(property.getValueString());
        }
    }

    public ArrayList<Product> filterProducts () {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : Database.getAllProducts()) {
            if (currentFilter.isValid(product))
                result.add(product);
        }
        return result;
    }

    public ArrayList<String> showAvailableSorts () {
        ArrayList<String> result = new ArrayList<>();
        result.add("time");
        result.add("score");
        result.add("viewed");
        return result;
    }

    public static void removeProduct (String productId) {
        Database.remove(Database.getProductById(productId));
    }

    public static void addFilter (String name, String value) {
        Property restriction = new Property();
        restriction.setName(name);
        if (value.matches("\\d+")) {
            restriction.setNumber(true);
            restriction.setValueLong(Long.parseLong(value));
        }else {
            restriction.setNumber(false);
            restriction.setValueString(value);
        }
        currentFilter.addRestriction(restriction);
    }

    public static void removeFilter (String property) {
        currentFilter.removeRestriction(property);
    }

    public static String getCurrentFilters () {
        ArrayList<String> result = new ArrayList<>();
        for (Property property : currentFilter.getProperties()) {
            result.add(property.toString());
        }
        return result.toString();
    }

    public static void setSort (String sortType) {
        for (Comparator<Product> productComparator : allComparators) {
            if (productComparator.getClass().getName().equals(sortType))
                comparator = productComparator;
        }
    }

    public static String getCurrentSort () {
        return comparator.getClass().getName();
    }

    public static ArrayList<String> getAttributes (Product product) {
        ArrayList<String> attributes = new ArrayList<>();
        for (Property property : product.getAllProperties()) {
            attributes.add(property.toString());
        }
        for (Property property : product.getAllSpecialProperties()) {
            attributes.add(property.toString());
        }
        return attributes;
    }

    public static String compareToProducts (Product first, Product second) {
        StringBuilder result = new StringBuilder();
        result.append(first.getName()).append("\t").append(second.getName()).append("\n");
        ArrayList<String> firstAttributes = getAttributes(first);
        ArrayList<String> secondAttributes = getAttributes(second);
        for (int i = 0 ; i < firstAttributes.size() ; i++) {
            result.append(firstAttributes.get(i)).append("\t").
                    append(secondAttributes.get(i)).append("\n");
        }
        return result.toString();
    }

    public String getCategories() {
        StringBuilder result = new StringBuilder();
        for (Category category : Database.getAllCategories()) {
            result.append(category.getName()).append("\n");
        }
        return result.toString();
    }
}
