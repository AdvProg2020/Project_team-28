package view;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    protected static Scanner scanner = new Scanner(System.in);

    private String name;
    private Menu parentMenu;
    private ArrayList<Menu> subMenus;

    protected Menu(String name, Menu parentMenu, ArrayList<Menu> subMenus) {
        this.name = name;
        this.parentMenu = parentMenu;
        this.subMenus = subMenus;
        System.out.println("hey there");
    }

    public void execute() {

    }

    public void show() {

    }

    public void help() {

    }
}
