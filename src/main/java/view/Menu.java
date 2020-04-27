package view;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Menu {
    protected static Scanner scanner = new Scanner(System.in);

    protected String title;
    protected String fortune;
    protected String hint;
    protected boolean give_hint;
    protected String error;
    private Menu parentMenu;

    protected Menu(String title, Menu parentMenu, String fortune) {
        this.title = title;
        this.parentMenu = parentMenu;
        this.fortune = fortune;
    }

    protected Menu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    protected Menu() {

    }

    protected static Matcher getMatcher(String string, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(string);
    }

    public void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 80 * 30; i++)
            System.out.print("\b");
    }

    public abstract boolean execute();

    public void show() {
        clearScreen();
        System.out.println(ConsoleColors.RED_BOLD + ConsoleColors.WHITE_BACKGROUND +
                title + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN + ConsoleColors.WHITE_BACKGROUND +
                fortune + ConsoleColors.RESET);

        if (give_hint) {
            System.out.println(ConsoleColors.YELLOW + ConsoleColors.BLACK_BACKGROUND +
                    hint + ConsoleColors.RESET);
        }
        if (error != null) {
            System.out.println(ConsoleColors.RED + ConsoleColors.BLUE_BACKGROUND +
                    error + ConsoleColors.RESET);
            error = null;
        }
    }

    public void help() {
        give_hint = !give_hint;
    }
}
