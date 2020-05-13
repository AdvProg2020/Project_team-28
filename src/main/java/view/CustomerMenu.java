package view;

import controller.CustomerController;

import java.util.ArrayList;

public class CustomerMenu extends Menu {
    CustomerController controller;

    public CustomerMenu(String name, Menu parentMenu, ArrayList<Menu> subMenus) {
        super(parentMenu);
    }

    public CustomerMenu(CustomerController customerController) {
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
