package view.userstuff;

import controller.UserController;
import view.Conversation;
import view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class PersonalInfoMenu extends Menu {
    UserController controller;

    public PersonalInfoMenu(UserController controller) {
        super();
        this.controller = controller;
        hint = "";
        title = "";
        fortune = "";
        do {
            context = controller.getPersonalInfo();
            show();
        } while (execute());
    }

    @Override
    public boolean execute() {
        String input = Menu.scanner.nextLine();
        input = input.replaceAll("^\\s+", "");
        input = input.replaceAll("\\s+$", "");

        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher editMatcher = Menu.getMatcher(input, "^edit \\S+$");

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            } else if (editMatcher.find()) {
                String field = input.split(" ")[1];
                HashMap<String, Boolean> fields = new HashMap<>();
                fields.put(field, false);
                String value = new Conversation(fields).execute().get(field);
                controller.changePersonalInfo(field, value);
            } else {
                throw new Exception("Invalid command. Use help if you haven't yet, " +
                        "else, close the application Immediately.");
            }
        } catch (Exception e) {
            error = e.toString();
        }
        return true;
    }
}
