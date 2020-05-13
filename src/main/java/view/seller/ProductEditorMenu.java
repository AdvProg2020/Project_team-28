package view.seller;

import controller.SellerController;
import view.Conversation;
import view.Filler;
import view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class ProductEditorMenu extends Menu {
    SellerController controller;

    public ProductEditorMenu(SellerController sellerController) {
        super();
        controller = sellerController;
        hint = "";
        title = "";
        fortune = "";
        context = controller.viewProducts();
        do {
            show();
            context = controller.viewProducts();
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
        Matcher viewBuyersMatcher = Menu.getMatcher(input, "^view buyers \\S+$");
        Matcher editMatcher = Menu.getMatcher(input, "^edit \\S+$");

        try {
            if (backMatcher.find()) {
                return false;
            } else if (helpMatcher.find()) {
                help();
            } else if (viewMatcher.find()) {
                context = controller.viewProduct(input.split(" ")[1]);
            } else if (viewBuyersMatcher.find()) {
                context = controller.viewBuyers(input.split(" ")[1]);
            } else if (editMatcher.find()) {
                HashMap<String, Boolean> fields = new HashMap<>();
                Filler.fillSellerEditProductFields(fields);
                HashMap<String, String> res = new Conversation(fields).execute();
                controller.editProduct(input.split(" ")[1], res);
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
