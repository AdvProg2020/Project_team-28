package model;

public class User {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private long credit;

    public User(String username, String name, String surname, String email, String password, long credit) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.credit = credit;
    }

    public String getType() {
        return null;
    }
}
