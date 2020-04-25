package model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class SellLog {
    private LocalDateTime date;
    private long amountReceived;
    private long amountReduced;
    private HashMap<String, Integer> soldProducts;//<Product, Number>
    private String customer;
    private enum shippingStatus {A, B}
    private String id;

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

    public long getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(long amountReceived) {
        this.amountReceived = amountReceived;
    }

    public long getAmountReduced() {
        return amountReduced;
    }

    public void setAmountReduced(long amountReduced) {
        this.amountReduced = amountReduced;
    }

    public HashMap<Product, Integer> getSoldProducts() {
        return null;
    }

    public void setSoldProducts(HashMap<Product, Integer> soldProducts) {

    }

    public Customer getCustomer() {
        return null;
    }

    public void setCustomer(Customer customer) {

    }
}
