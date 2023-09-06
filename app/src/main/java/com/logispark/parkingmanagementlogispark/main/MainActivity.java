package com.logispark.parkingmanagementlogispark.main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonArray;
import com.logispark.parkingmanagementlogispark.IminPrinter.IminPrinterHelper;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.fragment.DataFragment;
import com.logispark.parkingmanagementlogispark.fragment.ParkingSlotFragment;
import com.logispark.parkingmanagementlogispark.fragment.ProfileFrag;
import com.logispark.parkingmanagementlogispark.fragment.VechileCheckIn;
import com.logispark.parkingmanagementlogispark.models.ModelDeviceSpecificInformation;
import com.logispark.parkingmanagementlogispark.models.ModelGenerateJsonArrayOutput;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelSucessSaveData;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.JsonConvertor;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;
import com.logispark.parkingmanagementlogispark.utilites.Sync.NetworkStateChecker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    int Requestcamerapermissionid = 1001;
    int Requestexternalpermmisionid = 1002;
    private static final int REQUEST_CODE = 1;
    private ProgressDialog dialog;
    private BroadcastReceiver broadcastReceiver;
    private ModelDeviceSpecificInformation modelDeviceSpecificInformation;
    private Call<ModelSucessSaveData> callSaveSuccessData;

    Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fragment = null;


            if (item.getItemId() == R.id.navigation_qr) {

                Intent intent = new Intent(MainActivity.this, QrActivity.class);
                startActivity(intent);
            } else {
                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                        fragment = new ProfileFrag();
                        break;
//
                    case R.id.navigation_history:
                        fragment = new DataFragment();
                        break;

//                    case R.id.navigation_today:
//                        fragment = new ParkingSlotFragment();
//                        break;

                    case R.id.navigation_checkIn:
                        fragment = new VechileCheckIn();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                        fragment).commit();

            }


            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(MainActivity.this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer,
                new VechileCheckIn()).commit();

//        IminPrinterHelper iminPrinterHelper = new IminPrinterHelper(getApplicationContext(),this);
//        iminPrinterHelper.initPrinter();

        modelDeviceSpecificInformation = SharedPreferenceManager.getmInstance(getApplicationContext()).getDeviceInformation();

//        if (!modelDeviceSpecificInformation.isSysncStatus()) {
//            hideItem();
//        }

        broadcastReceiver = new NetworkStateChecker();
        registerNetworkBroadcastForNougat();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, Requestcamerapermissionid);

            }
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Requestexternalpermmisionid);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Requestexternalpermmisionid);
                return;
            }
        }

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            //Requesting the permission
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CODE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == R.id.navigation_syncMain) {

            dialog.setTitle("Syncing Data");
            dialog.show();
            synData();

        }

        return super.onOptionsItemSelected(item);
    }

    private void synData() {

        //todo:get unsyncedData from server and sync data


        DbHandler dbHandler = new DbHandler(getApplicationContext());

        String branch_name = modelDeviceSpecificInformation.getBranchName();
        List<ModelParkingData> modelParkingDataList = dbHandler.getUnsyncedData();

        if (modelParkingDataList.size() < 1) {
            showtoast(getString(R.string.notDataToSync), R.drawable.checked);
            dialog.cancel();
        } else {
            ModelGenerateJsonArrayOutput modelGenerateJsonArrayOutput = new JsonConvertor(getApplicationContext()).generateJsonArray(modelParkingDataList);

            ArrayList<String> idAlist = modelGenerateJsonArrayOutput.getId();
            String[] idString = idAlist.toArray(new String[idAlist.size()]);

            JsonArray payload = modelGenerateJsonArrayOutput.getJsonArray();

            callSaveSuccessData = RetrofitClient.getmInstance().getretrofit(getApplicationContext()).saveDataInServer(branch_name, payload);
            callSaveSuccessData.enqueue(new Callback<ModelSucessSaveData>() {
                @Override
                public void onResponse(Call<ModelSucessSaveData> call, Response<ModelSucessSaveData> response) {

                    if (response.body() != null) {


                        ModelSucessSaveData modelSucessSaveData = response.body();
                        if (modelSucessSaveData.getSuccess().equals("true")) {


                            for (String id : idString) {
                                dbHandler.updateSyncOnParkingData(Integer.parseInt(id));

                            }
                            showtoast(getString(R.string.syncSuccess), R.drawable.checked);
                            dialog.cancel();

                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            Log.d("Sync Update", "Syncing Success");


                        } else {
                            Log.d("Sync Update", "Syncing Failed");
                            dialog.cancel();

                        }
                    } else {
                        Log.d("Sync Update", "Syncing Failed");

                        dialog.cancel();

                    }
                }

                @Override
                public void onFailure(Call<ModelSucessSaveData> call, Throwable t) {
                    dialog.cancel();

                    Log.d("Sync Update", "Syncing Failed" + String.valueOf(t));


                }
            });
        }


    }


//    private void hideItem() {
//        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);
//        Menu nav_Menu = navigationView.getMenu();
//        nav_Menu.findItem(R.id.navigation_today).setVisible(false);
//    }


    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
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


}