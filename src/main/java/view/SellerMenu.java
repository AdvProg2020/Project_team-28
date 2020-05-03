package view;

import controller.SellerController;

import java.util.ArrayList;

public class SellerMenu extends Menu {
    SellerController controller;
    public SellerMenu(String name, Menu parentMenu, ArrayList<Menu> subMenus) {
        super(parentMenu);
    }

    public SellerMenu(SellerController sellerController) {
        controller = sellerController;
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
}
