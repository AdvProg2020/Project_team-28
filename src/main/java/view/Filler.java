package view;

import java.util.HashMap;

public class Filler {
    //<String fieldName, Boolean isOptional>
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

    public static void fillPurchaseInformationReceiver(HashMap<String, Boolean> fields) {
        fields.put("street", false); //It's necessity can be obtained using the last information
        fields.put("building number", false);
        fields.put("phone number", true);
    }

    public static void fillForRegisterProperties(HashMap<String, Boolean> fields, String type) {
        fields.put("password", false);
        fields.put("name", true);
        fields.put("surname", true);
        fields.put("email", true);
        fields.put("phoneNumber", true);
        fields.put("credit", true);
        if (type.equals("seller")) {
            fields.put("companyName", true);
        }
    }
}
