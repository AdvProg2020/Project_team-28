package controller;

import model.Category;
import model.Comment;
import model.Product;
import model.Seller;

import java.util.ArrayList;

public class ProductController extends UserController {
    private static Product currentProduct;
    public ProductController () {

    }
    public ProductController (Product currentProduct) {
        ProductController.currentProduct = currentProduct;
    }

    public static String showProduct(String productId) {
        Product product = Database.getProductById(productId);
        assert product != null;
        Seller seller = product.getSeller();
        assert seller != null;
        return "Product name:\t" + product.getName() + "\n" +
                "Brand:\t" + product.getBrand() + "\n" +
                "Price:\t" + product.getPrice() + "\n" +
                "Seller Company:\t" + seller.getCompanyName() + "\n" +
                "In stock:\t" + (product.isInStock() ? "Yes" : "No") + "\n" +
                "Average Score:\t" + product.getAverageScore() +
                "Description:\n" + product.getDescription();
    }

    public void addReview (String title, String text , boolean hasBought) {
        Comment thisComment = new Comment(UserController.getUser(), currentProduct, title, text, hasBought);
        Database.add(thisComment);
        currentProduct.addComment(thisComment);
    }

    public ArrayList<String> viewCategories () {
        ArrayList<Category> allCategories = Database.getAllCategories();
        ArrayList<String> categoryNames = new ArrayList<>();
        for (Category category : allCategories) {
            categoryNames.add(category.getName());
        }
        return categoryNames;
    }
}
