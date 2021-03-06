package view;

import java.util.HashMap;

public class Filler {
    //<String fieldName, Boolean isOptional>
    public static void fillSellerEditProductFields(HashMap<String, Boolean> fields) {
        fields.put("name", true);
        fields.put("brand", true);
        fields.put("price", true);
        fields.put("description", true);
        fields.put("category", true);
    }

    public static void fillSellerNewProductFields(HashMap<String, Boolean> fields) {
        fields.put("name", true);
        fields.put("brand", true);
        fields.put("price", false);
        fields.put("description", true);
        fields.put("category", true);
    }

    public static void fillSellerNewOffFields(HashMap<String, Boolean> fields) {
        fields.put("product ids", false);
        fields.put("startTime", false);
        fields.put("finishTime", false);
        fields.put("offStatus", false);
        fields.put("Amount", false);
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
            fields.put("companyInfo", true);
        }
    }

    public static void fillForCommenting(HashMap<String, Boolean> fields) {
        fields.put("title", false);
        fields.put("comment", false);
    }

    public static void fillSellerEditOffFields(HashMap<String, Boolean> fields) {
        fields.put("product ids", false);
        fields.put("startTime", false);
        fields.put("finishTime", false);
        fields.put("offStatus", false);
        fields.put("Amount", false);
    }
}
