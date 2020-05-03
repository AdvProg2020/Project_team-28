package view;

import controller.ManagerController;
import model.Discount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class ManagerMenu extends Menu {
    private ManagerController controller;

    public ManagerMenu(ManagerController managerController) {
        super();
        controller = managerController;
        hint = "You can:\n" +
                "\t- view personal info: show and edit your info\n" +
                "\t- manage users: as it sounds you can view, delete user accounts and add new managers\n" +
                "\t- manage all products: not managing, you just remove them\n" +
                "\t- create discount code: should I explain everything to you?\n" +
                "\t- view discount codes: Yes, seems so. You can edit/remove all the discount codes\n" +
                "\t- manage requests: accepting and declining all the requests is on you of course";
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
        Matcher manageUsersMatcher = Menu.getMatcher(input, "^manage users$");
        Matcher manageAllProducts = Menu.getMatcher(input, "^manage all products$");
        Matcher createDiscountCodeMatcher = Menu.getMatcher(input, "^create discount code$");
        Matcher viewDiscountCodesMatcher = Menu.getMatcher(input, "^view discount codes$");
        Matcher manageRequestsMatcher = Menu.getMatcher(input, "^manage requests$");
        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        try {
            if (viewPersonalInfoMatcher.find()) {
                new PersonalInfoMenu(controller);
            } else if (manageUsersMatcher.find()) {
                new UserEditorMenu(controller);
            } else if (manageAllProducts.find()) {
                new ProductEditorMenu(controller);
            } else if (createDiscountCodeMatcher.find()) {
                //TODO make it right
                HashMap<String, Boolean> fields = new HashMap<>();
                fields.put("code", false);
                fields.put("percent", true);
                HashMap<String, String> res = new Conversation(fields).execute();
                controller.createDiscount(res);
            } else if (viewDiscountCodesMatcher.find()) {
                new DiscountEditorMenu(controller);
            } else if (manageRequestsMatcher.find()) {
                new RequestMenu(controller);
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
