import controller.Database;
import controller.UserController;
import view.Menu;
import view.userstuff.RegisterLoginMenu;

class Main {
    public static void main(String[] args) {
        Database.loadAllData();
        new RegisterLoginMenu(new UserController(null, Menu.productController));
    }
}