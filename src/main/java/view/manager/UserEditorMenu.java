package view.manager;

import controller.ManagerController;
import view.Conversation;
import view.Filler;
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
            }
            else if (helpMatcher.find()) {
                help();
            }
            else if (viewMatcher.find()) {
                context = controller.viewUser(input.split(" ")[1]);
            }
            else if (deleteUserMatcher.find()) {
                controller.deleteUser(input.split(" ")[2]);
            }
            else if (createManagerProfileMatcher.find()) {
                HashMap<String, Boolean> fields = new HashMap<>();
                fillForRegisterManager(fields);
                HashMap<String, String> data = new Conversation(fields).execute();
                data.put("type", "manager");
                controller.registerAccount(data, true);
            }
            else {
                throw new Exception("Invalid command. Use help if you haven't yet, " +
                        "else, close the application Immediately.");
            }
        }
        catch (Exception e) {
            error = e.toString();
        }
        return true;
    }

    public static void fillForRegisterManager(HashMap<String, Boolean> fields) {
        fields.put("username", false);
        Filler.fillForRegisterProperties(fields, "manager");
    }
}


