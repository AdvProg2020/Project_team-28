package controller;

import com.google.gson.Gson;
import model.*;

import java.util.ArrayList;

public class Database { //jaaye in tu model nist? :thinking:
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static ArrayList<Product> allProducts = new ArrayList<>();
    private static ArrayList<Gson> allRequests = new ArrayList<>();
    private static ArrayList<Discount> allDiscountCodes = new ArrayList<>();
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private static ArrayList<Comment> allComments = new ArrayList<>();
    private static ArrayList<Property> allProperties = new ArrayList<>();
    private static ArrayList<Score> allScores = new ArrayList<>();
    private static ArrayList<purchaseLog> allPurchaseLogs = new ArrayList<>();

    public void loadAllData() {

    }
    public static void add (User user) {
        allUsers.add(user);
    }
    public static void add (Product product) {
        allProducts.add(product);
    }
    public static void add (Gson request) {
        allRequests.add(request);
    }
    public static void add (Discount discount) {
        allDiscountCodes.add(discount);
    }
    public static void add (Category category) {
        allCategories.add(category);
    }
    public static void add (Comment comment) {
        allComments.add(comment);
    }
    public static void add (Property property) {
        allProperties.add(property);
    }
    public static void add (Score score) {
        allScores.add(score);
    }
    public static void add (purchaseLog log) {
        allPurchaseLogs.add(log);
    }

    public static Product getProductById(String id) {
        for (Product product : allProducts) {
            if (product.getId().equals(id))
                return product;
        }
        return null;
    }
    public static User getUserById(String id) {
        for (User user : allUsers) {
            if (user.getId().equals(id))
                return user;
        }
        return null;
    }
    public static Gson getRequestById(String id) {
        return null;
    }
    public static Discount getDiscountById (String id) {
        for (Discount discountCode : allDiscountCodes) {
            if (discountCode.getId().equals(id))
                return discountCode;
        }
        return null;
    }
    public static Category getCategoryById (String id) {
        return null;
    }
    public static Comment getCommentById (String id) {
        return null;
    }
    public static Property getPropertyById (String id) {
        for (Property property : allProperties) {
            if (property.getId().equals(id))
                return property;
        }
        return null;
    }
    public static Score getScoreById (String id) {
        for (Score score : allScores) {
            if (score.getId().equals(id))
                return score;
        }
        return null;
    }
    public static purchaseLog getPurchaseLogById (String id) {
        for (purchaseLog log : allPurchaseLogs) {
            if (log.getId().equals(id))
                return log;
        }
        return null;
    }

    public User getUserByUsername(String username) {
        return null;
    }

    public static Discount getDiscountByCode (String code) {
        for (Discount discountCode : allDiscountCodes) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        return null;
    }

    public void update(Object object) {

    }
}
