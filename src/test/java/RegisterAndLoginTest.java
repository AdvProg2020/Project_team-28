import controller.Database;
import controller.UserController;
import model.User;
import org.junit.BeforeClass;
import org.junit.Test;
import view.Menu;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RegisterAndLoginTest {
    public static UserController controller;

    @BeforeClass
    public static void maintainController() throws IOException {
        controller = new UserController(null, Menu.productController);
    }

    @Test
    public void customerLoginTest() throws Exception {
        try {
            Database.remove(Database.getUserByUsername("ali"));
            controller.loginUser("ali", "12");
            assertNotEquals(true, true);
        } catch (Exception ignored) {
        }
        HashMap<String, String> data = new HashMap<>();
        data.put("username", "ali");
        data.put("password", "12");
        data.put("type", "Customer");
        data.put("credit", "");
        controller.registerAccount(data, true);
        controller.loginUser("ali", "12");
        User user = controller.getUser();
        assertEquals("ali", user.getUsername());
        controller.logout();
        assertNotEquals("ali", controller.getUser().getUsername());
    }

    @Test
    public void ManagerLoginTest() throws Exception {
        try {
            controller.loginUser("ali", "12");
            assertNotEquals(true, true);
        } catch (Exception ignored) {
        }
        HashMap<String, String> data = new HashMap<>();
        data.put("username", "ali");
        data.put("password", "12");
        data.put("type", "Manager");
        data.put("credit", "");
        try {
            controller.registerAccount(data, false);
            System.out.println("registered");
            data.put("username", "naghi");
            controller.registerAccount(data, false);
            assertNotEquals(true, true);
        } catch (Exception ignored) {
        }
        controller.loginUser("ali", "12");
        User user = controller.getUser();
        assertEquals("ali", user.getUsername());
        controller.logout();
        assertNotEquals("ali", controller.getUser().getUsername());
    }
}
