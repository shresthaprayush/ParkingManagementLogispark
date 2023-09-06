package com.logispark.parkingmanagementlogispark.models;

public class ModelSalesStats {
    private int totalAmount;
    private int tokens;

    public ModelSalesStats(int totalAmount, int tokens) {
        this.totalAmount = totalAmount;
        this.tokens = tokens;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
}
