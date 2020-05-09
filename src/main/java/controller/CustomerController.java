package controller;

import model.Customer;
import model.Discount;
import model.Product;
import model.User;
import model.purchaseLog;

import java.time.LocalDateTime;
import java.util.HashMap;

public class CustomerController extends UserController {
    private Customer customerLoggedOn;

    public CustomerController(User user) {
        if (user instanceof Customer)
            this.customerLoggedOn = (Customer) user;
    }

    private String viewPersonalInfo() {
        return "Username: " + customerLoggedOn.getUsername() + "\n" +
                "Full name: " + customerLoggedOn.getFullName() + "\n" +
                "E-mail address: " + customerLoggedOn.getEmail() + "\n" +
                "Phone number: " + customerLoggedOn.getPhoneNumber() + "\n";
    }

    private void changePersonalInfo() {

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

    public boolean validateDiscountCode (String code) {
        Discount discount = Database.getDiscountByCode(code);
        if (discount != null) {
            return customerLoggedOn.hasDiscount(discount);
        }
        return false;
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
        //TODO: how to set seller?
        //log.setSeller();
        return log;
    }

}
