package com.logispark.parkingmanagementlogispark.models;

public class ModelSucessSaveData {

    private String success,message;

    public ModelSucessSaveData(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
