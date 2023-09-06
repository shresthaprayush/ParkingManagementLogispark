package com.logispark.parkingmanagementlogispark.models;

import java.util.List;

public class ModelSlotsSucess {
    private String success;
    private String message;
    private List<ModelSlots> data;

    public ModelSlotsSucess(String success, String message, List<ModelSlots> data) {
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

    public List<ModelSlots> getData() {
        return data;
    }

    public void setData(List<ModelSlots> data) {
        this.data = data;
    }
}
