package com.logispark.parkingmanagementlogispark.main;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
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

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.logispark.parkingmanagementlogispark.Adapters.ViewPagerAdapter;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.fragment.ServiceRateFragment;
import com.logispark.parkingmanagementlogispark.fragment.VehicleLocationFragment;
import com.logispark.parkingmanagementlogispark.fragment.VehicleRateFragment;
import com.logispark.parkingmanagementlogispark.models.ModelData;
import com.logispark.parkingmanagementlogispark.models.ModelDataService;
import com.logispark.parkingmanagementlogispark.models.ModelDeviceSpecificInformation;
import com.logispark.parkingmanagementlogispark.models.ModelLocationSuccess;
import com.logispark.parkingmanagementlogispark.models.ModelLocations;
import com.logispark.parkingmanagementlogispark.models.ModelService;
import com.logispark.parkingmanagementlogispark.models.ModelServiceSuccess;
import com.logispark.parkingmanagementlogispark.models.ModelSlots;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleSuccess;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatesSlotsLocationList extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private String branchName;
    private DbHandler dbHandler;
    private TextView textViewLastUpdated;
    private ModelDeviceSpecificInformation modelDeviceSpecificInformation;
    private ModelLocationSuccess modelLocationSuccess;
    private Call<ModelLocationSuccess> callLocations;
    private Call<ModelServiceSuccess> callService;
    String lastUpdated;

    private Call<ModelVehicleSuccess> modelVehicleSuccessCall;
    private List<ModelVehicleRate> modelVehicleRateList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_slots_location_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        textViewLastUpdated = findViewById(R.id.lastUpdated);
        modelDeviceSpecificInformation = SharedPreferenceManager.getmInstance(getApplicationContext()).getDeviceInformation();

        if (modelDeviceSpecificInformation.getLastUpdated() == null) {
            textViewLastUpdated.setText(getString(R.string.lastUpdatedEmpty));
        } else {
            textViewLastUpdated.setText("Last Updated : " + modelDeviceSpecificInformation.getLastUpdated());
        }

        dbHandler = new DbHandler(getApplicationContext());
        branchName = modelDeviceSpecificInformation.getBranchName();

        progressDialog = new ProgressDialog(RatesSlotsLocationList.this);

        final TabLayout tabLayout = findViewById(R.id.tablayout);
        final ViewPager viewPager = findViewById(R.id.view_rate_slots_location_list);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ServiceRateFragment(), "Service");
        viewPagerAdapter.addFragment(new VehicleRateFragment(), "Rate");
        viewPagerAdapter.addFragment(new VehicleLocationFragment(), "Locations");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
            syncServices();
            syncSlotandLocationDataWithServer();
            syncDataWithServer();

        }
        else if(id == android.R.id.home){

            Intent intent = new Intent(RatesSlotsLocationList.this,MainActivity.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }

    private void syncSlotandLocationDataWithServer() {

        dbHandler.dellAllVehicleSlot();
        dbHandler.deleteAllVehicleArea();
        callLocations = RetrofitClient.getmInstance().getretrofit(getApplicationContext()).getParkingLocations(branchName);
        callLocations.enqueue(new Callback<ModelLocationSuccess>() {
            @Override
            public void onResponse(Call<ModelLocationSuccess> call, Response<ModelLocationSuccess> response) {
                if (response.body() == null) {

                    showtoast(getString(R.string.unexpected_error), R.drawable.error);
                } else {
                    modelLocationSuccess = response.body();
                    ModelData modelData = modelLocationSuccess.getData();
                    List<ModelLocations> modelLocationList = modelData.getModelLocationsList();
                    if (modelLocationList.size() != 0) {

                        for (ModelLocations modelLocation : modelLocationList) {
                            dbHandler.addLocation(modelLocation);
                            List<ModelSlots> modelSlots = modelLocation.getSlots();
                            for (ModelSlots modelSlots1 : modelSlots) {
                                modelSlots1.setLocation(modelLocation.getName());
                                dbHandler.addSlots(modelSlots1);
                            }
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<ModelLocationSuccess> call, Throwable t) {
                showtoast(getString(R.string.no_internet) + t, R.drawable.nointernet);

            }
        });

    }


    private void syncServices() {

        dbHandler.deleteAllServices();
        callService = RetrofitClient.getmInstance().getretrofit(getApplicationContext()).getServices(branchName);
        callService.enqueue(new Callback<ModelServiceSuccess>() {
            @Override
            public void onResponse(Call<ModelServiceSuccess> call, Response<ModelServiceSuccess> response) {
                if (response.body() == null) {

                    showtoast(getString(R.string.unexpected_error), R.drawable.error);
                } else {
                    ModelServiceSuccess modelServiceSuccess = response.body();
                    ModelDataService modelData = modelServiceSuccess.getData();
                    List<ModelService> modelServiceList = modelData.getModelServiceList();
                    if (modelServiceList.size() != 0) {

                        for (ModelService modelService : modelServiceList) {
                            dbHandler.addService(modelService);

                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<ModelServiceSuccess> call, Throwable t) {
                showtoast(getString(R.string.no_internet) + t, R.drawable.nointernet);

            }
        });

    }


    /**
     * Custom Toast Generator
     *
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


    /**
     * Syncing data with server i.e. fetching API from server
     */
    private void syncDataWithServer() {


        modelVehicleSuccessCall = RetrofitClient.getmInstance().getretrofit(getApplicationContext()).getVehicleRates(branchName);

        modelVehicleSuccessCall.enqueue(new Callback<ModelVehicleSuccess>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ModelVehicleSuccess> call, Response<ModelVehicleSuccess> response) {
                if (response.body() == null) {
                    progressDialog.cancel();
                    showtoast(getString(R.string.unexpected_error), R.drawable.error);
                } else {
                    ModelVehicleSuccess modelVehicleSuccess = response.body();
                    modelVehicleRateList = modelVehicleSuccess.getData();
                    if (modelVehicleRateList.size() == 0) {
                        //todo: show message data is null
                        progressDialog.cancel();

                    } else {

                        ArrayList<Boolean> successMessage = new ArrayList<>();
                        dbHandler.deleteAllVehicleRate();
                        for (ModelVehicleRate modelVehicleRate : modelVehicleRateList) {
                            Boolean success = dbHandler.addVehicleRate(modelVehicleRate);
                            successMessage.add(success);
                        }

                        if (successMessage.size() == modelVehicleRateList.size()) {
                            progressDialog.cancel();
                            try{
                                Date date = new Date();

                                lastUpdated = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(date);

                            }
                            catch (Exception e){

                                LocalDateTime myObj = LocalDateTime.now();
                                lastUpdated = myObj.toString();
                            }

                            modelDeviceSpecificInformation.setLastUpdated(lastUpdated);
                            SharedPreferenceManager.getmInstance(getApplicationContext()).save_deviceInformation(modelDeviceSpecificInformation);

                            showtoast(getString(R.string.syncSuccess), R.drawable.checked);
                            Intent intent = new Intent(RatesSlotsLocationList.this, RatesSlotsLocationList.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                            startActivity(intent);

                        } else {
                            progressDialog.cancel();
                            showtoast(getString(R.string.syncFailed), R.drawable.error);

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelVehicleSuccess> call, Throwable t) {
                progressDialog.cancel();
                showtoast(getString(R.string.no_internet), R.drawable.nointernet);

            }
        });

    }




}