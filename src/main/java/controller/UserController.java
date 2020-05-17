package controller;

import model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserController {
    protected static User userLoggedOn;

    public void loginUser(String username, String password) {
        System.out.println("Logging in with " + username + " and " + password);
    }

    public static User getUser() {
        return userLoggedOn;
    }

    public void registerAccount(HashMap<String, String> data) throws Exception{
        throw new Exception("Registering with " + data);
    }

    public String getPersonalInfo() {
        return "You are what you are";
    }

    public void changePersonalInfo(HashMap<String, String> infoToSet) {
        for (String info : infoToSet.keySet()) {
            String value = infoToSet.get(info);
            switch (info) {
                case "name":
                    userLoggedOn.setName(value);
                    break;
                case "surname":
                    userLoggedOn.setSurname(value);
                    break;
                case "email":
                    userLoggedOn.setEmail(value);
                    break;
                case "phoneNumber":
                    userLoggedOn.setPhoneNumber(value);
                    break;
                case "password":
                    userLoggedOn.setPassword(value);
                    break;
                case "username":
                    userLoggedOn.setUsername(value);
                    break;
            }
        }
    }
}
