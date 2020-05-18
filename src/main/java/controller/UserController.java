package controller;

import model.Comment;
import model.Customer;
import model.User;

import java.util.HashMap;

public class UserController {
    protected User userLoggedOn;
    protected ProductController productController;

    public UserController(User userLoggedOn, ProductController productController) {
        if (userLoggedOn == null) {
            userLoggedOn = CustomerController.newDefaultUser();
        }
        this.userLoggedOn = userLoggedOn;
        this.productController = productController;
    }

    public void loginUser(String username, String password) throws Exception{
        User thisUser = Database.getUserByUsername(username);
        if (thisUser.validatePassword(password))
            userLoggedOn = thisUser;
        else
            throw new Exception("Wrong password");
    }

    public User getUser() {
        return userLoggedOn;
    }

    public void registerAccount(HashMap<String, String> data) throws Exception {
        Database.getUserByUsername(data.get("username"));
        throw new Exception("Duplicated Username");
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

    public void addReview(String title, String text) {
        boolean hasBought;
        if (userLoggedOn instanceof Customer)
            hasBought = ((Customer) userLoggedOn).hasBoughtProduct(productController.getCurrentProduct());
        else
            hasBought = false;
        Comment thisComment = new Comment(this.userLoggedOn, productController.getCurrentProduct(),
                title, text, hasBought);
        Database.add(thisComment);
        productController.getCurrentProduct().addComment(thisComment);
    }
}
