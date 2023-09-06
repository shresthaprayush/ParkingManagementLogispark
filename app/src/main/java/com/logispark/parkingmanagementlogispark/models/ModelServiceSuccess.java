package com.logispark.parkingmanagementlogispark.models;

public class ModelServiceSuccess {

    private String success;
    private String message;
    private ModelDataService data;

    public ModelServiceSuccess(String success, String message, ModelDataService data) {
        this.success = success;
        this.message = message;
        this.data = data;
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

    public ModelDataService getData() {
        return data;
    }

    public void setData(ModelDataService data) {
        this.data = data;
    }
}
