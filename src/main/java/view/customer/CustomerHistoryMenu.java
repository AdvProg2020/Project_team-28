package view.customer;

import controller.CustomerController;
import view.Menu;
import view.Table;

import java.util.Arrays;
import java.util.regex.Matcher;

public class CustomerHistoryMenu extends Menu {
    private CustomerController controller;

    public CustomerHistoryMenu(CustomerController customerController) {
        super();
        controller = customerController;
        hint = "";
        title = "";
        fortune = "";
        context = new Table(Arrays.asList("logId", "amount paid", "date"), controller.viewOrders()).toString();
        do {
            show();
            context = new Table(Arrays.asList("logId", "amount paid", "date"), controller.viewOrders()).toString();
        } while (execute());
    }

    @Override
    public boolean execute() {
        String input = Menu.scanner.nextLine();
        input = input.replaceAll("^\\s+", "");
        input = input.replaceAll("\\s+$", "");

        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher showOrderMatcher = Menu.getMatcher(input, "^show order \\S+$");
        Matcher rateMatcher = Menu.getMatcher(input, "^rate \\S+ \\S+$");

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            } else if (showOrderMatcher.find()) {
                context = controller.showOrder(input.split(" ")[1]);
            } else if (rateMatcher.find()) {
                controller.rateProduct(input.split(" ")[1], input.split(" ")[2]);
                error = "product number " + input.split(" ")[1] + " rated " + input.split(" ")[2];
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
