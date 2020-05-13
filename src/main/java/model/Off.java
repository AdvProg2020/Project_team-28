package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Off {
    private ArrayList<String> products;
    private enum offStatus {A, B}
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int discountAmount;
    private String id;

    public Off() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Product> getProducts() {
        return null;
    }

    public void setProducts(ArrayList<Product> products) {

    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }
}
