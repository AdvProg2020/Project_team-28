package view;

import controller.CustomerController;
import view.userstuff.PersonalInfoMenu;

import java.util.regex.Matcher;

public class SampleClass extends Menu {
    CustomerController controller;

    public SampleClass(CustomerController customerController) {
        super();
        controller = customerController;
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

        Matcher viewPersonalInfoMatcher = Menu.getMatcher(input, "^view personal info$");
        Matcher helpMatcher = Menu.getMatcher(input, "^help$");
        Matcher backMatcher = Menu.getMatcher(input, "^back$");
        try {
            if (viewPersonalInfoMatcher.find()) {
                new PersonalInfoMenu(controller);
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