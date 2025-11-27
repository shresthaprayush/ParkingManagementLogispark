package com.logispark.parkingmanagementlogispark.models;

import com.google.gson.annotations.SerializedName;

public class ModelVehicleRate {
    int id,rate,discount;
    String serverId;
    int syncStatus;
    @SerializedName("name")
    String vehicleType;
    int isThirtyMinActivation;
    int exceedingLimit;
    int halfHourCost;


    public ModelVehicleRate(int id, int rate, int discount, String serverId, int syncStatus, String vehicleType, int isThirtyMinActivation, int exceedingLimit, int halfHourCost) {
        this.id = id;
        this.rate = rate;
        this.discount = discount;
        this.serverId = serverId;
        this.syncStatus = syncStatus;
        this.vehicleType = vehicleType;
        this.isThirtyMinActivation = isThirtyMinActivation;
        this.exceedingLimit = exceedingLimit;
        this.halfHourCost = halfHourCost;
    }


    public int getIsThirtyMinActivation() {
        return isThirtyMinActivation;
    }

    public void setIsThirtyMinActivation(int isThirtyMinActivation) {
        this.isThirtyMinActivation = isThirtyMinActivation;
    }

    public int getExceedingLimit() {
        return exceedingLimit;
    }

    public void setExceedingLimit(int exceedingLimit) {
        this.exceedingLimit = exceedingLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public int getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(int syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getHalfHourCost() {
        return halfHourCost;
    }

    public void setHalfHourCost(int halfHourCost) {
        this.halfHourCost = halfHourCost;
    }
}
