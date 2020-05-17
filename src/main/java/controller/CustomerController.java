package controller;

import model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.HashMap;

public class CustomerController extends UserController {
    private Customer customerLoggedOn;

    public CustomerController(User user) throws Exception {
        if (user instanceof Customer)
            this.customerLoggedOn = (Customer) user;
        else
            throw new Exception("User logged on is not a customer :|");
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
            return ProductController.showProduct(productId);
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

    public void useDiscuontCode(String discountCode) throws Exception {
        //TODO It's been checked and it just needs to be performed on the current cart
        throw new Exception("Using discount code " + discountCode);
    }

    public String getPaymentCheck() {
        //TODO finalizing the purchase
        return "purchase was failed :D";
    }
    public void purchase() {
        customerLoggedOn.payCredit(getTotalPrice());
        purchaseLog newLog = createPurchaseLog();
        Database.add(newLog);
        customerLoggedOn.addToPurchaseHistory(newLog);
        customerLoggedOn.emptyCart();

    }

    private purchaseLog createPurchaseLog () {
        purchaseLog log = new purchaseLog();
        log.setDate(LocalDateTime.now());
        log.setAmountPaid(getTotalPrice());
        log.setProducts(customerLoggedOn.getCart());
        return log;
    }

    public String viewOrders () {
        StringBuilder stringToReturn = new StringBuilder();
        stringToReturn.append("Order ID\tAmount paid\tDate\n");
        for (purchaseLog log : customerLoggedOn.getPurchaseHistory()) {
            stringToReturn.append(log.getId()).append("\t")
                    .append(log.getAmountPaid()).append("\t")
                    .append(log.getDate()).append("\n");
        }
        return stringToReturn.toString();
    }

    public String showOrder(String orderId) {
        purchaseLog thisLog = Database.getPurchaseLogById(orderId);
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
