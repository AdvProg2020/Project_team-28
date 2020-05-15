package view.customer;

import controller.CustomerController;
import view.Conversation;
import view.Filler;
import view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class CartMenu extends Menu {
    CustomerController controller;

    public CartMenu(CustomerController customerController) {
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

        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        Matcher showProductsMatcher = Menu.getMatcher(input, "^show products$");
        Matcher viewProductMatcher = Menu.getMatcher(input, "^view \\S+$");
        Matcher increaseMatcher = Menu.getMatcher(input, "^increase \\S+$");
        Matcher decreaseMatcher = Menu.getMatcher(input, "^decrease \\S+$");
        Matcher showTotalPriceMatcher = Menu.getMatcher(input, "^show total price$");
        Matcher purchaseMatcher = Menu.getMatcher(input, "^purchase$");

        try {
            if (helpMatcher.find()) {
                help();
            } else if (backMatcher.find()) {
                return false;
            } else if (showProductsMatcher.find()) {
                context = controller.viewCartProducts();
            } else if (viewProductMatcher.find()) {
                context = controller.showProduct(input.split(" ")[1]);
            } else if (increaseMatcher.find()) {
                controller.addToCart(input.split(" ")[1]);
                error = "One added to the count of product number " + input.split(" ")[1];
            } else if (decreaseMatcher.find()) {
                controller.decreaseNumberOfProduct(input.split(" ")[1]);
                error = "One removed from count of product number " + input.split(" ")[1];
            } else if (showTotalPriceMatcher.find()) {
                context = "Total price is " + controller.getTotalPrice();
            } else if (purchaseMatcher.find()) {
                HashMap<String, Boolean> fields = new HashMap<>();
                Filler.fillPurchaseInformationReceiver(fields);
                HashMap<String, String> res = new Conversation(fields).execute();
                controller.addAddress(res);
                new DiscountCodeReceiver(controller);
                context = controller.getPaymentCheck();
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