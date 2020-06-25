package model;

import controller.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class Product {
    private enum productStatus {WaitingForProduction, WaitingForEdition, Confirmed}

    private productStatus status = productStatus.WaitingForProduction;
    private String name;
    private String brand;
    private long price;
    private ArrayList<String> sellers;
    private boolean inStock;
    private int quantity = 100;
    private String category;
    private String off;
    private String description;
    private int viewed = 0;
    private ArrayList<String> allScores;
    private ArrayList<String> allComments;
    private ArrayList<String> allProperties;
    private ArrayList<String> allSpecialProperties;
    private ArrayList<String> allBuyers;
    private String id;
    private String productImageAddress;

    public Product(String name, String brand, String price, String seller, String category) {
        allScores = new ArrayList<>();
        allComments = new ArrayList<>();
        allProperties = new ArrayList<>();
        allSpecialProperties = new ArrayList<>();
        allBuyers = new ArrayList<>();
        sellers = new ArrayList<>();
        this.id = UUID.randomUUID().toString();

        this.name = name;
        this.brand = brand;
        this.price = Long.parseLong(price);
        this.sellers.add(seller);
        this.category = Objects.requireNonNull(Database.getCategoryByName(category)).getId();
        this.productImageAddress = null;
    }

    public void setProductImageAddress(String imageUrl) {
        this.productImageAddress = imageUrl;
    }

    public String getProductImageAddress() {
        return productImageAddress;
    }

    public Image getProductImage() {
        if (productImageAddress == null) {
            try {
                productImageAddress = new File("src/main/resources/images/no-product.png").toURI().toURL().toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return new Image(productImageAddress);
    }

    public ImageView setImageView(ImageView imageView) {
        if (quantity <= 0) {
            ColorAdjust grayscale = new ColorAdjust();
            grayscale.setSaturation(-1);
            imageView.setEffect(grayscale);
        }
        imageView.setImage(getProductImage());
        return imageView;
    }

    public ArrayList<String> getAllScores() {
        return allScores;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity(int num) {
        this.quantity -= num;
    }

    public void increaseQuantity(int num) {
        this.quantity += num;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public ArrayList<Seller> getSellers() {
        ArrayList<Seller> allSellers = new ArrayList<>();
        for (String seller : this.sellers) {
            allSellers.add((Seller) Database.getUserById(seller));
        }
        return allSellers;
    }

    public void addViewed() {
        this.viewed += 1;
    }

    public void addBuyer(Customer customer) {
        allBuyers.add(customer.getId());
    }

    public void addSeller(User seller) {
        this.sellers.add(seller.getId());
    }

    public void addSpecialProperty(Property specialProperty) {
        allSpecialProperties.add(specialProperty.getId());
    }

    public int getViewed() {
        return viewed;
    }

    public ArrayList<Customer> getAllBuyers() {
        ArrayList<Customer> result = new ArrayList<>();
        for (String buyer : allBuyers) {
            result.add((Customer) Database.getUserById(buyer));
        }
        return result;
    }

    public Off getOff() {
        return Database.getOffById(off);
    }

    public void setOff(String off) {
        this.off = off;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(String status) {
        this.status = productStatus.valueOf(status);
    }

    public ObservableList<Property> getAllProperties() {
        ObservableList<Property> result = FXCollections.observableArrayList();
        for (String property : this.allProperties) {
            result.add(Database.getPropertyById(property));
        }
        return result;
    }

    public ObservableList<Property> getAllSpecialProperties() {
        ObservableList<Property> result = FXCollections.observableArrayList();
        for (String property : allSpecialProperties) {
            result.add(Database.getPropertyById(property));
        }
        return result;
    }

    public void setCategory(Category category) {
        this.category = category.getId();
        allSpecialProperties = new ArrayList<>();
        for (Property property : category.getSpecialProperties()) {
            Property clonedProperty = new Property(property);
            allSpecialProperties.add(clonedProperty.getId());
            Database.add(clonedProperty);
        }
    }

    public boolean isInStock() {
        return inStock;
    }

    public Category getCategory() {
        return Database.getCategoryById(this.category);
    }

    public String getDescription() {
        return description;
    }

    public double getAverageScore() {
        if (allScores.size() == 0)
            return 0;
        float averageResult = 0;
        for (String score : allScores)
            averageResult += Objects.requireNonNull(Database.getScoreById(score)).getScore();
        return averageResult / allScores.size();
    }

    public void addProperty(Property property) {
        allProperties.add(property.getId());
    }

    public void addScore(Score score) {
        allScores.add(score.getId());
    }

    public void addComment(Comment comment) {
        allComments.add(comment.getId());
    }

    public ArrayList<Comment> getAllComments() {
        ArrayList<Comment> result = new ArrayList<>();
        for (String comment : allComments) {
            result.add(Database.getCommentById(comment));
        }
        return result;
    }

    public boolean hasProperty(Property property) {
        String valueString = property.getValueString().toLowerCase();
        long valueLong = property.getValueLong();
        switch (property.getName()) {
            case "Name":
                return this.name.toLowerCase().contains(valueString);
            case "Min Price":
                return this.price >= valueLong;
            case "Max Price":
                return this.price <= valueLong;
            case "Brand":
                return this.brand.toLowerCase().contains(valueString);
            case "Seller":
                for (String sellerString : sellers) {
                    User seller = Database.getUserById(sellerString);
                    assert seller != null;
                    if (seller.getFullName().toLowerCase().contains(valueString)
                            || seller.getUsername().toLowerCase().contains(valueString)
                            || seller.getId().toLowerCase().contains(valueString))
                        return true;
                }
                return false;
            // following cases aren't handled in graphic:
            case "category":
                return this.category.equals(property.getValueString());
            case "inStock":
                return (this.inStock ? 1 : 0) == property.getValueLong();
            case "hasOff":
                return ((this.hasOff()) ? 1 : 0) == property.getValueLong();
        }
        for (String thisProperty : allProperties) {
            if (property.equals(Database.getPropertyById(thisProperty)))
                return true;
        }
        for (String specialProperty : allSpecialProperties) {
            if (property.equals(Database.getPropertyById(specialProperty)))
                return true;
        }
        return false;
    }

    public void setMainSeller(Seller seller) {
        this.sellers.add(0, seller.getId());
    }

    public long getPrice() {
        if (this.hasOff())
            return Long.max((price * (100 - this.getOff().getPercentage())) / 100, price - this.getOff().getMaxAmount());
        else
            return price;
    }

    public Property getSpecialPropertyByName(String name) {
        for (Property property : this.getAllSpecialProperties()) {
            if (property.getName().equals(name))
                return property;
        }
        return null;
    }

    public boolean hasOff() {
        return this.off != null;
    }

    public SellLog createSellLog() {
        SellLog log = new SellLog();
        Database.add(log);
        log.setSoldProduct(this);
        log.setDate(LocalDateTime.now());
        log.setAmountReceived(this.getPrice());
        log.setAmountReduced(this.price - this.getPrice());
        return log;
    }

    @Override
    public String toString() {
        return name + "\t" + brand;
    }
}