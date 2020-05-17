package view.manager;

import controller.ManagerController;
import view.Conversation;
import view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class CategoryMenu extends Menu {
    ManagerController controller;

    public CategoryMenu(ManagerController managerController) {
        super();
        controller = managerController;
        hint = "";
        title = "";
        fortune = "";
        context = controller.viewCategories();
        do {
            show();
            context = controller.viewCategories();
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
        Matcher addMatcher = Menu.getMatcher(input, "^add \\S+$");
        Matcher removeMatcher = Menu.getMatcher(input, "^remove \\S+$");

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            } else if (editMatcher.find()) {
                HashMap<String, Boolean> fields = new HashMap<>();
                fields.put("some category fields", true);
                HashMap<String, String> res = new Conversation(fields).execute();
                controller.editCategory(input.split(" ")[1], res);
            } else if (addMatcher.find()) {
                HashMap<String, Boolean> fields = new HashMap<>();
                fields.put("some category fields", true);
                HashMap<String, String> res = new Conversation(fields).execute();
                res.put("name", input.split(" ")[1]);
                controller.createCategory(res);
                error = "Category Added!";
            } else if (removeMatcher.find()) {
                controller.removeCategory(input.split(" ")[1]);
                error = "Category with name " + input.split(" ")[1] + " removed";
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
