package view.product;

import controller.UserController;
import view.Menu;

import java.util.regex.Matcher;

public class FilteringMenu extends Menu {
    UserController controller;

    public FilteringMenu(UserController userController) {
        super();
        controller = userController;
        hint = "You can:\n";
        title = "Who's The Boss?";
        fortune = "You truly have tremendous charisma";
        do {
            show();
        } while (execute());
    }

    @Override
    public boolean execute() {
        String input = Menu.scanner.nextLine();
        input = input.replaceAll("^\\s+", "");
        input = input.replaceAll("\\s+$", "");

        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        Matcher showAvailableFiltersMatcher = Menu.getMatcher(input, "^show available filters$");
        Matcher filterMatcher = Menu.getMatcher(input, "^filter \\S+$");
        Matcher currentFiltersMatcher = Menu.getMatcher(input, "^current filters$");
        Matcher disableFilterMatcher = Menu.getMatcher(input, "^disable filter \\S+$");

        try {
            if (helpMatcher.find()) {
                help();
            } else if (backMatcher.find()) {
                return false;
            } else if (showAvailableFiltersMatcher.find()) {
                context = Menu.productController.showAvailableFilters();
            } else if (filterMatcher.find()) {
                Menu.productController.addFilter(input.split(" ")[1], input.split(" ")[1]);
                context = Menu.productController.filterProducts().toString();
            } else if (currentFiltersMatcher.find()) {
                context = Menu.productController.getCurrentFilters();
            } else if (disableFilterMatcher.find()) {
                Menu.productController.removeFilter(input.split(" ")[1]);
                error = "filter " + input.split(" ")[1] + " has been removed";
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