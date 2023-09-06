package com.logispark.parkingmanagementlogispark.models;

public class ModelVehicle {
    private int id;
    private String vehicle_no, type;
    private long userId;

    public ModelVehicle(int id, String vehicle_no, String type, long userId) {
        this.id = id;
        this.vehicle_no = vehicle_no;
        this.type = type;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}