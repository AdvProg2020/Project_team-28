package view;

import controller.ManagerController;

import java.util.regex.Matcher;

public class DiscountEditorMenu extends Menu {
    ManagerController controller;
    public DiscountEditorMenu(ManagerController managerController) {
        super();
        controller = managerController;
        hint = "";
        title = "";
        fortune = "";
        do {
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

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            }

            else {
                throw new Exception("Invalid command. Use help if you haven't yet, " +
                        "else, close the application Immediately.");
            }
        } catch (Exception e) {
            error = e.toString();
        }
        return true;
    }
}
