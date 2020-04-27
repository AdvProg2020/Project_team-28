package controller;

import model.User;

import java.util.ArrayList;

public class UserController {
    protected User userLoggedOn;

    public void registerAccount(ArrayList<String> data) throws Exception {
        throw new Exception("Registering with " + data);
    }

    public void loginUser(String username, String password) {
        System.out.println("Logging in with " + username + " and " + password);
    }

    public User getUser() {
        return userLoggedOn;
    }
}
