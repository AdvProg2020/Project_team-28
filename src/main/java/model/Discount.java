package model;

import controller.Database;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Discount {
    private String code;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int discountPercent;
    private long maximumAmount;
    private int repetitionNumber;
    private HashMap<User, Integer> users; // (id, repetitionNumber)
    private String id;

    public Discount() {
        this.id = UUID.randomUUID().toString();
        users = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public long getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(long maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public int getRepetitionNumber() {
        return repetitionNumber;
    }

    public void setRepetitionNumber(int repetitionNumber) {
        this.repetitionNumber = repetitionNumber;
        for (User user : users.keySet()) {
            users.replace(user, repetitionNumber);
        }
    }

    public void useCode (User user) throws Exception {
        if (!this.users.containsKey(user))
            throw new Exception("You dont own this code");
        if (this.users.get(user) > 0 && validateTime())
            this.users.replace(user, this.users.get(user) - 1);
        else
            throw new Exception("Code Expired");
    }

    public boolean validateTime () {
        return startTime.isBefore(LocalDateTime.now()) && finishTime.isAfter(LocalDateTime.now());
    }

    public static boolean isCodeUnique (String code) {
        for (Discount discount : Database.getAllDiscountCodes()) {
            if (discount.getCode().equals(code))
                return false;
        }
        return true;
    }

    public static String generateRandomCode () {
        char[] randomCode = new char[8];
        int min = 48;
        int max = 122;
        for (int i = 0 ; i < 8 ; i++) {
            int randomCharacter = (int) (Math.random() * (max - min + 1) + min);
            if ( (randomCharacter >= 58 && randomCharacter <= 64) || (randomCharacter <= 96 && randomCharacter >= 91) ) {
                i--;
            }else
                randomCode[i] = (char) randomCharacter;
        }
        return String.valueOf(randomCode);
    }
}
