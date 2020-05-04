import controller.UserController;
import view.Menu;
import view.RegisterLoginMenu;
import view.UserMenu;

import java.io.IOException;

class Main {
    public static void main(String[] args) {
        new RegisterLoginMenu(new UserController());
    }
}