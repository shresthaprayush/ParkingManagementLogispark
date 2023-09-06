package com.logispark.parkingmanagementlogispark.models;

public class ModelEstimate {

    private String date;
    private String rate;
    private String totalCost;
    private String duration;
    private String checkInTime;
    private String discount;
    private String vehicleType;
    private String vehicleNumber;
    private String washing;
    private String accommodation;
    private String subTotal;
    private String uniqueId;


    public ModelEstimate(String date, String rate, String totalCost, String duration, String checkInTime, String discount, String vehicleType, String vehicleNumber, String washing, String accommodation, String subTotal, String uniqueId) {
        this.date = date;
        this.rate = rate;
        this.totalCost = totalCost;
        this.duration = duration;
        this.checkInTime = checkInTime;
        this.discount = discount;
        this.vehicleType = vehicleType;
        this.vehicleNumber = vehicleNumber;
        this.washing = washing;
        this.accommodation = accommodation;
        this.subTotal = subTotal;
        this.uniqueId = uniqueId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getWashing() {
        return washing;
    }

    public void setWashing(String washing) {
        this.washing = washing;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
