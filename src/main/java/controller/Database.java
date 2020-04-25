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

    public static Product getProductById(String id) {
        return null;
    }

    public static User getUserById(String id) {
        return null;
    }
    public static Gson getRequestById(String id) {
        return null;
    }
    public static Discount getDiscountById (String id) {
        return null;
    }
    public Category getCategoryById (String id) {
        return null;
    }
    public Comment getCommentById (String id) {
        return null;
    }

    public User getUserByUsername(String username) {
        return null;
    }

    public void update(Object object) {

    }
}
