package com.logispark.parkingmanagementlogispark.utilites;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.logispark.parkingmanagementlogispark.models.ModelActivateTable;
import com.logispark.parkingmanagementlogispark.models.ModelLocationSuccess;
import com.logispark.parkingmanagementlogispark.models.ModelLocations;
import com.logispark.parkingmanagementlogispark.models.ModelServerParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelServiceSuccess;
import com.logispark.parkingmanagementlogispark.models.ModelSlotsSucess;
import com.logispark.parkingmanagementlogispark.models.ModelSuccessLogin;
import com.logispark.parkingmanagementlogispark.models.ModelSucessSaveData;
import com.logispark.parkingmanagementlogispark.models.ModelUser;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleSuccess;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {

    /**
     * Authenticate User
     * @param contactNumber
     * @param password
     * @param uniqueDeviceId
     * @return Success Login Model
     */
    @FormUrlEncoded
    @POST("login")
    Call<ModelSuccessLogin> loginUser(
            @Field("contact") String contactNumber,
            @Field("password") String password,
            @Field("uniqueDeviceId") String uniqueDeviceId
    );

    /**
     * Get Parking locations based on branch
     * @param branchName
     * @return Model Parking Location
     */
    @GET("parking-locations/{branchName}")
    Call<ModelLocationSuccess> getParkingLocations(
            @Path("branchName") String branchName
    );

    /**
     * Get Parking Slots Based on Location
     * @param locationId
     * @return Parking Slot of that parking location
     */
    @GET("parking-slots/{locationId}")
    Call<ModelSlotsSucess> getParkingSlots(
            @Path("locationId") int locationId
    );


    /**
     * Get Service Based on Branch Name
     * @param branchName
     * @return Services on that branch
     */
    @GET("services/{branchName}")
    Call<ModelServiceSuccess> getServices(
            @Path("branchName") String branchName
    );

    @GET("{slot_id}/toggle")
    Call<ModelActivateTable> activateSlot(
            @Path("slot_id") int slot_id
    );

    /**
     * Get vehicle rate specifi to branch
     * @param branch_name
     * @return
     */
    @GET("vehicle-rate/{branchName}")
    Call<ModelVehicleSuccess> getVehicleRates(
            @Path("branchName") String branch_name
    );



    //Todo:Change Based On server
    @POST("parking-sales/{branch-name}")
    Call<ModelSucessSaveData> saveDataInServer(
            @Path("branch-name") String branchName,
            @Body JsonArray body);


}
