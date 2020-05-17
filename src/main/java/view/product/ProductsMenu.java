package view.product;

import controller.ProductController;
import model.Product;
import view.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ProductsMenu extends Menu {

    ProductController controller;

    public ProductsMenu(ProductController productController) {
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
        Matcher viewCategoriesMatcher = Menu.getMatcher(input, "^view categories$");
        Matcher filteringMatcher = Menu.getMatcher(input, "^filtering$");
        Matcher sortingMatcher = Menu.getMatcher(input, "^sorting$");
        Matcher showProductsMatcher = Menu.getMatcher(input, "^show products$");
        Matcher showProductMatcher = Menu.getMatcher(input, "^show product \\S$");

        try {
            if (helpMatcher.find()) {
                help();
            } else if (backMatcher.find()) {
                return false;
            } else if (viewCategoriesMatcher.find()) {
                context = controller.getCategories();
            } else if (filteringMatcher.find()) {
                new FilteringMenu(controller);
            } else if (sortingMatcher.find()) {
                new SortingMenu(controller);
            } else {
                throw new Exception("Invalid command. Use help if you haven't yet, " +
                        "else, close the application Immediately.");
            }
        } catch (Exception e) {
            error = e.toString();
        }
        return true;
    }

    private void showFilteredProducts(ArrayList<Product> products) {

    }

    private void showSpecificProduct(Product product) {

    }
}
