package view.manager;

import controller.ManagerController;
import view.Menu;

import java.util.regex.Matcher;

public class RequestMenu extends Menu {
    ManagerController controller;
    public RequestMenu(ManagerController managerController) {
        super();
        controller = managerController;
        hint = "";
        title = "";
        fortune = "";
        context = controller.viewRequests();
        do {
            show();
            context = controller.viewRequests();
        } while (execute());
    }

    @Override
    public boolean execute() {
        String input = Menu.scanner.nextLine();
        input = input.replaceAll("^\\s+", "");
        input = input.replaceAll("\\s+$", "");

        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher detailMatcher = Menu.getMatcher(input, "^details \\S+$");
        Matcher acceptMatcher = Menu.getMatcher(input, "^accept \\S+");
        Matcher declineMatcher = Menu.getMatcher(input, "^decline \\S+");
        Matcher okMatcher = Menu.getMatcher(input, "^ok$");

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            } else if (detailMatcher.find()) {
                context = controller.viewRequest(input.split(" ")[1]);
            } else if (acceptMatcher.find()) {
                controller.evaluateRequest(input.split(" ")[1], true);
                error = "Request with ID " + input.split(" ")[1] + " is accepted";
            } else if (declineMatcher.find()) {
                controller.evaluateRequest(input.split(" ")[1], false);
                error = "Request with ID " + input.split(" ")[1] + " is declined";
            } else if (okMatcher.find()) {
                return true;
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
