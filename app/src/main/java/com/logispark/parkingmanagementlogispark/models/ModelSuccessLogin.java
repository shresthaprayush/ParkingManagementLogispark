package com.logispark.parkingmanagementlogispark.models;

public class ModelSuccessLogin {

    String success,message;
    ModelUser data;

    public ModelSuccessLogin(String success, String message, ModelUser data) {
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

    public ModelUser getData() {
        return data;
    }

    public void setData(ModelUser data) {
        this.data = data;
    }
}
