package model;

import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private ArrayList<SellLog> salesHistory;
    private ArrayList<Product> productsToSell;
    private ArrayList<Off> allOffs;

    public Seller(String username, String name, String surname, String email, String password, long credit, String companyName) {
        super(username, name, surname, email, password, credit);
        this.companyName = companyName;
    }

    @Override
    public String getType() {
        return super.getType();
    }
}
