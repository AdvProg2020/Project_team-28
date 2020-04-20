package model;

public class Manager extends User {
    public Manager(String username, String name, String surname, String email, String password, long credit) {
        super(username, name, surname, email, password, credit);
    }

    @Override
    public String getType() {
        return super.getType();
    }
}
