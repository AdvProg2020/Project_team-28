import controller.UserController;
import view.userstuff.RegisterLoginMenu;

class Main {
    public static void main(String[] args) {
        new RegisterLoginMenu(new UserController());
    }
}