package com.logispark.parkingmanagementlogispark.models;

public class ModelServerParkingData {

    int slotId, rate,  rateId, discount,duration,tokenNumber;
    double amount;
    String vehicleNumber, inTime, outTime,ticketCode;

    public ModelServerParkingData(int slotId, int rate, int rateId, int discount, int duration, int tokenNumber, double amount, String vehicleNumber, String inTime, String outTime, String ticketCode) {
        this.slotId = slotId;
        this.rate = rate;
        this.rateId = rateId;
        this.discount = discount;
        this.duration = duration;
        this.tokenNumber = tokenNumber;
        this.amount = amount;
        this.vehicleNumber = vehicleNumber;
        this.inTime = inTime;
        this.outTime = outTime;
        this.ticketCode = ticketCode;
    }

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getRateId() {
        return rateId;
    }

    public void setRateId(int rateId) {
        this.rateId = rateId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    public void setTokenNumber(int tokenNumber) {
        this.tokenNumber = tokenNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }
}
