package controller;

import model.User;

public class CustomerController extends UserController {
    public CustomerController(User user) {
        super();
        userLoggedOn = user;
    }
}
