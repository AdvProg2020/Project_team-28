package view.manager;

import controller.ManagerController;
import view.Conversation;
import view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class UserEditorMenu extends Menu {
    ManagerController controller;

    public UserEditorMenu(ManagerController managerController) {
        super();
        controller = managerController;
        hint = "";
        title = "";
        fortune = "";
        context = controller.viewAllUsers();
        do {
            show();
            context = controller.viewAllUsers();
        } while (execute());
    }

    @Override
    public boolean execute() {
        String input = Menu.scanner.nextLine();
        input = input.replaceAll("^\\s+", "");
        input = input.replaceAll("\\s+$", "");

        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher viewMatcher = Menu.getMatcher(input, "^view \\S+$");
        Matcher deleteUserMatcher = Menu.getMatcher(input, "^delete user \\S+$");
        Matcher createManagerProfileMatcher = Menu.getMatcher(input, "^create manager profile$");

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            } else if (viewMatcher.find()) {
                context = controller.viewUser(input.split(" ")[1]);
            } else if (deleteUserMatcher.find()) {
                controller.deleteUser(input.split(" ")[2]);
            } else if (createManagerProfileMatcher.find()) {
                String type = input.split(" ")[2];
                String username = input.split(" ")[3];
                HashMap<String, Boolean> fields = fillForProperties();
                HashMap<String, String> data = new Conversation(fields).execute();
                data.put("username", username);
                data.put("type", "manager");
                controller.registerAccount(data);
            } else {
                throw new Exception("Invalid command. Use help if you haven't yet, " +
                        "else, close the application Immediately.");
            }
        } catch (Exception e) {
            error = e.toString();
        }
        return true;
    }

    private HashMap<String, Boolean> fillForProperties() {
        HashMap<String, Boolean> fields = new HashMap<>();
        fields.put("password", false);
        fields.put("rest:D", true);
        return fields;
    }
}


