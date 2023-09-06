package com.logispark.parkingmanagementlogispark.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelData {

    @SerializedName("locations")
    List<ModelLocations> modelLocationsList;

    public ModelData(List<ModelLocations> modelLocationsList) {
        this.modelLocationsList = modelLocationsList;
    }

    public List<ModelLocations> getModelLocationsList() {
        return modelLocationsList;
    }

    public void setModelLocationsList(List<ModelLocations> modelLocationsList) {
        this.modelLocationsList = modelLocationsList;
    }
}
