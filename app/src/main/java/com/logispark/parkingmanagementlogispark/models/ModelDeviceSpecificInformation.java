package com.logispark.parkingmanagementlogispark.models;

public class ModelDeviceSpecificInformation {
    private String branchName;
    private String uniqueId;
    private String lastUpdated;
    private String branchCode;
    private boolean sysncStatus;


    public ModelDeviceSpecificInformation(String branchName, String uniqueId, String lastUpdated, String branchCode, boolean sysncStatus) {
        this.branchName = branchName;
        this.uniqueId = uniqueId;
        this.lastUpdated = lastUpdated;
        this.branchCode = branchCode;
        this.sysncStatus = sysncStatus;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public boolean isSysncStatus() {
        return sysncStatus;
    }

    public void setSysncStatus(boolean sysncStatus) {
        this.sysncStatus = sysncStatus;
    }
}
