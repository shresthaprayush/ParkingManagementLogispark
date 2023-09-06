package com.logispark.parkingmanagementlogispark.utilites.Sync;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.models.ModelDriver;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelServerParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelSuccessLogin;
import com.logispark.parkingmanagementlogispark.models.ModelSucessSaveData;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.JsonConvertor;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkStateChecker extends BroadcastReceiver {

    private Context context;
    private DbHandler db;
    private Call<ModelSucessSaveData> callSaveSuccessData;

    @Override
    public void onReceive(Context context, Intent intent) {


        this.context = context;
        db = new DbHandler(context);
        boolean syncStatus = SharedPreferenceManager.getmInstance(context).getDeviceInformation().isSysncStatus();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (isOnline(context)) {

                    if (syncStatus) {
                        savedata(context,db);

                    }


                } else {
                    System.out.println("No network");
                }


            }
        }, 20000);


    }

    private void savedata(Context context, DbHandler db) {

        List<ModelParkingData> modelParkingDataList = db.getUnsyncedData();
        for (ModelParkingData mData : modelParkingDataList) {
            //todo:create data on basis of server
            ModelDriver modelDriver = db.searchDriver(mData.getDriverId());
            saveorderdetailstoservertable(mData, mData.id,modelDriver);
        }

    }

    private void saveorderdetailstoservertable(ModelParkingData modelServerParkingData, int id, ModelDriver modelDriver) {

        DbHandler dbHandler = new DbHandler(context);

        String branch_name = SharedPreferenceManager.getmInstance(context).getDeviceInformation().getBranchName();
        String url;
//        if (branch_name.toLowerCase().trim().equals("test")) {
//            url = DBHandler.URL_TABLE_TEST;
//        } else {
//            url = DBHandler.URL_TABLE;
//        }


        JsonArray payload = new JsonConvertor(context).generateJson(modelServerParkingData,modelDriver);


        callSaveSuccessData = RetrofitClient.getmInstance().getretrofit(context).saveDataInServer(branch_name, payload);
        callSaveSuccessData.enqueue(new Callback<ModelSucessSaveData>() {
            @Override
            public void onResponse(Call<ModelSucessSaveData> call, Response<ModelSucessSaveData> response) {

                if (response.body() != null) {


                    ModelSucessSaveData modelSucessSaveData = response.body();
                    if (modelSucessSaveData.getSuccess().equals("true")) {

                            dbHandler.updateSyncOnParkingData(modelServerParkingData.id);
                            Log.d("Sync Update","Syncing Success");


                    } else {
                        Log.d("Sync Update","Syncing Failed");

                    }
                } else {
                    Log.d("Sync Update","Syncing Failed");


                }
            }

            @Override
            public void onFailure(Call<ModelSucessSaveData> call, Throwable t) {

                Log.d("Sync Update","Syncing Failed"+String.valueOf(t));


            }
        });


    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }


}
