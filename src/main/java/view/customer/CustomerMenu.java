package view.customer;

import controller.CustomerController;
import view.Menu;
import view.userstuff.PersonalInfoMenu;

import java.util.regex.Matcher;

public class CustomerMenu extends Menu {
    CustomerController controller;

    public CustomerMenu(CustomerController customerController) {
        super();
        controller = customerController;
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

        Matcher viewPersonalInfoMatcher = Menu.getMatcher(input, "^view personal info$");
        Matcher viewCartMatcher = Menu.getMatcher(input, "^view cart$");
        Matcher viewOrdersMatcher = Menu.getMatcher(input, "^view orders$");
        Matcher viewBalanceMatcher = Menu.getMatcher(input, "^view balance$");
        Matcher viewDiscountCodesMatcher = Menu.getMatcher(input, "^view discount codes$");
        Matcher logoutMatcher = Menu.getMatcher(input, "^logout$");
        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        try {
            if (viewPersonalInfoMatcher.find()) {
                new PersonalInfoMenu(controller);
            } else if (viewCartMatcher.find()) {
                new CartMenu(controller);
            } else if (viewOrdersMatcher.find()) {
                new CustomerHistoryMenu(controller);
            } else if (viewBalanceMatcher.find()) {
                context = "Your current balance is: " + controller.viewBalance();
            } else if (viewDiscountCodesMatcher.find()) {
                context = "Your discount codes are:\n" + controller.viewDiscountCodes();
            } else if (helpMatcher.find()) {
                help();
            } else if (backMatcher.find() || logoutMatcher.find()) {
                return false;
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