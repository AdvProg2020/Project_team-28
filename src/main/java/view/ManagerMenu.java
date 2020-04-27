package view;

import controller.ManagerController;

import java.util.ArrayList;

public class ManagerMenu extends Menu {
    private ManagerController controller;
    public ManagerMenu(String name, Menu parentMenu, ArrayList<Menu> subMenus) {
        super(parentMenu);
    }

    public ManagerMenu(ManagerController managerController) {
        controller = managerController;
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
