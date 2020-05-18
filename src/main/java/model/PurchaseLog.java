package model;

import controller.Database;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PurchaseLog {
    private LocalDateTime date;
    private long amountPaid;
    private String discount;
    private HashMap<String, Integer> products;//<Product, Number>
    private enum deliveryStatus {InStock, Sent, Delivered}
    deliveryStatus status = deliveryStatus.InStock;
    private String id;

    public PurchaseLog() {
        this.products = new HashMap<>();
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
        HashMap<Product, Integer> toReturn = new HashMap<>();
        for (String product : products.keySet()) {
            toReturn.put(Database.getProductById(product), products.get(product) );
        }
        return toReturn;
    }

    public void setProducts(HashMap<Product, Integer> products) {
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(String status) {
        this.status = deliveryStatus.valueOf(status);
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder();
        HashMap<Product, Integer> allProducts = this.getProducts();
        toReturn.append("Log id: ").append(this.id).append("\n")
                .append("Date and time: ").append(this.date).append("\n")
                .append("List of Products:").append("\n")
                .append("Product Id\tProduct name\tSeller\tPrice\tNumber").append("\n");
        for (Product product : allProducts.keySet()) {
            toReturn.append(product).append("\t").append(allProducts.get(product)).append("\n");
        }
        toReturn.append("Total price: ").append(this.amountPaid).append("\n")
                .append("Delivery status: ").append(status.toString());
        return toReturn.toString();
    }
}
