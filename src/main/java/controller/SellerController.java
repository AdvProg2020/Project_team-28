package controller;

import model.User;

public class SellerController extends UserController {
    public SellerController(User user) {
        super();
        userLoggedOn = user;
    }
}
