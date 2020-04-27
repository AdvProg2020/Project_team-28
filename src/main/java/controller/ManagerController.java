package controller;

import com.google.gson.Gson;
import model.Discount;
import model.User;

public class ManagerController extends UserController {
    private Discount selectedDiscount;
    private Gson selectedRequest;

    public ManagerController(User user) {
        super();
        userLoggedOn = user;
    }
}
