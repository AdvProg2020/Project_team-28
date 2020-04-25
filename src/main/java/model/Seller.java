package model;

import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private ArrayList<String> salesHistory;
    private ArrayList<String> productsToSell;
    private ArrayList<String> allOffs;

    public Seller(String username, String name, String surname, String email, String password, long credit, String companyName) {
        super(username, name, surname, email, password, credit);
        this.companyName = companyName;
    }

    @Override
    public String getType() {
        return super.getType();
    }
}
