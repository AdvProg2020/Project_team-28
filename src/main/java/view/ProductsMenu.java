package view;

import java.util.ArrayList;

public class ProductsMenu extends Menu {
    public ProductsMenu(String name, Menu parentMenu, ArrayList<Menu> subMenus) {
        super(parentMenu);
    }

    @Override
    public boolean execute() {

        return false;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void help() {

    }

    private void showFilteredProducts(ArrayList<model.Product> products) {

    }

    private void showSpecificProduct(model.Product product) {

    }
}
