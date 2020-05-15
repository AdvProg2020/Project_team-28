package controller;

import model.Customer;
import model.Discount;
import model.Product;
import model.User;

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

    public boolean validateDiscountCode(String code) {
        Discount discount = Database.getDiscountByCode(code);
        if (discount != null) {
            return customerLoggedOn.hasDiscount(discount);
        }
        return false;
    }

    public String viewBalance() {
        //TODO complete function
        return "This is the balance";
    }

    public String viewDiscountCodes() {
        //TODO complete function
        return "These are your discount codes";
    }

    public String viewOrders() {
        //TODO complete function
        return "These are History of your orders";
    }

    public String showOrder(String orderId) {
        //TODO complete function
        return "This is the product number " + orderId;
    }

    public void rateProduct(String productId, String rate) throws Exception {
        //TODO complete function
        throw new Exception(productId + " rated " + rate);
    }

    public void addAddress(HashMap<String, String> fields) throws Exception {
        //TODO adding address information for purchasing
        throw new Exception("Address is " + fields);
    }

    public void useDiscuontCode(String discountCode) throws Exception {
        //TODO It's been checked and it just needs to be performed on the current cart
        throw new Exception("Using discount code " + discountCode);
    }

    public String getPaymentCheck() {
        //TODO finalizing the purchase
        return "purchase was failed :D";
    }
}
