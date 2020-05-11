package view;

import controller.ManagerController;
import model.Manager;

import java.util.HashMap;
import java.util.regex.Matcher;

public class PersonalInfoMenu extends Menu {
    ManagerController controller;

    public PersonalInfoMenu(ManagerController managerController) {
        super();
        controller = managerController;
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
                HashMap<String, String> information = new HashMap<>();
                information.put(field, value);
                controller.changePersonalInfo(information);
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
