package controller;

import com.google.gson.Gson;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class Database { //jaaye in tu model nist? :thinking:
    private static ArrayList<User> allUsers = new ArrayList<>();
    private static ArrayList<Product> allProducts = new ArrayList<>();
    private static ArrayList<Gson> allRequests = new ArrayList<>();
    private static ArrayList<Discount> allDiscountCodes = new ArrayList<>();
    private static ArrayList<Category> allCategories = new ArrayList<>();
    private static ArrayList<Comment> allComments = new ArrayList<>();
    private static ArrayList<Property> allProperties = new ArrayList<>();
    private static ArrayList<Score> allScores = new ArrayList<>();
    private static ArrayList<PurchaseLog> allPurchaseLogs = new ArrayList<>();
    private static ArrayList<SellLog> allSellLogs = new ArrayList<>();
    private static ArrayList<Off> allOffs = new ArrayList<>();


    public static void loadAllData() {
        makeDirectories();
        loadLists();
    }

    private static void makeDirectories() {
        makeDirectory(Manager.class);
        makeDirectory(Seller.class);
        makeDirectory(Customer.class);
        makeDirectory(Product.class);
        makeDirectory(Gson.class);
        makeDirectory(Discount.class);
        makeDirectory(Category.class);
        makeDirectory(Comment.class);
        makeDirectory(Property.class);
        makeDirectory(Score.class);
        makeDirectory(PurchaseLog.class);
        makeDirectory(Off.class);
    }

    private static void loadLists() {
        loadList(allUsers, Manager.class);
        loadList(allUsers, Seller.class);
        loadList(allUsers, Customer.class);
        loadList(allProducts, Product.class);
        loadList(allRequests, Gson.class);
        loadList(allDiscountCodes, Discount.class);
        loadList(allCategories, Category.class);
        loadList(allComments, Comment.class);
        loadList(allProperties, Property.class);
        loadList(allScores, Score.class);
        loadList(allPurchaseLogs, PurchaseLog.class);
        loadList(allOffs, Off.class);
    }

    private static <T> String getPath(Class<T> cls) {
        return "Database\\" + cls.getSimpleName() + "\\";
    }

    private static <T> String getPath(Object object) {
        return "Database\\" + object.getClass().getSimpleName() + "\\";
    }

    private static <T> void makeDirectory(Class<T> cls) {
        new File(getPath(cls)).mkdirs();
    }

    private static <T> void loadList(ArrayList<T> list, Class<? extends T> cls) {
        for (final File fileEntry : new File(getPath(cls)).listFiles()) {
            try {
                Object object = new Gson().fromJson(new FileReader(fileEntry), cls);
                list.add(cls.cast(object));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeObject(Object object, String id) {
        String fileName = getPath(object) + id + ".json";
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName);
            new Gson().toJson(object, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void add(User user) {
        allUsers.add(user);
        writeObject(user, user.getId());
    }

    public static void add(Product product) {
        allProducts.add(product);
        writeObject(product, product.getId());
    }

    public static void add(Gson request) {
        allRequests.add(request);
        writeObject(request, UUID.randomUUID().toString());
    }

    public static void add(Discount discount) {
        allDiscountCodes.add(discount);
        writeObject(discount, discount.getId());
    }

    public static void add(Category category) {
        allCategories.add(category);
        writeObject(category, category.getId());
    }

    public static void add(Comment comment) {
        allComments.add(comment);
        writeObject(comment, comment.getId());
    }

    public static void add(Property property) {
        allProperties.add(property);
        writeObject(property, property.getId());
    }

    public static void add(Score score) {
        allScores.add(score);
        writeObject(score, score.getId());
    }

    public static void add(PurchaseLog log) {
        allPurchaseLogs.add(log);
        writeObject(log, log.getId());
    }

    public static void add (SellLog log) {
        allSellLogs.add(log);
        writeObject(log, log.getId());
    }

    public static void add(Off off) {
        allOffs.add(off);
        writeObject(off, off.getId());
    }

    public static void remove(Product product) {
        allProducts.remove(product);
        writeObject(product, product.getId());
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

    public static Discount getDiscountById(String id) {
        for (Discount discountCode : allDiscountCodes) {
            if (discountCode.getId().equals(id))
                return discountCode;
        }
        return null;
    }

    public static Category getCategoryById(String id) {
        return null;
    }

    public static Comment getCommentById(String id) {
        return null;
    }

    public static Property getPropertyById(String id) {
        for (Property property : allProperties) {
            if (property.getId().equals(id))
                return property;
        }
        return null;
    }

    public static Score getScoreById(String id) {
        for (Score score : allScores) {
            if (score.getId().equals(id))
                return score;
        }
        return null;
    }

    public static Off getOffById(String id) {
        for (Off off : allOffs) {
            if (off.getId().equals(id))
                return off;
        }
        return null;
    }

    public static PurchaseLog getPurchaseLogById(String id) {
        for (PurchaseLog log : allPurchaseLogs) {
            if (log.getId().equals(id))
                return log;
        }
        return null;
    }

    public static SellLog getSellLogById (String id) {
        for (SellLog log : allSellLogs) {
            if (log.getId().equals(id))
                return log;
        }
        return null;
    }

    public static User getUserByUsername(String username) throws Exception{
        for (User user : allUsers) {
            if (user.getUsername().equals(username))
                return user;
        }
        throw new Exception("Username not found");
    }

    public static Category getCategoryByName(String name) {
        for (Category category : allCategories) {
            if (category.getName().equals(name))
                return category;
        }
        return null;
    }

    public static Discount getDiscountByCode(String code) {
        for (Discount discountCode : allDiscountCodes) {
            if (discountCode.getCode().equals(code))
                return discountCode;
        }
        return null;
    }

    public static Property getPropertyByName(String name) {
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
