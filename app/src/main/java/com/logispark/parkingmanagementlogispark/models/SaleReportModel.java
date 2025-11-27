package com.logispark.parkingmanagementlogispark.models;

import java.util.List;
import java.util.Map;

public class SaleReportModel {

    private String date;
    private String totalSale;
    private String totalBillIssued;
    private Map<String, double[]> vehicleSales;

    public SaleReportModel(String date, String totalSale, String totalBillIssued, Map<String, double[]> vehicleSales) {
        this.date = date;
        this.totalSale = totalSale;
        this.totalBillIssued = totalBillIssued;
        this.vehicleSales = vehicleSales;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(String totalSale) {
        this.totalSale = totalSale;
    }

    public String getTotalBillIssued() {
        return totalBillIssued;
    }

    public void setTotalBillIssued(String totalBillIssued) {
        this.totalBillIssued = totalBillIssued;
    }

    public Map<String, double[]> getVehicleSales() {
        return vehicleSales;
    }

    public void setVehicleSales(Map<String, double[]> vehicleSales) {
        this.vehicleSales = vehicleSales;
    }
}
