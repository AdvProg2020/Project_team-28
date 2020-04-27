package view;

import javafx.css.Match;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class Conversation {
    private ArrayList<String> fields;
    private ArrayList<Boolean> isOptional;
    private int stateNumber = 0;
    private ArrayList<String> res = new ArrayList<>();
    private String error;

    Conversation(ArrayList<String> fields, ArrayList<Boolean> isOptional) {
        this.fields = fields;
        this.isOptional = isOptional;
    }

    public ArrayList<String> execute() {
        if (stateNumber == fields.size()) {
            return res;
        }
        System.out.print("Please enter " + fields.get(stateNumber) +": ");
        String input = Menu.scanner.nextLine();

        Matcher emptyMatcher = Menu.getMatcher(input, "^\\s*$");
        try {
            if (!emptyMatcher.find() || isOptional.get(stateNumber)) {
                res.add(input);
                stateNumber ++;
            } else {
                throw new Exception("Required field can't be left empty.");
            }
        } catch (Exception e) {
            error = e.toString();
        }
        execute();
        return res; //:
    }
}
