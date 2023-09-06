package com.logispark.parkingmanagementlogispark.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.logispark.parkingmanagementlogispark.Adapters.VehicleRateAdapter;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleSuccess;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleRate extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerViewRates;
    private VehicleRateAdapter vehicleRateAdapter;
    private DbHandler dbHandler;
    private String branchName;
    private List<ModelVehicleRate> modelVehicleRateList;
    private Call<ModelVehicleSuccess> modelVehicleSuccessCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_rate);

        branchName = SharedPreferenceManager.getmInstance(getApplicationContext()).getDeviceInformation().getBranchName();
        recyclerViewRates = findViewById(R.id.recycleViewVehicleRate);

        progressDialog = new ProgressDialog(VehicleRate.this);
        dbHandler = new DbHandler(getApplicationContext());
        modelVehicleRateList = dbHandler.getAllVehicleRate();

        if(modelVehicleRateList.size() == 0){

            showtoast(getString(R.string.NoData),R.drawable.error);
        }
        else {
            vehicleRateAdapter = new VehicleRateAdapter(getApplicationContext(),modelVehicleRateList);
            recyclerViewRates.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerViewRates.setAdapter(vehicleRateAdapter);
        }



    }

    /**
     * Custom Toast Generator
     * @param text
     * @param image
     */
    private void showtoast(String text, int image) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.customtoast);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText(text);
        Glide.with(getApplicationContext()).load(image).into(toastImage);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusync, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.navigation_sync) {

            progressDialog.setTitle("Fetching Data From Server");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            syncDataWithServer();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Syncing data with server i.e. fetching API from server
     */
    private void syncDataWithServer() {


        modelVehicleSuccessCall = RetrofitClient.getmInstance().getretrofit(getApplicationContext()).getVehicleRates(branchName);

        modelVehicleSuccessCall.enqueue(new Callback<ModelVehicleSuccess>() {
            @Override
            public void onResponse(Call<ModelVehicleSuccess> call, Response<ModelVehicleSuccess> response) {
                if(response.body() == null){
                    progressDialog.cancel();
                    showtoast(getString(R.string.unexpected_error),R.drawable.error);
                }else{
                    ModelVehicleSuccess modelVehicleSuccess = response.body();
                    modelVehicleRateList = modelVehicleSuccess.getData();
                    if(modelVehicleRateList.size() == 0){
                          //todo: show message data is null
                        progressDialog.cancel();

                    }else{

                        ArrayList<Boolean> successMessage = new ArrayList<>();
                        dbHandler.deleteAllVehicleRate();
                        for(ModelVehicleRate modelVehicleRate: modelVehicleRateList){
                            Boolean success = dbHandler.addVehicleRate(modelVehicleRate);
                            successMessage.add(success);
                        }

                        if(successMessage.size() == modelVehicleRateList.size()){
                            progressDialog.cancel();
                            showtoast(getString(R.string.syncSuccess),R.drawable.checked);
                            Intent intent = new Intent(VehicleRate.this,VehicleRate.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else{
                            progressDialog.cancel();
                            showtoast(getString(R.string.syncFailed),R.drawable.error);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelVehicleSuccess> call, Throwable t) {
                progressDialog.cancel();
                showtoast(getString(R.string.no_internet),R.drawable.nointernet);

            }
        });

    }


}