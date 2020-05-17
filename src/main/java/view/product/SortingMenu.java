package view.product;

import controller.ProductController;
import view.Menu;

import java.util.regex.Matcher;

public class SortingMenu extends Menu {
    ProductController controller;

    public SortingMenu(ProductController productController) {
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
        Matcher showAvailableSortsMatcher = Menu.getMatcher(input, "^show available filters$");
        Matcher sortMatcher = Menu.getMatcher(input, "^sort \\S+$");
        Matcher currentSortMatcher = Menu.getMatcher(input, "^current sort$");
        Matcher disableSortMatcher = Menu.getMatcher(input, "^disable sort$");

        try {
            if (helpMatcher.find()) {
                help();
            } else if (backMatcher.find()) {
                return false;
            } else if (showAvailableSortsMatcher.find()) {
                context = controller.showAvailableSorts().toString();
            } else if (sortMatcher.find()) {
                ProductController.setSort(input.split(" ")[1]);
                error = "set has been set to " + input.split(" ")[1];
            } else if (currentSortMatcher.find()) {
                context = ProductController.getCurrentSort();
            } else if (disableSortMatcher.find()) {
                ProductController.setSort("view-count");
                error = "Sort has been set to view-count";
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