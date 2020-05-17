package model;

import java.util.UUID;

public class Property {
    private String name;
    private boolean isNumber;
    private long valueLong = 0;
    private String valueString = "";
    private String id;

    public Property() {
        this.id = UUID.randomUUID().toString();
    }

    public Property (Property property) {
        this.name = property.getName();
        this.isNumber = property.isNumber();
        this.id = UUID.randomUUID().toString();
        this.valueLong = 0;
        this.valueString = "";
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Property && ((Property) obj).isNumber == this.isNumber &&
                ((Property) obj).getName().equals(this.name)) {
            if (isNumber) {
                return this.valueLong == ((Property) obj).valueLong;
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

    public void setValueLong(int valueLong) {
        this.valueLong = valueLong;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public long getValueLong() {
        return valueLong;
    }

    public String getValueString() {
        return valueString;
    }

    @Override
    public String toString() {
        return this.name + ": " + (isNumber ? this.valueLong : this.valueString);
    }
}
