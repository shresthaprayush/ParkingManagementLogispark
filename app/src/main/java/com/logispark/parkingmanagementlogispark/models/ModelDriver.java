package com.logispark.parkingmanagementlogispark.models;

public class ModelDriver {
    private int id;
    private String name,panNumber,contactNumber,licenceNumber,placeOfOrigin,nationality,customerCode;


    public ModelDriver(int id, String name, String panNumber, String contactNumber, String licenceNumber, String placeOfOrigin, String nationality, String customerCode) {
        this.id = id;
        this.name = name;
        this.panNumber = panNumber;
        this.contactNumber = contactNumber;
        this.licenceNumber = licenceNumber;
        this.placeOfOrigin = placeOfOrigin;
        this.nationality = nationality;
        this.customerCode = customerCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
