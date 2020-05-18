package controller;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;

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