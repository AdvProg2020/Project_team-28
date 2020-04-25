package model;

import java.util.UUID;

public class User {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private long credit;
    private String id;

    public User(String username, String name, String surname, String email, String password, long credit) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.credit = credit;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return null;
    }

    public String getFullName() {
        return name + " " + surname;
    }
}
