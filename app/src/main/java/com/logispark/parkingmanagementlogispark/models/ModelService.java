package com.logispark.parkingmanagementlogispark.models;

import com.google.gson.annotations.SerializedName;

public class ModelService {
    @SerializedName("id")
    int serverId;
    int dbId;
    String rate,service;

    public ModelService(int serverId, int dbId, String rate, String service) {
        this.serverId = serverId;
        this.dbId = dbId;
        this.rate = rate;
        this.service = service;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
