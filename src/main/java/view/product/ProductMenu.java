package view.product;

import controller.CustomerController;
import controller.UserController;
import view.Conversation;
import view.Filler;
import view.Menu;

import java.util.HashMap;
import java.util.regex.Matcher;

public class ProductMenu extends Menu {
    UserController controller;

    public ProductMenu(UserController userController) {
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
        Matcher digestMatcher = Menu.getMatcher(input, "^digest$");
        Matcher addToCartMatcher = Menu.getMatcher(input, "^add to cart$");
        Matcher selectSellerMatcher = Menu.getMatcher(input, "^select seller \\S+$");
        Matcher attributesMatcher = Menu.getMatcher(input, "^attributes$");
        Matcher compareMatcher = Menu.getMatcher(input, "^compare \\S+$");
        Matcher commentsMatcher = Menu.getMatcher(input, "^comments$");
        Matcher addCommentMatcher = Menu.getMatcher(input, "^add comment$");

        try {
            if (helpMatcher.find()) {
                help();
            } else if (backMatcher.find()) {
                return false;
            } else if (digestMatcher.find()) {
                context = Menu.productController.digest();
            } else if (addToCartMatcher.find()) {
                ((CustomerController) controller).purchase();
            } else if (selectSellerMatcher.find()) {
                Menu.productController.setProductSeller(input.split(" ")[2]);
                error = "seller set to " + input.split(" ")[2];
            } else if (attributesMatcher.find()) {
                context = Menu.productController.viewAttributes().toString();
            } else if (compareMatcher.find()) {
                context = Menu.productController.compareToProducts(input.split(" ")[1]);
            } else if (commentsMatcher.find()) {
                context = Menu.productController.getReviews();
            } else if (addCommentMatcher.find()) {
                HashMap<String, Boolean> fields = new HashMap<>();
                Filler.fillForCommenting(fields);
                HashMap<String, String> res = new Conversation(fields).execute();
                controller.addReview(res.get("title"), res.get("comment"));
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