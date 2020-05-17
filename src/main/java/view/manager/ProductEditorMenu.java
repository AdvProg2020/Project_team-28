package view.manager;

import controller.ManagerController;
import view.Menu;

import java.util.regex.Matcher;

public class ProductEditorMenu extends Menu {
    ManagerController controller;

    public ProductEditorMenu(ManagerController managerController) {
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
        Matcher removeMatcher = Menu.getMatcher(input, "^remove \\S+$");

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            } else if (removeMatcher.find()) {
                controller.removeProduct(input.split(" ")[1]);
                error = "product with id " + input.split(" ")[1] + " is removed";
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
