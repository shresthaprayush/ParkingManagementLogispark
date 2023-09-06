package com.logispark.parkingmanagementlogispark.models;

import java.util.List;

public class ModelLocations {

    private int id,serverId;
    private String name;
    private List<ModelSlots> slots;

    public ModelLocations(int id, int serverId, String name, List<ModelSlots> slots) {
        this.id = id;
        this.serverId = serverId;
        this.name = name;
        this.slots = slots;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ModelSlots> getSlots() {
        return slots;
    }

    public void setSlots(List<ModelSlots> slots) {
        this.slots = slots;
    }
}
