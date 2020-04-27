package view;

import controller.CustomerController;
import controller.ManagerController;
import controller.SellerController;
import controller.UserController;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class RegisterLoginMenu extends Menu {
    private UserController controller;

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
        System.out.println(input);

        Matcher helpMatcher = Menu.getMatcher(input,"^help$");
        Matcher registerMatcher = Menu.getMatcher(input, "^create account \\S+ \\S+$");
        Matcher loginMatcher = Menu.getMatcher(input, "^login \\S+$");
        Matcher backMatcher = Menu.getMatcher(input, "^back$");

        try {
            if (helpMatcher.find()) {
                help();
            } else if (registerMatcher.find()) {
                String type = input.split(" ")[2];
                String username = input.split(" ")[3];
                ArrayList<String> fields = new ArrayList<>();
                ArrayList<Boolean> isOptional = new ArrayList<>();
                fillForProperties(fields, isOptional);
                ArrayList<String> data = new Conversation(fields, isOptional).execute();
                data.add(0,username);
                data.add(0, type);
                controller.registerAccount(data);
            } else if (loginMatcher.find()) {
                String username = input.split(" ")[1];
                ArrayList<String> fields = new ArrayList<>();
                ArrayList<Boolean> isOptional = new ArrayList<>();
                fields.add("password");
                isOptional.add(false);
                String password = new Conversation(fields, isOptional).execute().get(0);
                controller.loginUser(username, password);
                if ("manager".equals(controller.getUser().getType())) {
                    new ManagerMenu(new ManagerController(controller.getUser()));
                } else if ("customer".equals(controller.getUser().getType())) {
                    new UserMenu(new CustomerController(controller.getUser()));
                } else if ("seller".equals(controller.getUser().getType())) {
                    new SellerMenu(new SellerController(controller.getUser()));
                }
            } else if (backMatcher.find()) {
                return false;
            } else {
                throw new Exception("Invalid command. Use help if you haven't yet, " +
                        "else, close the application Immediately.");
            }
        } catch (Exception e) {
            error = e.toString();
        }
        return true;
    }

    private void fillForProperties(ArrayList<String> fields, ArrayList<Boolean> isOptional) {
        fields.add("password");
        isOptional.add(false);
        fields.add("rest:D");
        isOptional.add(true);
    }
}
