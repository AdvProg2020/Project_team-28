package controller;

import model.Customer;
import model.Manager;
import model.Seller;
import model.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

public class UserController {
    protected static User userLoggedOn;

    public void loginUser(String username, String password) throws Exception{
        User thisUser = Database.getUserByUsername(username);
        if (thisUser == null)
            throw new Exception("Username not found");
        if (thisUser.validatePassword(password))
            userLoggedOn = thisUser;
        else
            throw new Exception("Wrong password");
    }

    public static User getUser() {
        return userLoggedOn;
    }

    public void registerAccount(HashMap<String, String> data) {
        switch (data.get("type")) {
            case "customer":
                Customer customer = new Customer(data.get("username"),
                        data.get("name"),data.get("surname"),data.get("email"),data.get("phoneNumber"),
                        data.get("password"),Long.parseLong(data.get("credit")));
                Database.add(customer);
                break;
            case "manager":
                Manager manager = new Manager(data.get("username"),
                        data.get("name"),data.get("surname"),data.get("email"),data.get("phoneNumber"),
                        data.get("password"),Long.parseLong(data.get("credit")));
                Database.add(manager);
                break;
            case "seller":
                Seller seller = new Seller(data.get("username"),
                        data.get("name"),data.get("surname"),data.get("email"),data.get("phoneNumber"),
                        data.get("password"),Long.parseLong(data.get("credit")),data.get("companyName"));
                Database.add(seller);
                break;

        }
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
