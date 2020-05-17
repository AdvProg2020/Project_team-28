package view.product;

import controller.ProductController;
import view.Menu;

import java.util.regex.Matcher;

public class FilteringMenu extends Menu {
    ProductController controller;

    public FilteringMenu(ProductController productController) {
        super();
        controller = productController;
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
                context = controller.showAvailableFilters();
            } else if (filterMatcher.find()) {
                context = controller.filterProducts(input.split(" ")[1]);
            } else if (currentFiltersMatcher.find()) {
                context = controller.getCurrentFilters();
            } else if (disableFilterMatcher.find()) {
                controller.removeFilter(input.split(" ")[1]);
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