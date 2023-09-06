package com.logispark.parkingmanagementlogispark.models;

public class ModelRecentBills {

    private int token;
    private int tokenid;
    private int amount;
    private String date;
    private int sync_status;


    public ModelRecentBills(int token, int tokenid, int amount, String date, int sync_status) {
        this.token = token;
        this.tokenid = tokenid;
        this.amount = amount;
        this.date = date;
        this.sync_status = sync_status;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getTokenid() {
        return tokenid;
    }

    public void setTokenid(int tokenid) {
        this.tokenid = tokenid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSync_status() {
        return sync_status;
    }

    public void setSync_status(int sync_status) {
        this.sync_status = sync_status;
    }
}
