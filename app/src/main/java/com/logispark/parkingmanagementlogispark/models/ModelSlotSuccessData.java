package com.logispark.parkingmanagementlogispark.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelSlotSuccessData {

    @SerializedName("location")
    private String locationSlot;
    private List<ModelSlots> slots;

    public ModelSlotSuccessData(String locationSlot, List<ModelSlots> slots) {
        this.locationSlot = locationSlot;
        this.slots = slots;
    }

    public String getLocationSlot() {
        return locationSlot;
    }

    public void setLocationSlot(String locationSlot) {
        this.locationSlot = locationSlot;
    }

    public List<ModelSlots> getSlots() {
        return slots;
    }

    public void setSlots(List<ModelSlots> slots) {
        this.slots = slots;
    }
}
