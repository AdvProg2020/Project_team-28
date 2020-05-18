package controller;

import com.google.gson.Gson;
import model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
    private static ArrayList<Off> allOffs = new ArrayList<>();


    public static void loadAllData() {
        makeDirectories();
        loadLists();
    }

    private static void makeDirectories() {
        makeDirectory(User.class);
        makeDirectory(Product.class);
        makeDirectory(Gson.class);
        makeDirectory(Discount.class);
        makeDirectory(Category.class);
        makeDirectory(Comment.class);
        makeDirectory(Property.class);
        makeDirectory(Score.class);
        makeDirectory(purchaseLog.class);
        makeDirectory(Off.class);
    }

    private static void loadLists() {
        loadList(allUsers, User.class);
        loadList(allProducts, Product.class);
        loadList(allRequests, Gson.class);
        loadList(allDiscountCodes, Discount.class);
        loadList(allCategories, Category.class);
        loadList(allComments, Comment.class);
        loadList(allProperties, Property.class);
        loadList(allScores, Score.class);
        loadList(allPurchaseLogs, purchaseLog.class);
        loadList(allOffs, Off.class);
    }

    private static <T> String getPath(Class<T> cls) {
        return "Database\\" + cls.getSimpleName() + "\\";
    }

    private static <T> void makeDirectory(Class<T> cls) {
        new File(getPath(cls)).mkdirs();
    }

    private static <T> void loadList (ArrayList<T> list, Class<T> cls) {
        for (final File fileEntry : new File(getPath(cls)).listFiles()) {
            try {
                Object object = new Gson().fromJson(new FileReader(fileEntry), cls);
                list.add(cls.cast(object));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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
    public static void add (Off off) {
        allOffs.add(off);
    }

    public static void remove (Product product) {
        allProducts.remove(product);
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
    public static Off getOffById (String id) {
        for (Off off : allOffs) {
            if (off.getId().equals(id))
                return off;
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

    public static User getUserByUsername(String username) {
        for (User user : allUsers) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static Category getCategoryByName (String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name))
                return category;
        }
        return null;
    }

    public static Discount getDiscountByCode (String code) {
        for (Discount discountCode : allDiscountCodes) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        return null;
    }

    public static Property getPropertyByName (String name) {
        for (Property property : allProperties) {
            if (property.getName().equals(name))
                return property;
        }
        return null;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void update(Object object) {

    }
}
