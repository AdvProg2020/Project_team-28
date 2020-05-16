package controller;

import model.*;

import java.util.ArrayList;

public class ProductController extends UserController {
    private static Product currentProduct;
    private static Category currentCategory;
    private Filter currentFilter = new Filter();

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

    public String showAvailableFilters () {
        StringBuilder result = new StringBuilder();
        result.append("Name\nBrand\nIn stock\nPrice\n");
        for (Property property : currentCategory.getSpecialProperties()) {
            result.append(property.getName()).append("\n");
        }
        return result.toString();
    }

    public ArrayList<Product> filterProducts () {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : Database.getAllProducts()) {
            if (validateCategory(product) && currentFilter.isValid(product))
                result.add(product);
        }
        return result;
    }

    public boolean validateCategory (Product product) {
        return currentCategory.equals(product.getCategory());
    }
}
