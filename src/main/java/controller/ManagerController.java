package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import model.Discount;
import model.Manager;
import model.Off;
import model.User;
import model.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public String viewRequests() {
        StringBuilder res = new StringBuilder();
        for (JsonElement jsonElement : Database.getAllRequests()) {
            res.append(jsonElement.toString());
        }
        return res.toString();
    }

    public String viewRequest(String requestId) {
        return Database.getRequestById(requestId).toString();
    }

    public void evaluateRequest(String requestId, boolean isAccepted) throws Exception {
        //TODO not complete
        JsonElement jsonElement = Database.getRequestById(requestId);
        if (!isAccepted) {
            Database.remove(jsonElement);
        } else {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            switch (jsonElement.getAsJsonObject().get("request-type").getAsString()) {
                case "add off":
                    addOff(jsonElement);
                    break;
                case "edit off":
                    editOff(jsonElement);
                    break;
                case "add product":
                    break;
                case "edit product":
                    break;
                default:
            }
        }
    }

    private void addOff(JsonElement jsonElement) throws Exception {
        Gson gson = new Gson();
        Off off = gson.fromJson(jsonElement.getAsJsonObject().get("off").toString(), Off.class);
        Database.add(off);
    }

    private void editOff(JsonElement jsonElement) throws Exception {
        Gson gson = new Gson();
        Off off = Database.getOffById(jsonElement.getAsJsonObject().get("offId").getAsString());
        if (off == null) {
            throw new Exception("off not found");
        } else {
            if (!jsonElement.getAsJsonObject().get("offStatus").getAsString().equals("")) {
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
            if (!jsonElement.getAsJsonObject().get("currentSeller").getAsString().equals("")) {
                off.setSellerId(jsonElement.getAsJsonObject().get("currentSeller").getAsString());
            }
        }
    }

    public String viewCategories() {
        return "These are Categories";
    }

    public void editCategory(String category, HashMap<String, String> fields) {
        //TODO this should change into something functional for adding/removing properties
    }

    public void createCategory(HashMap<String, String> fields) {
    }

    public void removeCategory(String category) {
    }
}
