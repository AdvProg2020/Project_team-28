package model;

public class Score {
    private User user;
    private int score;
    private Product product;

    public Score(User user, int score, Product product) {
        this.user = user;
        this.score = score;
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    public Product getProduct() {
        return product;
    }
}
