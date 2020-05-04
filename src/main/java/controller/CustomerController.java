package controller;

import model.Customer;
import model.Discount;
import model.Product;

import java.util.HashMap;

public class CustomerController extends UserController {

    private String viewPersonalInfo() {
        return "Username: " + userLoggedOn.getUsername() + "\n" +
                "Full name: " + userLoggedOn.getFullName() + "\n" +
                "E-mail address: " + userLoggedOn.getEmail() + "\n" +
                "Phone number: " + userLoggedOn.getPhoneNumber() + "\n";
    }

    private void changePersonalInfo() {

    }

    public String viewCartProducts() {
        StringBuilder stringToReturn = new StringBuilder();
        stringToReturn.append("Product ID\tProduct name\tUnit price\tNumber\n");
        if (userLoggedOn instanceof Customer) {
            HashMap<Product, Integer> customerCart = ((Customer) userLoggedOn).getCart();
            for (Product product : customerCart.keySet()) {
                stringToReturn.append(product).append("\t").append(customerCart.get(product)).append("\n");
            }
        }
        return stringToReturn.toString();
    }

    public String showProduct(String productId) {
        if (userLoggedOn instanceof Customer && ((Customer) userLoggedOn).isProductInCart(productId)) {
            return ProductController.showProduct(productId);
        }
        return "Not a Valid Id";
    }

    public void addToCart(String productId) {
        if (userLoggedOn instanceof Customer) {
            if (((Customer) userLoggedOn).isProductInCart(productId))
                ((Customer) userLoggedOn).increaseNumberInCart(productId);
            else
                ((Customer) userLoggedOn).addToCart(productId);
        }
    }

    public void removeFromCart(String productId) {
        if (userLoggedOn instanceof Customer) {
            ((Customer) userLoggedOn).removeFromCart(productId);
        }
    }

    public void decreaseNumberOfProduct (String productId) {
        if (userLoggedOn instanceof Customer) {
            int newNum = ((Customer) userLoggedOn).decreaseNumberInCart(productId);
            if (newNum == 0)
                removeFromCart(productId);
        }
    }

    public long getTotalPrice() {
        long totalPrice = 0;
        if (userLoggedOn instanceof Customer) {
            for (Product product : ((Customer) userLoggedOn).getCart().keySet()) {
                totalPrice += product.getPrice()*((Customer) userLoggedOn).getCart().get(product);
            }
        }
        return totalPrice;
    }

    public boolean validateDiscountCode (String code) {
        Discount discount = Database.getDiscountByCode(code);
        if (userLoggedOn instanceof Customer && discount != null) {
            return ((Customer) userLoggedOn).hasDiscount(discount);
        }
        return false;
    }

}
