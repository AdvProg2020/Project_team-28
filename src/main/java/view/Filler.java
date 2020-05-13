package view;

import java.util.HashMap;

public class Filler {
    public static void fillSellerEditProductFields(HashMap<String, Boolean> fields) {
        fields.put("Old fields", false);
    }

    public static void fillSellerNewProductFields(HashMap<String, Boolean> fields) {
        fields.put("name", true);
        fields.put("some fields for new product", false);
    }

    public static void fillSellerNewOffFields(HashMap<String, Boolean> fields) {
        fields.put("product id", true);
        fields.put("percent", true);
        fields.put("maximum amount", true);
    }
}
