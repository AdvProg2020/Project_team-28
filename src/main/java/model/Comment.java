package model;

import controller.Database;

import java.util.UUID;

public class Comment {
    private String user;
    private String product;
    private String text;
    private enum CommentStatus {WaitingForConfirmation,Confirmed, RejectedByManager}
    CommentStatus status = CommentStatus.WaitingForConfirmation;
    private boolean bought;
    private String id;

    public Comment(User user, Product product, String text, boolean bought) {
        this.user = user.getId();
        this.product = product.getId();
        this.text = text;
        this.bought = bought;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return Database.getUserById(user);
    }

    public Product getProduct() {
        return Database.getProductById(product);
    }

    public String getText() {
        return text;
    }

    public boolean hasBought() {
        return bought;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(String status) {
        this.status = CommentStatus.valueOf(status);
    }

    @Override
    public String toString() {
        StringBuilder finalString = new StringBuilder();
        finalString.append(getUser().getFullName()).append(" said:\n").append(text).append("\n")
        .append("has bought this product: ");
        finalString.append(bought ? "yes" : "no");
        return finalString.toString();
    }
}
