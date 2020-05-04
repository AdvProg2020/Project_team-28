package controller;

import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserController {
    protected User userLoggedOn;

    public void loginUser(String username, String password) {
        System.out.println("Logging in with " + username + " and " + password);
    }

    public User getUser() {
        return userLoggedOn;
    }

    public void registerAccount(HashMap<String, String> data) throws Exception{
        throw new Exception("Registering with " + data);
    }

    public String getPersonalInfo() {
        return "You are what you are";
    }

    public void changePersonalInfo(String field, String value) throws Exception {
        throw new Exception(field + "changed to" + value);
    }
}
