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

    public Property (Property property) {
        this.name = property.getName();
        this.isNumber = property.isNumber();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(boolean number) {
        isNumber = number;
    }

    public boolean isNumber() {
        return isNumber;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public static Property createPropertyFromString (String string, boolean isNumber) {
        Property result = new Property();
        result.setName(string);
        result.setNumber(isNumber);
        return result;
    }
}
