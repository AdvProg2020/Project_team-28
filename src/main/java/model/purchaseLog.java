package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class purchaseLog {
    private LocalDateTime date;
    private long amountPaid;
    private String discount;
    private HashMap<String, Integer> products;//<Product, Number>
    private String seller;
    private enum deliveryStatus {A,B}
    private String id;

    public purchaseLog() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(long amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Discount getDiscount() {
        return null;
    }

    public void setDiscount(Discount discount) {

    }

    public HashMap<Product, Integer> getProducts() {
        return null;
    }

    public void setProducts(HashMap<Product, Integer> products) {
    }

    public Seller getSeller() {
        return null;
    }

    public void setSeller(Seller seller) {
    }
}
