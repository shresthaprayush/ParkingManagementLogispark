package com.logispark.parkingmanagementlogispark.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelDataService {

    @SerializedName("services")
    List<ModelService> modelServiceList;


    public ModelDataService(List<ModelService> modelServiceList) {
        this.modelServiceList = modelServiceList;
    }


    public List<ModelService> getModelServiceList() {
        return modelServiceList;
    }

    public void setModelServiceList(List<ModelService> modelServiceList) {
        this.modelServiceList = modelServiceList;
    }
}
