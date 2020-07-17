package controller;

import model.*;
import model.exception.DefaultUser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class CustomerController extends UserController {
    private Customer customerLoggedOn;

    public Customer getCustomerLoggedOn() {
        return customerLoggedOn;
    }

    public CustomerController(Customer user, ProductController productController) throws IOException {
        super(user, productController);
        this.customerLoggedOn = user;
    }

    public static Customer newDefaultUser() throws IOException {
        return new DefaultUser();
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
        //phase 1!
//        try {
//            long totalPrice = purchase();
//            return "Succesfully purchased\n" +
//                    "Total price: " + totalPrice + "\n" +
//                    "Your current credit: " + customerLoggedOn.getCredit();
//        } catch (Exception e) {
//            return e.getMessage();
//        }
        return null;
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

    public long purchase(String paymentMethod) throws Exception {
        long totalPrice = getTotalPrice();
        if (paymentMethod.equalsIgnoreCase("e-wallet")) {
            customerLoggedOn.payCredit(totalPrice);
            payEachSellerInCredit();
        }else if (paymentMethod.equalsIgnoreCase("bank account")) {
            payViaBankAccount(totalPrice);
        }
        PurchaseLog newLog = createPurchaseLog();
        createSellLogForAllProducts();
        customerLoggedOn.deleteDiscount();
        Database.add(newLog);
        customerLoggedOn.addToPurchaseHistory(newLog);
        this.addCustomerToProducts();
        customerLoggedOn.emptyCart();
        Database.update(customerLoggedOn, customerLoggedOn.getId());
        return totalPrice;
    }

    private void payViaBankAccount(long totalPrice) throws IOException {
        BankAPI api = new BankAPI();
        String token = api.getToken(customerLoggedOn.getUsername(), customerLoggedOn.getPassword());
        String result = api.moveBalance(token, totalPrice, customerLoggedOn.getBankAccountId(),
                Database.getShopBankAccountId());
        System.out.println(result);
        payEachSellerInBank();
    }

    private void payEachSellerInCredit() {
        for (Product product : customerLoggedOn.getCart().keySet()) {
            Seller seller = product.getSellers().get(0);
            seller.addToCredit(product.getPrice()*(1 - ManagerController.getWagePercent()));
            Database.update(seller, seller.getId());
        }
    }

    private void payEachSellerInBank () {
        //each seller should be paid
    }

    public void createSellLogForAllProducts() {
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

    private PurchaseLog createPurchaseLog() throws Exception {
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
