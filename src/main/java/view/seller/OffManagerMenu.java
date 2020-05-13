package view.seller;

import controller.SellerController;
import view.Conversation;
import view.Filler;
import view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class OffManagerMenu extends Menu {
    SellerController controller;

    public OffManagerMenu(SellerController sellerController) {
        super();
        controller = sellerController;
        hint = "";
        title = "";
        fortune = "";
        context = controller.viewOffs();
        do {
            show();
            context = controller.viewOffs();
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
        Matcher editMatcher = Menu.getMatcher(input, "^edit \\S+$");
        Matcher addOffMatcher = Menu.getMatcher(input, "^add off$");

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            } else if (viewMatcher.find()) {
                context = controller.viewOff(input.split(" ")[1]);
            } else if (editMatcher.find()) {
                controller.editOff(input.split(" ")[1]);
            } else if (addOffMatcher.find()) {
                HashMap<String, Boolean> fields = new HashMap<>();
                Filler.fillSellerNewOffFields(fields);
                HashMap<String, String> res = new Conversation(fields).execute();
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
