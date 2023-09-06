package com.logispark.parkingmanagementlogispark.models;

public class ModelParkingData {

    public int id,rate,slot,active,discount,duration,sync,tokenNo,accommodation,washing;
    public String ticketCode,inTime,outTime,vehicleNumber,createdAt,slotName;
    public double amount;
    public long vechileId,driverId;


    public ModelParkingData(int id, int rate, int slot, int active, int discount, int duration, int sync, int tokenNo, int accommodation, int washing, String ticketCode, String inTime, String outTime, String vehicleNumber, String createdAt, String slotName, double amount, long vechileId, long driverId) {
        this.id = id;
        this.rate = rate;
        this.slot = slot;
        this.active = active;
        this.discount = discount;
        this.duration = duration;
        this.sync = sync;
        this.tokenNo = tokenNo;
        this.accommodation = accommodation;
        this.washing = washing;
        this.ticketCode = ticketCode;
        this.inTime = inTime;
        this.outTime = outTime;
        this.vehicleNumber = vehicleNumber;
        this.createdAt = createdAt;
        this.slotName = slotName;
        this.amount = amount;
        this.vechileId = vechileId;
        this.driverId = driverId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(int tokenNo) {
        this.tokenNo = tokenNo;
    }

    public int getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(int accommodation) {
        this.accommodation = accommodation;
    }

    public int getWashing() {
        return washing;
    }

    public void setWashing(int washing) {
        this.washing = washing;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getVechileId() {
        return vechileId;
    }

    public void setVechileId(long vechileId) {
        this.vechileId = vechileId;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }
}
