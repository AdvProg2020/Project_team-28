package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class Conversation {
    private HashMap<String, Boolean> fields;
    private int stateNumber = 0;
    private HashMap<String, String> res = new HashMap<>();
    ArrayList<String> keys = new ArrayList<>();
    private String error;

    public Conversation(HashMap<String, Boolean> fields) {
        this.fields = fields;
        keys.addAll(fields.keySet());
    }

    public HashMap<String, String> execute() {
        if (stateNumber == fields.size()) {
            return res;
        }
        String key = keys.get(stateNumber);
        System.out.print("Please enter " + key + ": ");
        String input = Menu.scanner.nextLine();

        Matcher emptyMatcher = Menu.getMatcher(input, "^\\s*$");
        try {
            if (!emptyMatcher.find() || fields.get(key)) {
                res.put(key, input);
                stateNumber++;
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
