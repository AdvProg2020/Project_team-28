package controller;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomerController extends UserController {
    private Customer customerLoggedOn;

    public CustomerController(User user, ProductController productController) throws Exception {
        super(user, productController);
        if (user instanceof Customer)
            this.customerLoggedOn = (Customer) user;
        else
            throw new Exception("User logged on is not a customer :|");
    }

    public static Customer newDefaultUser() {
        Customer defaultCustomer = new Customer("","Anonymous User","",
                "username@example.com","+98xxxxxxxxxx","",0);
        defaultCustomer.setUsername(defaultCustomer.getId());
        return defaultCustomer;
    }

    private String viewPersonalInfo() {
        return "Username: " + customerLoggedOn.getUsername() + "\n" +
                "Full name: " + customerLoggedOn.getFullName() + "\n" +
                "E-mail address: " + customerLoggedOn.getEmail() + "\n" +
                "Phone number: " + customerLoggedOn.getPhoneNumber() + "\n";
    }

    public void changePersonalInfo(HashMap<String, String> infoToSet) {
        super.changePersonalInfo(infoToSet);
    }

    public void addAddress(HashMap<String, String> information) {
        customerLoggedOn.setAddress(information);
    }

    public String getPaymentCheck() {
        return "";
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

    public String showProduct(String productId) {
        if (customerLoggedOn.isProductInCart(productId)) {
            return productController.showProduct(productId);
        }
        return "Not a Valid Id";
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

    public void decreaseNumberOfProduct (String productId) {
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
            totalPrice *= (double) (100 - customerLoggedOn.getDiscountUsed().getDiscountPercent()) /100;
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
        if (customerLoggedOn.hasDiscount(thisDiscount))
            customerLoggedOn.useDiscount(thisDiscount);
    }

    public void purchase() {
        customerLoggedOn.payCredit(getTotalPrice());
        PurchaseLog newLog = createPurchaseLog();
        Database.add(newLog);
        customerLoggedOn.addToPurchaseHistory(newLog);
        customerLoggedOn.emptyCart();
    }

    private PurchaseLog createPurchaseLog () {
        PurchaseLog log = new PurchaseLog();
        log.setDate(LocalDateTime.now());
        log.setAmountPaid(getTotalPrice());
        log.setProducts(customerLoggedOn.getCart());
        return log;
    }

    public ArrayList<String> viewOrders () {
        //format: logId     amount paid     date
        ArrayList<String> result = new ArrayList<>();
        for (PurchaseLog log : customerLoggedOn.getPurchaseHistory()) {
            result.add(log.getId() + "\t" + log.getAmountPaid() + "\t" + log.getDate());
        }
        return result;
    }

    public String showOrder(String orderId) {
        PurchaseLog thisLog = Database.getPurchaseLogById(orderId);
        if (thisLog != null)
            return thisLog.toString();
        else
            return "Not a valid id";
    }

    public void rateProduct (String productId, String score) {
        Score newScore = new Score(customerLoggedOn, Integer.parseInt(score), Database.getProductById(productId));
        Database.add(newScore);
        Database.getProductById(productId).addScore(newScore);
    }

    public String viewBalance () {
        return Long.toString(customerLoggedOn.getCredit());
    }

    public ArrayList<String> viewDiscountCodes () {
        ArrayList<String> result = new ArrayList<>();
        for (Discount code : customerLoggedOn.getDiscountCodes())
            result.add(code.getCode());
        return result;
    }

}
