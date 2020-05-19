package controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class SellerController extends UserController {
    private Seller currentSeller;
    public SellerController(User user, ProductController productController) throws Exception{
        super(user, productController);
        if (user instanceof Seller)
            this.currentSeller = (Seller) user;
        else
            throw new Exception("User logged on is not a seller :|");
    }

    public String viewCompanyInfo() {
        return "company name: " + currentSeller.getCompanyName() + "\n"
                + currentSeller.getCompanyInfo();
    }

    public ArrayList<String> viewSalesHistory() {
        //format: logId     amount received     date
        ArrayList<String> result = new ArrayList<>();
        for (SellLog log : currentSeller.getSalesHistory()) {
            result.add(log.getId() + "\t" + log.getAmountReceived() + "\t" +log.getDate());
        }
        return result;
    }

    public String viewProducts() throws Exception{
        ArrayList<String> result = new ArrayList<>();
        for (Product product : currentSeller.getProductsToSell()) {
            result.add(product.getId() + "\t" + product.getName() + "\t" + product.getPrice());
        }
        return result.toString();
    }

    public String viewProduct(String productId) throws Exception{
        return productController.showProduct(productId);
    }

    public String viewBuyers(String productId) throws Exception{
        return Database.getProductById(productId).getAllBuyers().toString();
    }

    public void editProduct(String productId, HashMap<String, String> fields) throws Exception {
        Product product = new Product(fields.get("name"), fields.get("brand"), fields.get("price"), currentSeller.getId(), fields.get("category"));
        Gson request = new Gson();
        JsonElement jsonElement = request.toJsonTree(product);
        jsonElement.getAsJsonObject().addProperty("request-type", "edit product");
        jsonElement.getAsJsonObject().addProperty("productId", productId);
        jsonElement.getAsJsonObject().addProperty("id", UUID.randomUUID().toString());
        Database.add(jsonElement);
    }

    public void addProduct(HashMap<String, String> fields) throws Exception {
        Product product = new Product(fields.get("name"), fields.get("brand"), fields.get("price"), currentSeller.getId(), fields.get("category"));
        Gson request = new Gson();
        JsonElement jsonElement = request.toJsonTree(product);
        jsonElement.getAsJsonObject().addProperty("request-type", "add product");
        jsonElement.getAsJsonObject().addProperty("id", UUID.randomUUID().toString());
        Database.add(jsonElement);
    }

    public void deleteProduct(String productId) throws Exception {
        Database.remove(Database.getProductById(productId));
    }

    public String viewCategories() {
        return productController.getCategories();
    }

    public String viewOffs() {
        StringBuilder res = new StringBuilder();
        res.append("Offs of this seller:\n");
        for (Off off : Database.getAllOffs()) {
            if (off.getSellerId().equals(currentSeller.getId())) {
                res.append(off.toString()).append("\n");
            }
        }
        return res.toString();
    }

    public String viewOff(String offId) {
        Off off = Database.getOffById(offId);
        return off.toString();
    }

    public void editOff(HashMap<String, String> data) throws Exception {
        Gson request = new Gson();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("product ids", data.get("product ids"));
        jsonObject.addProperty("offStatus", data.get("offStatus"));
        jsonObject.addProperty("startTime", data.get("startTime"));
        jsonObject.addProperty("finishTime", data.get("finishTime"));
        jsonObject.addProperty("discountAmount", data.get("discountAmount"));
        jsonObject.addProperty("currentSeller", currentSeller.getId());
        jsonObject.addProperty("request-type", "edit off");
        jsonObject.addProperty("offId", data.get("offId"));
        jsonObject.addProperty("id", UUID.randomUUID().toString());
        Database.add(jsonObject);
    }

    public void addOff(HashMap<String, String> data) throws Exception {
        Off off = new Off(Arrays.asList(data.get("product ids").split("\\s*,\\s+")), data.get("offStatus"),
                data.get("startTime"), data.get("finishTime"), data.get("discountAmount"), currentSeller.getId());
        Gson request = new Gson();
        JsonElement jsonElement = request.toJsonTree(off);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("off", jsonElement);
        jsonObject.addProperty("request-type", "add off");
        jsonObject.getAsJsonObject().addProperty("id", UUID.randomUUID().toString());
        Database.add(jsonObject);
    }
}