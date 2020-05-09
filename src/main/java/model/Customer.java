package model;

import controller.Database;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User {
    private HashMap<String, Integer> cart = new HashMap<>(); //<productId, Number>
    private ArrayList<String> purchaseHistory;
    private ArrayList<String> discountCodes;

    public Customer(String username, String name, String surname, String email, String phoneNumber, String password, long credit) {
        super(username, name, surname, email, phoneNumber, password, credit);
    }

    @Override
    public String getType() {
        return super.getType();
    }

    public boolean hasDiscount (Discount discount) {
        return discountCodes.contains(discount.getId());
    }

    public void addToPurchaseHistory(purchaseLog purchaseLog) {
        purchaseHistory.add(purchaseLog.getId());
    }

    public void addToCart(String productId) {
        cart.put(productId, 1);
    }

    public void increaseNumberInCart (String productId) {
        if (isProductInCart(productId)) {
            cart.put(productId, cart.get(productId) + 1);
        }
    }

    public int decreaseNumberInCart (String productId) {
        if (isProductInCart(productId)) {
            cart.put(productId, cart.get(productId) - 1);
        }
        return cart.get(productId);
    }

    public void removeFromCart (String productId) {
        if (isProductInCart(productId)) {
            cart.remove(productId);
        }
    }

    public void emptyCart () {
        this.cart = new HashMap<>();
    }

    public HashMap<Product, Integer> getCart() {
        HashMap<Product, Integer> finalCart = new HashMap<>();
        for (String item : cart.keySet()) {
            finalCart.put(Database.getProductById(item), cart.get(item));
        }
        return finalCart;
    }

    public boolean isProductInCart(String productId) {
        return this.cart.containsKey(productId);
    }

    public void payCredit (long cost) {
        this.setCredit(this.getCredit() - cost);
    }
}
