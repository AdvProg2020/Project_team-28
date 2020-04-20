package controller;

import com.google.gson.Gson;
import model.Discount;
import model.Product;
import model.User;

import java.util.ArrayList;

public class Database { //jaaye in tu model nist? :thinking:
    private ArrayList<User> allUsers;
    private ArrayList<Product> allProducts;
    private ArrayList<Gson> allRequests;
    private ArrayList<Discount> allDiscountCodes;

    public void loadAllData() {

    }

    public Product getProductById(String id) {
        return null;
    }

    public User getUserByUsername(String username) {
        return null;
    }

    public void update(Object object) {

    }
}
