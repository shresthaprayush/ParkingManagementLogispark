package com.logispark.parkingmanagementlogispark.models;

public class ModelSlots {

    private int id,active,serverId;
    private String name,location;

    public ModelSlots(int id, int active, int serverId, String name, String location) {
        this.id = id;
        this.active = active;
        this.serverId = serverId;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
