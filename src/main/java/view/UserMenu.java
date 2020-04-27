package view;

import controller.CustomerController;

import java.util.ArrayList;

public class UserMenu extends Menu {
    CustomerController controller;
    public UserMenu(String name, Menu parentMenu, ArrayList<Menu> subMenus) {
        super(parentMenu);
    }

    public UserMenu(CustomerController customerController) {
        controller = customerController;
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
