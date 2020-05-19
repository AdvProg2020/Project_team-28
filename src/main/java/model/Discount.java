package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Discount {
    private String code;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private int discountPercent;
    private long maximumAmount;
    private int repetitionNumber;
    private String id;

    public Discount() {
        this.id = UUID.randomUUID().toString();
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
    }

    public void useCode () throws Exception {
        if (this.repetitionNumber > 0 && validateTime())
            this.repetitionNumber -= 1;
        else
            throw new Exception("Code Expired");
    }

    public boolean validateTime () {
        return startTime.isBefore(LocalDateTime.now()) && finishTime.isAfter(LocalDateTime.now());
    }
}
