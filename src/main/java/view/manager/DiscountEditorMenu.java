package view.manager;

import controller.ManagerController;
import view.Conversation;
import view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class DiscountEditorMenu extends Menu {
    ManagerController controller;

    public DiscountEditorMenu(ManagerController managerController) {
        super();
        controller = managerController;
        hint = "";
        title = "";
        fortune = "";
        context = controller.viewDiscounts();
        do {
            show();
            context = controller.viewDiscounts();
        } while (execute());
    }

    @Override
    public boolean execute() {
        String input = Menu.scanner.nextLine();
        input = input.replaceAll("^\\s+", "");
        input = input.replaceAll("\\s+$", "");

        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher viewMatcher = Menu.getMatcher(input, "^view discount code \\S+$");
        Matcher editMatcher = Menu.getMatcher(input, "^edit discount code \\S+$");
        Matcher removeMatcher = Menu.getMatcher(input, "^remove discount code \\S+$");
        Matcher okMatcher = Menu.getMatcher(input, "^ok$");

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            } else if (okMatcher.find()) {
                return true;
            } else if (viewMatcher.find()) {
                context = controller.viewDiscount(input.split(" ")[1]);
            } else if (editMatcher.find()) {
                HashMap<String, Boolean> fields = new HashMap<>();
                fields.put("Some fields", true); //TODO make it right
                HashMap<String, String> res = new Conversation(fields).execute();
                controller.editDiscount(input.split(" ")[1], res);
            } else if (removeMatcher.find()) {
                controller.removeDiscount(input.split(" ")[1]);
                error = "Discount with code " + input.split(" ")[1] + " removed.";
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
