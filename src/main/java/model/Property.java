package model;

import java.util.UUID;

public class Property {
    private String name;
    private boolean isNumber;
    private int valueInt;
    private String valueString;
    private String id;

    public Property() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Property && ((Property) obj).isNumber == this.isNumber) {
            if (isNumber) {
                return this.valueInt == ((Property) obj).valueInt;
            }else {
                return this.valueString.compareTo(((Property) obj).valueString) == 0;
            }
        }
        return false;
    }
}
