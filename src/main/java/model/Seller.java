package model;

import controller.Database;

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

    public String getCompanyInfo() {
        return companyInfo;
    }

    public ArrayList<SellLog> getSalesHistory() {
        ArrayList<SellLog> result = new ArrayList<>();
        for (String log : this.salesHistory) {
            result.add(Database.getSellLogById(log));
        }
        return result;
    }

    public ArrayList<Product> getProductsToSell () throws Exception {
        ArrayList<Product> result = new ArrayList<>();
        for (String product : productsToSell) {
            result.add(Database.getProductById(product));
        }
        return result;
    }

    @Override
    public String getType() {
        return "seller";
    }
}
