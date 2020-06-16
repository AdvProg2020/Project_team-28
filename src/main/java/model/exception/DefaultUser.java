package model.exception;

import model.Customer;
import model.User;

public class DefaultUser extends Customer {

    public DefaultUser() {
            super("","Anonymous User","",
                "username@example.com","+98xxxxxxxxxx","",0);
        setUsername(getId());
    }
}
