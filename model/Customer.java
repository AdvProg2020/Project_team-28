package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User {
    private HashMap<Product, Integer> cart;
    private ArrayList<purchaseLog> purchaseHistory;
    private ArrayList<Discount> discountCodes;

    public Customer(String username, String name, String surname, String email, String password, long credit) {
        super(username, name, surname, email, password, credit);
    }

    @Override
    public String getType() {
        return super.getType();
    }
}
