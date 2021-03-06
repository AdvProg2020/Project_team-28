package view.userstuff;

import controller.CustomerController;
import controller.ManagerController;
import controller.SellerController;
import controller.UserController;
import model.Customer;
import view.Conversation;
import view.Filler;
import view.Menu;
import view.customer.CustomerMenu;
import view.manager.ManagerMenu;
import view.seller.SellerMenu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class RegisterLoginMenu extends Menu {
    private UserController controller;
    private boolean managerAllowed;

    public RegisterLoginMenu(UserController controller) {
        super();
        this.controller = controller;
        hint = "You can log in with command login\n" +
                "and register with command register";
        title = "Where Legends Were Born";
        fortune = "Why don't you just start trading?";
        do {
            show();
        } while (execute());
    }

    @Override
    public boolean execute() {
        String input = Menu.scanner.nextLine();
        input = input.replaceAll("^\\s+", "");
        input = input.replaceAll("\\s+$", "");

        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher registerMatcher = Menu.getMatcher(input, "^create account \\S+ \\S+$");
        Matcher loginMatcher = Menu.getMatcher(input, "^login \\S+$");
        Matcher backMatcher = Menu.getMatcher(input, "^back$");

        try {
            if (helpMatcher.find()) {
                help();
            }
            else if (registerMatcher.find()) {
                String type = input.split(" ")[2];
                String username = input.split(" ")[3];
                HashMap<String, Boolean> fields = new HashMap<>();
                Filler.fillForRegisterProperties(fields, type);
                HashMap<String, String> data = new Conversation(fields).execute();
                data.put("username", username);
                data.put("type", type);
                controller.registerAccount(data, false);
            }
            else if (loginMatcher.find()) {
                String username = input.split(" ")[1];
                HashMap<String, Boolean> fields = new HashMap<>();
                fields.put("password", false);
                String password = new Conversation(fields).execute().get("password");
                controller.loginUser(username, password);
                context = controller.getUser().getType();
                if ("manager".equals(controller.getUser().getType())) {
                    new ManagerMenu(new ManagerController(controller.getUser(), Menu.productController));
                } else if ("customer".equals(controller.getUser().getType())) {
                    new CustomerMenu(new CustomerController((Customer)controller.getUser(), Menu.productController));
                    //TODO renew default account
                } else if ("seller".equals(controller.getUser().getType())) {
                    new SellerMenu(new SellerController(controller.getUser(), Menu.productController));
                }
            }
            else if (backMatcher.find()) {
                return false;
            }
            else {
                throw new Exception("Invalid command. Use help if you haven't yet, " +
                        "else, close the application Immediately.");
            }
        } catch (Exception e) {
            error = "error: " + e.toString();
        }
        return true;
    }
}
