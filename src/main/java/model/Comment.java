package model;

public class Comment {
    private User user;
    private Product product;
    private String text;
    private enum commentStatus {A,B}
    private boolean hasBought;

    public Comment(User user, Product product, String text, boolean hasBought) {
        this.user = user;
        this.product = product;
        this.text = text;
        this.hasBought = hasBought;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public String getText() {
        return text;
    }

    public boolean isHasBought() {
        return hasBought;
    }
}
