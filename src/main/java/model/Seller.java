package model;

import java.util.ArrayList;

public class Seller extends User {
    private String companyName;
    private String companyInfo;
    private ArrayList<String> salesHistory;
    private ArrayList<String> productsToSell;
    private ArrayList<String> allOffs;

    public Seller(String username, String name, String surname, String email, String phoneNumber, String password, long credit, String companyName, String companyInfo) {
        super(username, name, surname, email, phoneNumber, password, credit);
        this.companyName = companyName;
        this.companyInfo = companyInfo;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getType() {
        return "seller";
    }
}
