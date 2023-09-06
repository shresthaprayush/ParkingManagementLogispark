package com.logispark.parkingmanagementlogispark.models;

public class ModelPrintTable {

    int parkingTableID;
    int estimateCount;
    int slipCount;


    public ModelPrintTable(int parkingTableID, int estimateCount, int slipCount) {
        this.parkingTableID = parkingTableID;
        this.estimateCount = estimateCount;
        this.slipCount = slipCount;
    }

    public int getParkingTableID() {
        return parkingTableID;
    }

    public void setParkingTableID(int parkingTableID) {
        this.parkingTableID = parkingTableID;
    }

    public int getEstimateCount() {
        return estimateCount;
    }

    public void setEstimateCount(int estimateCount) {
        this.estimateCount = estimateCount;
    }

    public int getSlipCount() {
        return slipCount;
    }

    public void setSlipCount(int slipCount) {
        this.slipCount = slipCount;
    }
}
