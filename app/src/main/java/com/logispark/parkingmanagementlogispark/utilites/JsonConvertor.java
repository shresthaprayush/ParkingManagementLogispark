package com.logispark.parkingmanagementlogispark.utilites;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.logispark.parkingmanagementlogispark.models.ModelDriver;
import com.logispark.parkingmanagementlogispark.models.ModelGenerateJsonArrayOutput;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelVehicle;

import java.util.ArrayList;
import java.util.List;

public class JsonConvertor {

    private Context context;

    public JsonConvertor(Context context) {
        this.context = context;
    }

    public JsonArray generateJson(ModelParkingData modelParkingData, ModelDriver modelDriver) {

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        jsonObject.addProperty("ticketCode", modelParkingData.getTicketCode());
        jsonObject.addProperty("vehicleNo", modelParkingData.getVehicleNumber());
        jsonObject.addProperty("inTime", modelParkingData.getInTime());
        jsonObject.addProperty("outTime", modelParkingData.getOutTime());
        jsonObject.addProperty("createdAt", modelParkingData.getCreatedAt());
        jsonObject.addProperty("rate", modelParkingData.getRate());
        jsonObject.addProperty("discount", modelParkingData.getDiscount());
        jsonObject.addProperty("amount", modelParkingData.getAmount());
        jsonObject.addProperty("slotId", modelParkingData.getSlot());
        jsonObject.addProperty("tokenNo", modelParkingData.getTokenNo());
        jsonObject.addProperty("duration", modelParkingData.getDuration());
        jsonObject.addProperty("license", modelDriver.getLicenceNumber());
        jsonObject.addProperty("pan", modelDriver.getPanNumber());
        jsonObject.addProperty("name", modelDriver.getName());
        jsonObject.addProperty("contact", modelDriver.getContactNumber());
        jsonObject.addProperty("origin", modelDriver.getPlaceOfOrigin());
        jsonObject.addProperty("accommodation", modelParkingData.getAccommodation());
        jsonObject.addProperty("washing", modelParkingData.getWashing());
        jsonObject.addProperty("nationality", modelDriver.getNationality());


        jsonArray.add(jsonObject);

        return jsonArray;

    }


    public ModelGenerateJsonArrayOutput generateJsonArray(List<ModelParkingData> modelParkingDataList) {

        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        ArrayList<String> id = new ArrayList<>();

        DbHandler dbHandler = new DbHandler(context);

        for (ModelParkingData modelParkingData : modelParkingDataList) {

            ModelDriver modelDriver = dbHandler.searchDriver(modelParkingData.getDriverId());


            jsonObject.addProperty("ticketCode", modelParkingData.getTicketCode());
            jsonObject.addProperty("vehicleNo", modelParkingData.getVehicleNumber());
            jsonObject.addProperty("inTime", modelParkingData.getInTime());
            jsonObject.addProperty("outTime", modelParkingData.getOutTime());
            jsonObject.addProperty("createdAt", modelParkingData.getCreatedAt());
            jsonObject.addProperty("rate", modelParkingData.getRate());
            jsonObject.addProperty("discount", modelParkingData.getDiscount());
            jsonObject.addProperty("amount", modelParkingData.getAmount());
            jsonObject.addProperty("slotId", modelParkingData.getSlot());
            jsonObject.addProperty("tokenNo", modelParkingData.getTokenNo());
            jsonObject.addProperty("duration", modelParkingData.getDuration());
            jsonObject.addProperty("license", modelDriver.getLicenceNumber());
            jsonObject.addProperty("pan", modelDriver.getPanNumber());
            jsonObject.addProperty("name", modelDriver.getName());
            jsonObject.addProperty("contact", modelDriver.getContactNumber());
            jsonObject.addProperty("origin", modelDriver.getPlaceOfOrigin());
            jsonObject.addProperty("accommodation", modelParkingData.getAccommodation());
            jsonObject.addProperty("washing", modelParkingData.getWashing());
            jsonObject.addProperty("nationality", modelDriver.getNationality());


            jsonArray.add(jsonObject);
            id.add(String.valueOf(modelParkingData.id));


        }


        return new ModelGenerateJsonArrayOutput(id, jsonArray);

    }

}
