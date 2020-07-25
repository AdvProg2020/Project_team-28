package controller;

import com.google.gson.JsonObject;
import model.*;
import model.exception.DefaultUser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.Database.getJsonObjectFromReader;

public class CustomerController extends UserController {
    private Customer customerLoggedOn;

    public Customer getCustomerLoggedOn() {
        return customerLoggedOn;
    }

    public CustomerController(Customer user, ProductController productController) {
        super(user, productController);
        this.customerLoggedOn = user;
    }

    public static Customer newDefaultUser() {
        return new DefaultUser();
    }

    private String viewPersonalInfo() {
        return "Username: " + customerLoggedOn.getUsername() + "\n" +
                "Full name: " + customerLoggedOn.getFullName() + "\n" +
                "E-mail address: " + customerLoggedOn.getEmail() + "\n" +
                "Phone number: " + customerLoggedOn.getPhoneNumber() + "\n";
    }

    public void changePersonalInfo(HashMap<String, String> infoToSet) throws Exception {
        super.changePersonalInfo(infoToSet);
    }

    public void addAddress(HashMap<String, String> information) {
        customerLoggedOn.setAddress(information);
    }

    public String getPaymentCheck() {
        try {
            long totalPrice = purchase("e-wallet");
            return "Succesfully purchased\n" +
                    "Total price: " + totalPrice + "\n" +
                    "Your current credit: " + customerLoggedOn.getCredit();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String viewCartProducts() {
        StringBuilder stringToReturn = new StringBuilder();
        stringToReturn.append("Product ID\tProduct name\tUnit price\tNumber\n");
        HashMap<Product, Integer> customerCart = customerLoggedOn.getCart();
        for (Product product : customerCart.keySet()) {
            stringToReturn.append(product).append("\t").append(customerCart.get(product)).append("\n");
        }
        return stringToReturn.toString();
    }

    public String showProduct(String productId) throws Exception {
        if (customerLoggedOn.isProductInCart(productId)) {
            return productController.showProduct(productId);
        }
        return "Not a Valid Id";
    }

    public void addToCart() throws Exception {
        if (!productController.getCurrentProduct().isInStock())
            throw new Exception("Product is unavailable");
        String productId = productController.getCurrentProduct().getId();
        if (customerLoggedOn.isProductInCart(productId))
            customerLoggedOn.increaseNumberInCart(productId);
        else
            customerLoggedOn.addToCart(productId);
    }

    public void setQuantityInCart (Product product, int quantity) {
        customerLoggedOn.setQuantityInCart(product.getId(), quantity);
    }

    public int getQuantityInCart (Product product) {
        return customerLoggedOn.getProductQuantityInCart(product.getId());
    }

    public void addToCart(String productId) {
        if (customerLoggedOn.isProductInCart(productId))
            customerLoggedOn.increaseNumberInCart(productId);
        else
            customerLoggedOn.addToCart(productId);
    }

    public void removeFromCart(String productId) {
        customerLoggedOn.removeFromCart(productId);
    }

    public void decreaseNumberOfProduct(String productId) {
        int newNum = customerLoggedOn.decreaseNumberInCart(productId);
        if (newNum == 0)
            removeFromCart(productId);
    }

    public long getTotalPrice() {
        long totalPrice = 0;
        for (Product product : customerLoggedOn.getCart().keySet()) {
            totalPrice += product.getPrice() * customerLoggedOn.getCart().get(product);
        }
        if (customerLoggedOn.getDiscountUsed() != null)
            totalPrice *= (double) (100 - customerLoggedOn.getDiscountUsed().getDiscountPercent()) / 100;
        return totalPrice;
    }

    public long getProductPrice (Product product) {
        long totalPrice = 0;
        totalPrice += product.getPrice() * customerLoggedOn.getCart().get(product);
        if (customerLoggedOn.getDiscountUsed() != null)
            totalPrice *= (double) (100 - customerLoggedOn.getDiscountUsed().getDiscountPercent()) / 100;
        return totalPrice;
    }

    public boolean validateDiscountCode(String code) {
        Discount discount = Database.getDiscountByCode(code);
        if (discount != null) {
            return customerLoggedOn.hasDiscount(discount);
        }
        return false;
    }

    public void useDiscountCode(String code) throws Exception {
        Discount thisDiscount = Database.getDiscountByCode(code);
        if (thisDiscount == null)
            throw new Exception("Not a valid discount Code");
        customerLoggedOn.useDiscount(thisDiscount);
    }

    public void removeDiscountCode () {
        if (customerLoggedOn.getDiscountUsed() != null)
            customerLoggedOn.undoUseDiscount();
    }

    public long purchase(String method) throws Exception {
        long totalPrice = getTotalPrice();
        payEachSeller(method);
        //creating log
        PurchaseLog newLog = createPurchaseLog();
        createSellLogForAllProducts();
        customerLoggedOn.deleteDiscount();
        //should be changed?
        Database.add(newLog);
        customerLoggedOn.addToPurchaseHistory(newLog);
        this.addCustomerToProducts();
        customerLoggedOn.emptyCart();
        Database.update(customerLoggedOn, customerLoggedOn.getId());
        return totalPrice;
    }

    private void payEachSeller(String method) throws Exception {
        for (Product product : customerLoggedOn.getCart().keySet()) {
            String destId = product.getSellers().get(0).getId();
            String reply;
            if (method.equalsIgnoreCase("e-wallet"))
                reply = sendCreditPaymentRequest(getProductPrice(product), destId);
            else
                reply = sendBankPaymentRequest(getProductPrice(product), destId);
            System.out.println(product.getName() + ": " + reply);
        }
    }

    private String sendBankPaymentRequest(long productPrice, String destId) throws Exception {
        String url = Database.getServerUrl() + "/paySellerByBankAccount" + "?username=" + userLoggedOn.getUsername()
                + "&password=" + userLoggedOn.getPassword() + "&money=" + productPrice + "&sourceId="
                + userLoggedOn.getBankAccountId() + "&destId=" + destId;
        return sendRequestToServer(url);
    }

    private String sendCreditPaymentRequest(long productPrice, String destId) throws Exception {
        String url = Database.getServerUrl() + "/paySellerCredit" + "?sourceId=" + userLoggedOn.getId()
                + "&money=" + productPrice + "&destId=" + destId;
        return sendRequestToServer(url);
    }

    public void createSellLogForAllProducts() throws Exception {
        for (Product product : customerLoggedOn.getCart().keySet()) {
            SellLog log = product.createSellLog();
            log.setCustomer(customerLoggedOn);
            Database.update(log, log.getId());
            product.getSellers().get(0).addSellLog(log);
        }
    }

    public void addCustomerToProducts() {
        for (Product product : this.customerLoggedOn.getCart().keySet()) {
            product.addBuyer(this.customerLoggedOn);
        }
    }

    private PurchaseLog createPurchaseLog() {
        PurchaseLog log = new PurchaseLog();
        log.setDate(LocalDateTime.now());
        log.setAmountPaid(getTotalPrice());
        if (customerLoggedOn.getDiscountUsed() != null)
            log.setDiscount(customerLoggedOn.getDiscountUsed());
        log.setProducts(customerLoggedOn.getCart());
        return log;
    }

    public ArrayList<String> viewOrders() {
        ArrayList<String> result = new ArrayList<>();
        for (PurchaseLog log : customerLoggedOn.getPurchaseHistory()) {
            result.add(log.getId() + "\t" + log.getAmountPaid() + "\t" + log.getDate());
        }
        return result;
    }

    public String showOrder(String orderId) throws Exception {
        PurchaseLog thisLog = Database.getPurchaseLogById(orderId);
        if (thisLog != null)
            return thisLog.toString();
        else
            throw new Exception("Invalid log id");
    }

    public void rateProduct(String productId, String score) throws Exception {
        Score newScore = new Score(customerLoggedOn, Integer.parseInt(score), Database.getProductById(productId));
        Database.add(newScore);
        Database.getProductById(productId).addScore(newScore);
    }

    public String viewBalance() {
        return Long.toString(customerLoggedOn.getCredit());
    }

    public ArrayList<String> viewDiscountCodes() {
        ArrayList<String> result = new ArrayList<>();
        for (Discount code : customerLoggedOn.getDiscountCodes())
            result.add(code.getCode());
        return result;
    }
}
