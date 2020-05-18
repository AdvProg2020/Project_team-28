package controller;

import model.Product;
import model.SellLog;
import model.Seller;
import model.User;

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

    public void editOff(HashMap<String, String> data) throws Exception {
        throw new Exception("Editing the off " + data);
    }

    public void addOff(HashMap<String, String> data) throws Exception {
        throw new Exception("Adding off: " + data);
    }
}