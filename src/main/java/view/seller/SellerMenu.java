package view.seller;

import controller.SellerController;
import view.Conversation;
import view.Filler;
import view.Menu;
import view.userstuff.PersonalInfoMenu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class SellerMenu extends Menu {
    SellerController controller;

    public SellerMenu(SellerController sellerController) {
        super();
        controller = sellerController;
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
        Matcher companyInfoMatcher = Menu.getMatcher(input, "^view company information$");
        Matcher viewSaleHistoryMatcher = Menu.getMatcher(input, "^view sales history$");
        Matcher manageProductsMatcher = Menu.getMatcher(input, "^manage products$");
        Matcher addProductMatcher = Menu.getMatcher(input, "^add product$");
        Matcher removeProductMatcher = Menu.getMatcher(input, "^remove product \\S+$");
        Matcher showCategoriesMatcher = Menu.getMatcher(input, "^show categories$");
        Matcher viewOffsMatcher = Menu.getMatcher(input, "^view offs$");
        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        try {
            if (viewPersonalInfoMatcher.find()) {
                new PersonalInfoMenu(controller);
            } else if (companyInfoMatcher.find()) {
                context = controller.viewCompanyInfo();
            } else if (viewSaleHistoryMatcher.find()) {
                context = controller.viewSalesHistory().toString();
            } else if (manageProductsMatcher.find()) {
                new ProductEditorMenu(controller);
            } else if (addProductMatcher.find()) {
                HashMap<String, Boolean> fields = new HashMap<>();
                Filler.fillSellerNewProductFields(fields);
                HashMap<String, String> res = new Conversation(fields).execute();
                controller.addProduct(res);
            } else if (removeProductMatcher.find()) {
                controller.deleteProduct(input.split(" ")[1]);
            } else if (showCategoriesMatcher.find()) {
                context = controller.viewCategories();
            } else if (viewOffsMatcher.find()) {
                new OffManagerMenu(controller);
            } else if (helpMatcher.find()) {
                help();
            } else if (backMatcher.find()) {
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
