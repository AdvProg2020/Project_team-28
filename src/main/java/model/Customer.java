package model;

import controller.Database;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User {
    private HashMap<String, String> address;
    private HashMap<String, Integer> cart = new HashMap<>(); //<productId, Number>
    private Discount discountUsed;
    private ArrayList<String> purchaseHistory;
    private ArrayList<String> discountCodes;

    public Customer(String username, String name, String surname, String email, String phoneNumber, String password, long credit) {
        super(username, name, surname, email, phoneNumber, password, credit);
    }

    @Override
    public String getType() {
        return "customer";
    }

    public Discount getDiscountUsed() {
        return discountUsed;
    }

    public void setAddress(HashMap<String, String> address) {
        this.address = address;
    }

    public boolean hasDiscount (Discount discount) {
        return discountCodes.contains(discount.getId());
    }

    public void addToPurchaseHistory(PurchaseLog purchaseLog) {
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

    public void useDiscount (Discount discount) throws Exception{
        discount.useCode();
        discountUsed = discount;
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

    public ArrayList<PurchaseLog> getPurchaseHistory() {
        ArrayList<PurchaseLog> purchaseLogs = new ArrayList<>();
        for (String log : this.purchaseHistory) {
            purchaseLogs.add(Database.getPurchaseLogById(log));
        }
        return purchaseLogs;
    }

    public ArrayList<Discount> getDiscountCodes() {
        ArrayList<Discount> toReturn = new ArrayList<>();
        for (String code : discountCodes) {
            toReturn.add(Database.getDiscountById(code));
        }
        return toReturn;
    }
}
