package com.logispark.parkingmanagementlogispark.models;

import java.util.List;

public class ModelLocationSuccess {

    private String success;
    private String message;
    private ModelData data;

    public ModelLocationSuccess(String success, String message, ModelData data) {
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

    public ModelData getData() {
        return data;
    }

    public void setData(ModelData data) {
        this.data = data;
    }
}
