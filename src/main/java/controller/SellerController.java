package controller;

import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerController extends UserController {
    public SellerController(User user, ProductController productController) {
        super(user, productController);
    }

    public String viewCompanyInfo() {
        return "This is the company information";
    }

    public ArrayList<String> viewSalesHistory() {
        ArrayList<String> res = new ArrayList<>();
        res.add("This a sale record");
        return res;
    }

    public String viewProducts() {
        return "This is all seller's products";
    }

    public String viewProduct(String productId) {
        return "This is the product number " + productId;
    }

    public String viewBuyers(String productId) {
        return "These are buyers of product " + productId;
    }

    public void editProduct(String productId, HashMap<String, String> fields) throws Exception {
        throw new Exception("product with productId: " + productId + " edited with fields " + fields);
    }

    public void addProduct(HashMap<String, String> fields) throws Exception {
        throw new Exception("new product with fields: " + fields);
    }

    public void deleteProduct(String productId) throws Exception {
        throw new Exception("product with productId: " + productId + " removed");
    }

    public String viewCategories() {
        return "These are all categories";
    }

    public String viewOffs() {
        return "These are all offs of this seller";
    }

    public String viewOff(String offId) {
        return "This is the Off number: " + offId;
    }

    public void editOff(String offId) throws Exception {
        throw new Exception("Editing the off number " + offId);
    }
}
