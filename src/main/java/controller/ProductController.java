package controller;

import model.Product;
import model.Seller;

public class ProductController extends UserController {
    private static Product currentProduct;
    public ProductController () {

    }
    public ProductController (Product currentProduct) {
        this.currentProduct = currentProduct;
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

}
