package controller;

import model.*;

import java.util.ArrayList;
import java.util.Comparator;

public class ProductController {
    private Product currentProduct;
    private Category currentCategory;
    private Comparator<Product> comparator;
    private ArrayList<Comparator<Product>> allComparators;
    private Filter currentFilter = new Filter();

    public ProductController () {
        this.allComparators = new ArrayList<>();
        this.allComparators.add(new sortByViewed());
        this.allComparators.add(new sortByTime());
        this.allComparators.add(new sortByScore());
    }
    public ProductController (Product currentProduct) {
        this.currentProduct = currentProduct;
        this.allComparators = new ArrayList<>();
        this.allComparators.add(new sortByViewed());
        this.allComparators.add(new sortByTime());
        this.allComparators.add(new sortByScore());
    }

    public String showProduct(String productId) throws Exception{
        Product product = Database.getProductById(productId);
        assert product != null;
        product.addViewed();
        Seller seller = product.getSellers().get(0);
        assert seller != null;
        return "Product name:\t" + product.getName() + "\n" +
                "Price:\t" + product.getPrice() + "\n" +
                "Seller Company:\t" + seller.getCompanyName() + "\n" +
                "Average Score:\t" + product.getAverageScore() +
                "Description:\n" + product.getDescription();
    }

    public String showProducts () {
        StringBuilder result = new StringBuilder();
        result.append("id\tname\tseller\tprice\n");
        for (Product product : Database.getAllProducts()) {
            result.append(product.toString()).append("\n");
        }
        return result.toString();
    }

    public String digest() throws Exception{
        return showProduct(this.currentProduct.getId());
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public String showAvailableFilters () {
        setCategoryForFilter();
        StringBuilder result = new StringBuilder();
        result.append("Name\nBrand\nIn stock\nPrice\n");
        for (Property property : currentCategory.getSpecialProperties()) {
            result.append(property.getName()).append("\n");
        }
        return result.toString();
    }

    public void setCategoryForFilter () {
        for (Property property : currentFilter.getProperties()) {
            if (property.getName().equals("category"))
                currentCategory = Database.getCategoryByName(property.getValueString());
        }
    }

    public ArrayList<Product> filterProducts () {
        ArrayList<Product> result = new ArrayList<>();
        for (Product product : Database.getAllProducts()) {
            if (currentFilter.isValid(product))
                result.add(product);
        }
        return result;
    }

    public ArrayList<String> showAvailableSorts () {
        ArrayList<String> result = new ArrayList<>();
        result.add("time");
        result.add("score");
        result.add("viewed");
        return result;
    }

    public void removeProduct (String productId) throws Exception {
        Database.remove(Database.getProductById(productId));
    }

    public void addFilter (String name, String value) {
        Property restriction = new Property();
        restriction.setName(name);
        if (value.matches("\\d+")) {
            restriction.setNumber(true);
            restriction.setValueLong(Long.parseLong(value));
        }else {
            restriction.setNumber(false);
            restriction.setValueString(value);
        }
        currentFilter.addRestriction(restriction);
    }

    public void removeFilter (String property) {
        currentFilter.removeRestriction(property);
    }

    public String getCurrentFilters () {
        ArrayList<String> result = new ArrayList<>();
        for (Property property : currentFilter.getProperties()) {
            result.add(property.toString());
        }
        return result.toString();
    }

    public void setSort (String sortType) {
        for (Comparator<Product> productComparator : allComparators) {
            if (productComparator.getClass().getName().equals(sortType))
                comparator = productComparator;
        }
    }

    public String getCurrentSort () {
        return this.comparator.getClass().getName();
    }

    public ArrayList<String> getAttributes (Product product) {
        ArrayList<String> attributes = new ArrayList<>();
        for (Property property : product.getAllProperties()) {
            attributes.add(property.toString());
        }
        for (Property property : product.getAllSpecialProperties()) {
            attributes.add(property.toString());
        }
        return attributes;
    }

    public String compareToProducts(String productId) throws Exception{
        Product first = getCurrentProduct();
        Product second = Database.getProductById(productId);
        StringBuilder result = new StringBuilder();
        result.append(first.getName()).append("\t").append(second.getName()).append("\n");
        ArrayList<String> firstAttributes = getAttributes(first);
        ArrayList<String> secondAttributes = getAttributes(second);
        for (int i = 0; i < firstAttributes.size(); i++) {
            result.append(firstAttributes.get(i)).append("\t").
                    append(secondAttributes.get(i)).append("\n");
        }
        return result.toString();
    }

    public String getCategories() {
        StringBuilder result = new StringBuilder();
        for (Category category : Database.getAllCategories()) {
            result.append(category.getName()).append("\n");
        }
        return result.toString();
    }

    public void setProductSeller(String sellerName) throws Exception {
        if (Database.getUserByUsername(sellerName) == null)
            throw new Exception("Username not found");
        this.currentProduct.setMainSeller((Seller) Database.getUserByUsername(sellerName));
    }

    public ArrayList<String> viewAttributes() {
        return getAttributes(currentProduct);
    }

    public String getReviews() {
        return "These are reviews of the current product";
    }
}
