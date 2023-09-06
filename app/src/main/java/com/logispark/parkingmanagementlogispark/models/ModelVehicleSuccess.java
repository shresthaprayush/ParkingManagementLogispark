package com.logispark.parkingmanagementlogispark.models;

import java.util.List;

public class ModelVehicleSuccess {

    private boolean success;
    private String message;
    private List<ModelVehicleRate> data;

    public ModelVehicleSuccess(boolean success, String message, List<ModelVehicleRate> data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelVehicleRate> getData() {
        return data;
    }

    public void setData(List<ModelVehicleRate> data) {
        this.data = data;
    }
}
