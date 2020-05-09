package model;

import controller.Database;

import java.util.UUID;

public class User {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private long credit;
    private String id;

    public User(String username, String name, String surname, String email, String phoneNumber, String password, long credit) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.credit = credit;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return null;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public long getCredit() {
        return credit;
    }
}
