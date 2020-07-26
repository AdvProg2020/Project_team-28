package controller;

import com.google.gson.JsonObject;
import model.*;
import model.exception.UserNotFoundException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import static controller.Database.getJsonObjectFromReader;

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

    public void loginUser(String username, String password) throws Exception {
        Database.login(username, password);
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
                        data.get("password"), Long.parseLong(data.get("credit")));
                Database.add(manager);
                break;
            case "Supporter":
                if (canBeSupporter(data.get("username")))
                    throw new Exception("You can't create a supporter account");
                Supporter supporter = new Supporter(data.get("username"),
                        data.get("name"), data.get("surname"), data.get("email"), data.get("phoneNumber"),
                        data.get("password"), Long.parseLong(data.get("credit")));
                Database.add(supporter);
                break;

            case "Seller":
                Seller seller = new Seller(data.get("username"),
                        data.get("name"), data.get("surname"), data.get("email"), data.get("phoneNumber"),
                        data.get("password"), Long.parseLong(data.get("credit")), data.get("companyName"), data.get("companyInfo"));
                Database.add(seller);
                break;
            default:
                throw new Exception("Invalid type");
        }
    }

    public String getPersonalInfo(String field) {
        return getString(field, userLoggedOn);
    }

    public String getPersonalInfo(String username, String field) {
        User user = Database.getUserByUsername(username);
        return getString(field, user);
    }

    private String getString(String field, User user) {
        try {
            switch (field) {
                case "type":
                    return user.getType();
                case "firstName":
                    return user.getFirstName();
                case "surname":
                    return user.getSurname();
                case "username":
                    return user.getUsername();
                case "password":
                    return user.getPassword();
                case "gender":
                    return user.getGender();
                case "birthDate":
                    return user.getBirthDate();
                case "email":
                    return user.getEmail();
                case "phoneNumber":
                    return user.getPhoneNumber();
                case "address":
                    return user.getAddress();
                case "credit":
                    return user.getCredit().toString();
                case "profilePictureAddress":
                    return user.getProfilePictureAddress();
                case "companyName":
                    return ((Seller) user).getCompanyName();
                case "companyInfo":
                    return ((Seller) user).getCompanyInfo();
                default:
                    return "not found";
            }
        } catch (Exception e) {
            System.err.println(e);
            return "";
        }
    }

    public void changePersonalInfo(String field, String newValue) throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(field, newValue);
        changePersonalInfo(hashMap);
    }

    public void changePersonalInfo(String username, String field, String newValue) throws Exception {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(field, newValue);
        changePersonalInfo(username, hashMap);
    }

    public void changePersonalInfo(String username, HashMap<String, String> infoToSet) throws Exception {
        User user = Database.getUserByUsername(username);
        for (String info : infoToSet.keySet()) {
            String value = infoToSet.get(info);
            switch (info) {
                case "name":
                    user.setName(value);
                    break;
                case "surname":
                    user.setSurname(value);
                    break;
                case "email":
                    user.setEmail(value);
                    break;
                case "phoneNumber":
                    user.setPhoneNumber(value);
                    break;
                case "password":
                    user.setPassword(value);
                    break;
                case "username":
                    user.setUsername(value);
                    break;
                case "address":
                    user.setAddress(value);
                    break;
                case "gender":
                    user.setGender(value);
                    break;
                case "birthDate":
                    user.setBirthDate(value);
                    break;
                case "credit":
                    user.setCredit(Long.parseLong(value));
                    break;
                case "profilePictureAddress":
                    user.setProfilePictureAddress(value);
                    break;
            }
        }
        Database.writeObject(user, user.getId());
    }

    public void changePersonalInfo(HashMap<String, String> infoToSet) throws Exception {
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
                case "credit":
                    userLoggedOn.setCredit(Long.parseLong(value));
                    break;
                case "profilePictureAddress":
                    userLoggedOn.setProfilePictureAddress(value);
                    break;
            }
        }
        Database.writeObject(userLoggedOn, userLoggedOn.getId());
    }

    public void addReview(String title, String text) throws Exception {
        Comment thisComment = new Comment(null, this.userLoggedOn,
                productController.getCurrentProduct(),
                title, text);
        Database.add(thisComment);
        productController.getCurrentProduct().addComment(thisComment);
    }

    public void addManager(String username) throws Exception {
        User thisUser = Database.getUserByUsername(username);
        if (thisUser != null)
            throw new Exception("User already exists");
        Database.addPossibleManager(username);
    }

    public void addSupporter(String username) throws Exception {
        User thisUser = Database.getUserByUsername(username);
        if (thisUser != null)
            throw new Exception("User already exists");
        Database.addPossibleSupporter(username);
    }

    public boolean canBeManager(String username) {
        return Database.getAllPossibleManagers().contains(username);
    }

    public boolean canBeSupporter(String username) {
        return Database.getAllPossibleManagers().contains(username);
    }

    public void logout() throws Exception {
        Database.logout();
        userLoggedOn = CustomerController.newDefaultUser();
    }

    public List<User> getAllUsers() {
        return Database.getAllUsers();
    }

    public void removeUser(String username) {
        Database.remove(Database.getUserByUsername(username));
    }

    public void removeProduct(String productId) throws Exception {
        Database.remove(Database.getProductById(productId));
    }

    public String getAddress() {
        return userLoggedOn.getAddress();
    }

    protected String sendRequestToServer(String url) throws Exception {
        System.out.println(url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", Database.getUserAgent());
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        System.out.println(" Response Body : " + con.getResponseMessage());
        JsonObject convertedObject = getJsonObjectFromReader(con, responseCode);
        if (convertedObject.get("ok").getAsString().equalsIgnoreCase("false"))
            throw new Exception(convertedObject.get("error").getAsString());
        return convertedObject.get("reply").getAsString();
    }

    public String depositCredit(long money) throws Exception {
        String url = Database.getServerUrl() + "/makeTransaction" + "?username=" + userLoggedOn.getUsername()
                + "&password=" + userLoggedOn.getPassword() + "&type=deposit" + "&money=" + money + "&sourceId=-1"
                + "&destId=" + userLoggedOn.getBankAccountId();
        return sendRequestToServer(url);
    }

    public String withdrawCredit(long money) throws Exception {
        String url = Database.getServerUrl() + "/makeTransaction" + "?username=" + userLoggedOn.getUsername()
                + "&password=" + userLoggedOn.getPassword() + "&type=withdraw" + "&money=" + money + "&sourceId="
                + userLoggedOn.getBankAccountId() + "&destId=-1";
        return sendRequestToServer(url);
    }
}
