package controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import model.Discount;
import model.User;

import java.util.HashMap;

public class ManagerController extends UserController {
    private Discount selectedDiscount;
    private Gson selectedRequest;

    public ManagerController(User user, ProductController productController) {
        super(user, productController);
    }

    public void createDiscount(HashMap<String, String> data) throws Exception {
        throw new Exception("got a discount code: " + data);
    }

    public String viewAllUsers() {
        return "Here are the Users";
    }

    public String viewUser(String username) {
        return "This is your user " + username;
    }

    public void deleteUser(String username) throws Exception {
        throw new Exception("deleted user: " + username);
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

    public void evaluateRequest(String requestId, boolean isAccepted) {
        //not complete
        JsonElement jsonElement = Database.getRequestById(requestId);
        if (!isAccepted) {
            Database.remove(jsonElement);
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
