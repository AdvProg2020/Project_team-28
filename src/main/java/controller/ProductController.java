package controller;

import model.Product;
import model.Seller;

public class ProductController extends UserController {
    public static String showProduct(String productId) {
        Product product = Database.getProductById(productId);
        assert product != null;
        Seller seller = (Seller) Database.getUserById(product.getSeller().toString());
        assert seller != null;
        return "Product name:\t" + product.getName() + "\n" +
                "Brand:\t" + product.getBrand() + "\n" +
                "Price:\t" + product.getPrice() + "\n" +
                "Seller Company:\t" + seller.getCompanyName() + "\n" +
                "In stock:\t" + (product.isInStock() ? "Yes" : "No") + "\n" +
                "Average Score:\t" + product.getAverageScore() +
                "Description:\n" + product.getDescription();
    }

    //TODO complete function

    public String getCategories() {
        return "These are all categories";
    }

    public String showAvailableFilters() {
        return "These are all filters";
    }

    public String filterProducts(String filter) {
        return "These are products after being filtered";
    }

    public String getCurrentFilters() {
        return "These are current filters";
    }

    public void removeFilter(String filter) throws Exception {
        throw new Exception("willing to remove filter " + filter);
    }

    public String showAvailableSorts() {
        return "These are all sorts";
    }

    public void setSort(String sort) throws Exception {
        throw new Exception("sort is set to " + sort);
    }

    public String getCurrentSort() {
        return "This is the current sort";
    }
}
