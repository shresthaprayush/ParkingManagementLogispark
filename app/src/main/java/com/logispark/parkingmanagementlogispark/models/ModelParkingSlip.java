package com.logispark.parkingmanagementlogispark.models;

public class ModelParkingSlip {

    String vechileNumber;
    int rate;
    String uniqueId;
    String date,time,slotName,vehicleType;
    boolean accommodation,washing;

    public ModelParkingSlip(String vechileNumber, int rate, String uniqueId, String date, String time, String slotName, String vehicleType, boolean accommodation, boolean washing) {
        this.vechileNumber = vechileNumber;
        this.rate = rate;
        this.uniqueId = uniqueId;
        this.date = date;
        this.time = time;
        this.slotName = slotName;
        this.vehicleType = vehicleType;
        this.accommodation = accommodation;
        this.washing = washing;
    }

    public String getVechileNumber() {
        return vechileNumber;
    }

    public void setVechileNumber(String vechileNumber) {
        this.vechileNumber = vechileNumber;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public boolean isAccommodation() {
        return accommodation;
    }

    public void setAccommodation(boolean accommodation) {
        this.accommodation = accommodation;
    }

    public boolean isWashing() {
        return washing;
    }

    public void setWashing(boolean washing) {
        this.washing = washing;
    }
}
