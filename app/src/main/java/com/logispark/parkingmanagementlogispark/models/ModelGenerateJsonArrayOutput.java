package com.logispark.parkingmanagementlogispark.models;

import com.google.gson.JsonArray;

import java.util.ArrayList;

public class ModelGenerateJsonArrayOutput {
    private ArrayList<String> id;
    private JsonArray jsonArray;

    public ModelGenerateJsonArrayOutput(ArrayList<String> id, JsonArray jsonArray) {
        this.id = id;
        this.jsonArray = jsonArray;
    }

    public ArrayList<String> getId() {
        return id;
    }

    public void setId(ArrayList<String> id) {
        this.id = id;
    }

    public JsonArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JsonArray jsonArray) {
        this.jsonArray = jsonArray;
    }
}
