package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.*;
import model.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ManagerController extends UserController {
    private Discount selectedDiscount;
    private Gson selectedRequest;

    public ManagerController(User user, ProductController productController) {
        super(user, productController);
    }

    public static boolean managerExists() {
        for (User user : Database.getAllUsers()) {
            if (user.getClass() == Manager.class)
                return true;
        }
        return false;
    }

    public void createDiscount(HashMap<String, String> data) throws Exception {
        throw new Exception("got a discount code: " + data);
    }

    public void createDiscount(Discount discount) {
        Database.add(discount);
    }

    public String viewAllUsers() {
        ArrayList<String> result = new ArrayList<>();
        for (User user : Database.getAllUsers()) {
            result.add(user.getUsername());
        }
        return result.toString();
    }

    public String viewUser(String username) throws Exception {
        User user = Database.getUserByUsername(username);
        if (user == null)
            throw new UserNotFoundException();
        return user.toString();
    }

    public void deleteUser(String username) throws Exception {
        User user = Database.getUserByUsername(username);
        if (user == null)
            throw new UserNotFoundException();
        Database.remove(user);
    }

    public String viewDiscounts() {
        return "These are all discounts";
    }

    public String viewDiscount(String discountCode) {
        return "Viewing discount with code " + discountCode;
    }

    public String editDiscount(String discountCode, HashMap<String, String> fields) {
        return "Discount code " + discountCode + " has been edited to " + fields;
    }

    public void removeDiscount(String discountCode) {
    }

    public void removeProduct(String discountCode) {
    }

    public ArrayList<JsonObject> viewRequests() {
        return Database.getAllRequests();
    }

    public String viewRequest(String requestId) {
        return Database.getRequestById(requestId).toString();
    }

    public void evaluateRequest(String requestId, boolean isAccepted) throws Exception {
        //TODO not complete
        JsonElement jsonElement = Database.getRequestById(requestId);
        if (!isAccepted) {
            jsonElement.getAsJsonObject().addProperty("requestStatus", "rejected");
            Database.add(jsonElement.getAsJsonObject());
        } else {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            jsonElement.getAsJsonObject().addProperty("requestStatus", "accepted");
            switch (jsonElement.getAsJsonObject().get("request-type").getAsString()) {
                case "add off":
                    addOff(jsonElement);
                    break;
                case "edit off":
                    editOff(jsonElement);
                    break;
                case "add product":
                case "edit product":
                    Product product = new Gson().fromJson(jsonElement.getAsJsonObject().get("product").getAsJsonObject(), Product.class);
                    Category category = product.getCategory();
                    Database.add(product);
                    category.addProduct(product);
                    Database.update(category, category.getId());
                    break;
                case "remove product":
                    Database.remove(new Gson().fromJson(jsonElement.getAsJsonObject().get("product").getAsJsonObject(), Product.class));
                case "advertisement":
                    break;
                default:
            }
            Database.add(jsonElement.getAsJsonObject());
        }
    }

    private void addOff(JsonElement jsonElement) throws Exception {
        Off off = new Off();
        off.setProducts(Arrays.asList(jsonElement.getAsJsonObject().get("product ids").getAsString().split(",")));
        off.setOffStatus(jsonElement.getAsJsonObject().get("product ids").getAsString());
        off.setStartTime(LocalDateTime.parse(jsonElement.getAsJsonObject().get("startTime").getAsString()));
        off.setFinishTime(LocalDateTime.parse(jsonElement.getAsJsonObject().get("finishTime").getAsString()));
        off.setPercentage(Integer.parseInt(jsonElement.getAsJsonObject().get("percentage").getAsString()));
        off.setMaxAmount(Integer.parseInt(jsonElement.getAsJsonObject().get("maxAmount").getAsString()));
        off.setSellerId(jsonElement.getAsJsonObject().get("owner").getAsString());
        off.putInDuty();
        Database.add(off);
    }

    private void editOff(JsonElement jsonElement) throws Exception {
        Gson gson = new Gson();
        Off off = Database.getOffById(jsonElement.getAsJsonObject().get("offId").getAsString());
        if (off == null) {
            throw new Exception("off not found");
        }
            /*if (!jsonElement.getAsJsonObject().get("offStatus").getAsString().equals("")) {
                off.setOffStatus(jsonElement.getAsJsonObject().get("offStatus").getAsString());
            }
            if (!jsonElement.getAsJsonObject().get("startTime").getAsString().equals("")) {
                off.setStartTime(LocalDateTime.parse(jsonElement.getAsJsonObject().get("startTime").getAsString()));
            }
            if (!jsonElement.getAsJsonObject().get("finishTime").getAsString().equals("")) {
                off.setFinishTime(LocalDateTime.parse(jsonElement.getAsJsonObject().get("finishTime").getAsString()));
            }
            if (!jsonElement.getAsJsonObject().get("discountAmount").getAsString().equals("")) {
                off.setDiscountAmount(Integer.parseInt(jsonElement.getAsJsonObject().get("discountAmount").getAsString()));
            }
            if (!jsonElement.getAsJsonObject().get("owner").getAsString().equals("")) {
                off.setSellerId(jsonElement.getAsJsonObject().get("owner").getAsString());
            }*/
        off.setProducts(Arrays.asList(jsonElement.getAsJsonObject().get("product ids").getAsString().split(",")));
        off.setOffStatus(jsonElement.getAsJsonObject().get("product ids").getAsString());
        off.setStartTime(LocalDateTime.parse(jsonElement.getAsJsonObject().get("startTime").getAsString()));
        off.setFinishTime(LocalDateTime.parse(jsonElement.getAsJsonObject().get("finishTime").getAsString()));
        off.setPercentage(Integer.parseInt(jsonElement.getAsJsonObject().get("percentage").getAsString()));
        off.setMaxAmount(Integer.parseInt(jsonElement.getAsJsonObject().get("maxAmount").getAsString()));
        off.setSellerId(jsonElement.getAsJsonObject().get("owner").getAsString());

    }

    public String viewCategories() {
        return "These are Categories";
    }

    public void editCategory(String category, HashMap<String, String> fields) {
        // this should change into something functional for adding/removing properties
    }

    public void createCategory(HashMap<String, String> fields) {
        // what to do with arguments?
        Database.add(new Category(fields.get("name")));
    }

    public void removeCategory(String category) {
    }
}
