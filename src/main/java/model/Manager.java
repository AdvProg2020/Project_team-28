package model;

import controller.Database;

public class Manager extends User {
    public Manager(String username, String name, String surname, String email, String phoneNumber, String password, long credit) {
        super(username, name, surname, email, phoneNumber, password, credit);
        this.bankAccountId = Database.getShopBankAccountId();
    }

    @Override
    public String getType() {
        return "manager";
    }
}
