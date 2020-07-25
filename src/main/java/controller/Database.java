package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Database {
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String serverUrl = "http://localhost:8080/";
    private static final NetworkArray<User> allUsers = new NetworkArray<>(User.class);
    private static final NetworkArray<Product> allProducts = new NetworkArray<>(Product.class);
    private static final NetworkArray<Request> allRequests = new NetworkArray<>(Request.class);
    private static final NetworkArray<Discount> allDiscountCodes = new NetworkArray<>(Discount.class);
    private static final NetworkArray<Category> allCategories = new NetworkArray<>(Category.class);
    private static final NetworkArray<Comment> allComments = new NetworkArray<>(Comment.class);
    private static final NetworkArray<Property> allProperties = new NetworkArray<>(Property.class);
    private static final NetworkArray<Score> allScores = new NetworkArray<>(Score.class);
    private static final NetworkArray<PurchaseLog> allPurchaseLogs = new NetworkArray<>(PurchaseLog.class);
    private static final NetworkArray<SellLog> allSellLogs = new NetworkArray<>(SellLog.class);
    private static final NetworkArray<Off> allOffs = new NetworkArray<>(Off.class);
    private static final NetworkArray<Product> allProductAds = new NetworkArray<>(Product.class);
    private static final NetworkArray<PossibleManager> allPossibleManagers = new NetworkArray<>(PossibleManager.class);
    private static final NetworkArray<PossibleSupporter> allPossibleSupporters = new NetworkArray<>(PossibleManager.class);

    private static String token = "random-customer";

    public static void loadAllData() throws Exception {
        makeDirectories();
        loadLists();
    }

    private static void makeDirectories() {
        makeDirectory(Manager.class);
        makeDirectory(Supporter.class);
        makeDirectory(Seller.class);
        makeDirectory(Customer.class);
        makeDirectory(Product.class);
        makeDirectory(Request.class);
        makeDirectory(Discount.class);
        makeDirectory(Category.class);
        makeDirectory(Comment.class);
        makeDirectory(Property.class);
        makeDirectory(Score.class);
        makeDirectory(PurchaseLog.class);
        makeDirectory(SellLog.class);
        makeDirectory(Off.class);
        makeDirectory("ProductAd");
        makeDirectory(PossibleManager.class);
        makeDirectory("PossibleSupporter");
    }

    private static void loadLists() throws Exception {
        loadList(allUsers, Manager.class);
        loadList(allUsers, Supporter.class);
        loadList(allUsers, Seller.class);
        loadList(allUsers, Customer.class);
        loadList(allProducts, Product.class);
        loadList(allRequests, Request.class);
        loadList(allDiscountCodes, Discount.class);
        loadList(allCategories, Category.class);
        loadList(allComments, Comment.class);
        loadList(allProperties, Property.class);
        loadList(allScores, Score.class);
        loadList(allPurchaseLogs, PurchaseLog.class);
        loadList(allSellLogs, SellLog.class);
        loadList(allOffs, Off.class);
        loadList(allProductAds, Product.class, "ProductAd");
        loadList(allPossibleManagers, PossibleManager.class);
        loadList(allPossibleSupporters, PossibleSupporter.class);
    }

    private static <T> String getPath(String folderName) {
        return "Database\\" + folderName + "\\";
    }

    private static String getPath(Object object) {
        return "Database\\" + object.getClass().getSimpleName() + "\\";
    }

    private static <T> void makeDirectory(Class<T> cls) {
        makeDirectory(cls.getSimpleName());
    }

    private static <T> void makeDirectory(String folderName) {
        new File(getPath(folderName)).mkdirs();
    }

    private static <T extends BaseModel> void loadList(ArrayList<T> list, Class<? extends T> cls) throws Exception {
        loadList(list, cls, cls.getSimpleName());
    }

    private static <T extends BaseModel> void loadList(ArrayList<T> list, Class<? extends T> cls, String folderName) throws Exception {
        ((NetworkArray<T>) list).load();
        for (final File fileEntry : new File(getPath(folderName)).listFiles()) {
            try {
                FileReader fileReader = new FileReader(fileEntry);
                Object object = new Gson().fromJson(fileReader, cls);
                list.add(cls.cast(object));
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Object getObjectFromNetwork(String objectId, Class<Object> objectClass) throws Exception {
        JsonObject jsonObject = sendGET("resource", objectClass.getSimpleName(), objectId);
        return new Gson().fromJson(jsonObject.get("object"), Class.forName("model." + jsonObject.get("className").getAsString()));
    }

    public static JsonObject sendGET(String url, String objectClass, String objectId) throws Exception {
        return sendGET(url + "?className=" + objectClass + "&objectId=" + objectId);
    }

    public static JsonObject sendGET(String url) throws Exception {
        url = serverUrl + url;
        System.out.println(url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("AuthToken", token);
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        System.out.println(" Response Body : " + con.getResponseMessage());
        JsonObject convertedObject = getJsonObjectFromReader(con, responseCode);

        token = convertedObject.get("token").getAsString();

        return convertedObject;
    }

    public static JsonObject sendGETall(Class className) throws Exception {
        return sendGET("allResources?className=" + className.getSimpleName());
    }


    private static void sendPost(String url, String objectClass, String objectId, Object object) throws Exception {
        url = serverUrl + url;
        System.out.println(url + objectClass + objectId);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("AuthToken", token);
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("className", objectClass);
            jsonObject.addProperty("objectId", objectId);
            jsonObject.addProperty("token", token);
            jsonObject.add("object", new Gson().toJsonTree(object).getAsJsonObject());
            String jsonInputString = new Gson().toJson(jsonObject);
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        System.out.println(" Response Body : " + con.getResponseMessage());
        JsonObject convertedObject = getJsonObjectFromReader(con, responseCode);

        token = convertedObject.get("token").getAsString();
    }

    public static void sendPOSTsellerPort(int port) throws Exception {
        String url = serverUrl + "p2p";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("AuthToken", token);
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", token);
            jsonObject.addProperty("port", Integer.toString(port));
            String jsonInputString = new Gson().toJson(jsonObject);
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        System.out.println(" Response Body : " + con.getResponseMessage());
        JsonObject convertedObject = getJsonObjectFromReader(con, responseCode);

        token = convertedObject.get("token").getAsString();
    }

    public static JsonObject getJsonObjectFromReader(HttpURLConnection con, int responseCode) throws Exception {
        BufferedReader in;
        if (responseCode >= 200 && responseCode < 300) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
            in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String json = getStringFromReader(in);
            JsonObject convertedObject = new Gson().fromJson(json, JsonObject.class);
            con.disconnect();
            throw new Exception("Network Error: " + convertedObject.get("error").getAsString());
        }
        String json = getStringFromReader(in);
        con.disconnect();
        return new Gson().fromJson(json, JsonObject.class);
    }


    private static String getStringFromReader(BufferedReader in) throws IOException {
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }


    static void writeObject(Object object, String id) throws Exception {
        writeObject(object, id, object.getClass().getSimpleName());
    }

    static void writeObject(Object object, String id, String folderName) throws Exception {
        sendPost("resource", folderName, id, object);

        String fileName = getPath(folderName) + id + ".json";
        FileWriter writer;
        try {
            writer = new FileWriter(fileName);
            new GsonBuilder().setPrettyPrinting().create().toJson(object, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteObject(Object object, String id) {
        deleteObject(id, object.getClass().getSimpleName());
    }

    private static void deleteObject(String id, String folderName) {
        try {
            File file = new File(getPath(folderName) + id + ".json");
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addPossibleManager(String username) throws Exception {
        PossibleManager possibleManager = new PossibleManager(username);
        if (!allPossibleManagers.contains(possibleManager)) {
            allPossibleManagers.add(new PossibleManager(username));
        }
        writeObject(possibleManager, possibleManager.getId());
    }

    public static void addPossibleSupporter(String username) throws Exception {
        PossibleSupporter possibleSupporter = new PossibleSupporter(username);
        if (!allPossibleSupporters.contains(possibleSupporter)) {
            allPossibleSupporters.add(possibleSupporter);
        }
        writeObject(possibleSupporter, possibleSupporter.getId());
    }

    public static void add(User user) throws Exception {
        allUsers.add(user);
        writeObject(user, user.getId());
    }

    public static void add(Product product) throws Exception {
        for (Product productIn : allProducts) {
            if (productIn.equals(product.getId())) {
                allProducts.remove(productIn);
            }
        }
        allProducts.add(product);
        writeObject(product, product.getId());
    }

    public static void add(JsonObject request) throws Exception {
        Request req = new Request(request);
        allRequests.add(req);
        writeObject(req, req.getId());
    }

    public static void add(Discount discount) throws Exception {
        allDiscountCodes.add(discount);
        writeObject(discount, discount.getId());
    }

    public static void add(Category category) throws Exception {
        allCategories.add(category);
        writeObject(category, category.getId());
    }

    public static void add(Comment comment) throws Exception {
        allComments.add(comment);
        writeObject(comment, comment.getId());
    }

    public static void add(Property property) throws Exception {
        allProperties.add(property);
        writeObject(property, property.getId());
    }

    public static void add(Score score) throws Exception {
        allScores.add(score);
        writeObject(score, score.getId());
    }

    public static void add(PurchaseLog log) throws Exception {
        allPurchaseLogs.add(log);
        writeObject(log, log.getId());
    }

    public static void add(SellLog log) throws Exception {
        allSellLogs.add(log);
        writeObject(log, log.getId());
    }

    public static void add(Off off) throws Exception {
        allOffs.add(off);
        off.putInDuty();
        writeObject(off, off.getId());
    }

    public static void addProductToAds(Product product) throws Exception {
        allProductAds.add(product);
        writeObject(product, product.getId(), "ProductAd");
    }

    public static void remove(User user) {
        allUsers.remove(user);
        deleteObject(user, user.getId());
    }

    public static void remove(Product product) throws Exception {
        product = getProductById(product.getId());
        allProducts.remove(product);
        deleteObject(product, product.getId());
    }

    public static void remove(JsonElement jsonElement) {
        allRequests.remove(jsonElement);
        deleteObject(jsonElement, jsonElement.getAsJsonObject().get("id").getAsString());
    }

    public static void removePossibleManager(String username) {
        allPossibleManagers.remove(username);
        deleteObject(username, username);
    }

    public static void removeProductFromAds(Product product) {
        allProductAds.remove(product);
        deleteObject(product.getId(), "ProductAd");
    }

    public static Product getProductById(String id) throws Exception {
        try {
            System.out.println("get product by id");
            JsonObject jsonObject = sendGET("resource", "Product", id);
            return new Gson().fromJson(jsonObject.get("object"), Product.class);
        } catch (Exception e) {
            e.printStackTrace();
            for (Product product : allProducts) {
                if (product.getId().equals(id))
                    return product;
            }
            throw new Exception("product id not found");
        }
    }

    public static User getUserById(String id) {
        try {
            JsonObject jsonObject = sendGET("resource", "User", id);
            return (User) new Gson().fromJson(jsonObject.get("object"), Class.forName("model." + jsonObject.get("className").getAsString()));
        } catch (Exception e) {
            e.printStackTrace();
            for (User user : allUsers) {
                if (user.getId().equals(id))
                    return user;
            }
            return null;
        }
    }

    public static JsonElement getRequestById(String id) {
        for (Request request : allRequests) {
            JsonElement jsonElement = request.getJsonObject();
            if (jsonElement.getAsJsonObject().get("id").getAsString().equals(id)) {
                return jsonElement;
            }
        }
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
        for (Category category : allCategories) {
            if (category.getId().equals(id))
                return category;
        }
        return null;
    }

    public static Comment getCommentById(String id) {
        for (Comment comment : allComments) {
            if (comment.getId().equals(id))
                return comment;
        }
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

    public static SellLog getSellLogById(String id) {
        for (SellLog log : allSellLogs) {
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

    public static ArrayList<PossibleManager> getAllPossibleManagers() {
        return allPossibleManagers;
    }

    public static ArrayList<PossibleSupporter> getAllPossibleSupporters() {
        return allPossibleSupporters;
    }

    public static ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public static ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public static ArrayList<Product> getAllProductAds() {
        return allProductAds;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static ArrayList<Request> getAllRequests() {
        return allRequests;
    }

    public static ArrayList<Discount> getAllDiscountCodes() {
        return allDiscountCodes;
    }

    public static ArrayList<Comment> getAllComments() {
        return allComments;
    }

    public static ArrayList<Property> getAllProperties() {
        return allProperties;
    }

    public static ArrayList<Score> getAllScores() {
        return allScores;
    }

    public static ArrayList<PurchaseLog> getAllPurchaseLogs() {
        return allPurchaseLogs;
    }

    public static ArrayList<SellLog> getAllSellLogs() {
        return allSellLogs;
    }

    public static String getServerUrl() {
        return serverUrl;
    }

    public static String getUserAgent() {
        return USER_AGENT;
    }

    public static ArrayList<Off> getAllOffs() {
        return allOffs;
    }

    public static void update(Object object, String id) throws Exception {
        writeObject(object, id);
    }

    public static void remove(Category category) {
        if (category.getParentCategory() != null) {
            getCategoryById(category.getParentCategory()).getSubCategories().remove(category.getId());
        }
        allCategories.remove(category);
        deleteObject(category, category.getId());
    }

    public static void login(String username, String password) throws Exception {
        String url = serverUrl + "login?" + "username=" + username + "&password=" + password;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("charset", "utf-8");
        con.setUseCaches(false);

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        System.out.println(" Response Body : " + con.getResponseMessage());
        JsonObject convertedObject = getJsonObjectFromReader(con, responseCode);

        token = convertedObject.get("token").getAsString();
    }

    public static void logout() throws Exception {
        String url = serverUrl + "logout";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        String urlParameters = "token=" + token;
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        con.setDoOutput(true);
        con.setInstanceFollowRedirects(false);
        con.setRequestMethod("DELETE");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("charset", "utf-8");
        con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        con.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.write(postData);
        }

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        System.out.println(" Response Body : " + con.getResponseMessage());
        JsonObject convertedObject = getJsonObjectFromReader(con, responseCode);

        token = "random-customer";
    }
}
