package model.exception;

import model.Customer;
import model.User;

import java.io.IOException;

public class DefaultUser extends Customer {

    public DefaultUser() throws IOException {
            super("","Anonymous User","",
                "username@example.com","+98xxxxxxxxxx","",0);
        setUsername(getId());
    }
}
