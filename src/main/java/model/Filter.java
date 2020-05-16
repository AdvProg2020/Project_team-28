package model;


import java.util.ArrayList;
import java.util.UUID;

public class Filter {
    private ArrayList<Property> properties;
    private String filteredName;
    private boolean inStock;
    private String brand;
    private long maxPrice;
    private long minPrice;
    private boolean isPriceFiltered = false;
    private String id;

    public Filter() {
        this.id = UUID.randomUUID().toString();
    }

    public boolean isValid(Product product) {
        boolean result = validateName(product);
        result = result && validateProperties(product);
        result = result && validateInStock(product) && validatePrice(product);
        result = result && validateBrand(product);
        return result;
    }

    public boolean validateProperties (Product product) {
        boolean result = true;
        for (Property property : properties) {
            if (!product.hasProperty(property)) {
                result = false;
                break;
            }
        }
        return result;
    }


    public boolean validateName (Product product) {
        if (this.filteredName != null)
            return product.getName().contains(filteredName);
        return true;
    }

    public boolean validateBrand (Product product) {
        if (this.brand != null)
            return product.getBrand().equals(brand);
        return true;
    }

    public boolean validateInStock (Product product) {
        if (inStock)
            return product.isInStock();
        return true;
    }

    public boolean validatePrice (Product product) {
        if (isPriceFiltered) {
            return (product.getPrice() >= this.minPrice) && (product.getPrice() <= this.maxPrice);
        }
        return true;
    }

    public void addRestriction(String property, boolean isNumber) {
        this.properties.add(Property.createPropertyFromString(property, isNumber));
    }

    public void addRestriction (String name, String brand) {
        this.filteredName = name;
        this.brand = brand;
    }

    public void addRestriction (boolean inStock) {
        this.inStock = true;
    }

    public void addRestriction (long minPrice, long maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        isPriceFiltered = true;
    }

}
