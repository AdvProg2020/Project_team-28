package controller;

import com.google.gson.Gson;
import model.Discount;
import model.User;

import java.util.HashMap;

public class ManagerController extends UserController {
    private Discount selectedDiscount;
    private Gson selectedRequest;

    public ManagerController(User user) {
        super();
        userLoggedOn = user;
    }

    public void createDiscount(HashMap<String, String> data) throws Exception {
        throw new Exception("got a discount code: " + data);
    }

    public String viewAllUsers() {
        return "Here are the Users";
    }

    public String viewUser(String username) {
        return "This is your user " + username;
    }

    public void deleteUser(String username) throws Exception {
        throw new Exception("deleted user: " + username);
    }
}
