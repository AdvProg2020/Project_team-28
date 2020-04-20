package model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class SellLog {
    private String logId;
    private LocalDateTime date;
    private long amountReceived;
    private long amountReduced;
    private HashMap<Product, Integer> soldProducts;
    private Customer customer;
    private enum shippingStatus {A, B}

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
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
        return soldProducts;
    }

    public void setSoldProducts(HashMap<Product, Integer> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
