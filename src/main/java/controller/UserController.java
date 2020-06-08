package controller;

import model.*;
import model.exception.UserNotFoundException;

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
        if (thisUser == null)
            throw new UserNotFoundException();
        if (thisUser.validatePassword(password))
            userLoggedOn = thisUser;
        else
            throw new Exception("Wrong password");
    }

    public User getUser() {
        return userLoggedOn;
    }

    public void registerAccount(HashMap<String, String> data, boolean newManagerAllowed) throws Exception {
        if (Database.getUserByUsername(data.get("username")) != null)
            throw new Exception("Duplicated Username");
        if (data.get("credit").equals("")) {
            data.replace("credit", "0");
        }
        switch (data.get("type")) {
            case "Customer":
                Customer customer = new Customer(data.get("username"),
                        data.get("name"), data.get("surname"), data.get("email"), data.get("phoneNumber"),
                        data.get("password"), Long.parseLong(data.get("credit")));
                Database.add(customer);
                break;
            case "Manager":
                if (!newManagerAllowed && ManagerController.managerExists())
                    throw new Exception("You can't create a manager account");
                Manager manager = new Manager(data.get("username"),
                        data.get("name"), data.get("surname"), data.get("email"), data.get("phoneNumber"),
                        data.get("password"),Long.parseLong(data.get("credit")));
                Database.add(manager);
                break;
            case "Seller":
                Seller seller = new Seller(data.get("username"),
                        data.get("name"),data.get("surname"),data.get("email"),data.get("phoneNumber"),
                        data.get("password"),Long.parseLong(data.get("credit")),data.get("companyName"),data.get("companyInfo"));
                Database.add(seller);
                break;
            default:
                throw new Exception("Invalid type");
        }
    }

    public String getPersonalInfo() {
        return userLoggedOn.toString();
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
                case "address":
                    userLoggedOn.setAddress(value);
                    break;
                case "gender":
                    userLoggedOn.setGender(value);
                    break;
                case "birthDate":
                    userLoggedOn.setBirthDate(value);
                    break;
            }
        }
    }

    public void addReview(String title, String text) throws Exception{
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
