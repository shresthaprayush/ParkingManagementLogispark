package com.logispark.parkingmanagementlogispark.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonArray;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.main.LoginActivity;
import com.logispark.parkingmanagementlogispark.main.MainActivity;
import com.logispark.parkingmanagementlogispark.main.RatesSlotsLocationList;
import com.logispark.parkingmanagementlogispark.models.ModelDeviceSpecificInformation;
import com.logispark.parkingmanagementlogispark.models.ModelGenerateJsonArrayOutput;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelSucessSaveData;
import com.logispark.parkingmanagementlogispark.models.ModelUser;
import com.logispark.parkingmanagementlogispark.models.ModelVehicle;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.JsonConvertor;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFrag extends Fragment {


    TextView profilename, textViewprofilenumber, textViewbranch, txtbranchCode;
    String name, contact, branch, branchCode;
    private boolean syncStatus;
    private Switch syncSwitch;
    private View view;
    private Call<ModelSucessSaveData> callSaveSuccessData;
    private ProgressDialog progressDialog;
    private ModelDeviceSpecificInformation modelDeviceSpecificInformation;
    CircleImageView circleimageview;
    private RelativeLayout relativeLayoutSetting, relativeLayoutLogout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register_user, container, false);

        final Activity activity = getActivity();
        getActivity().setTitle("Profile");


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.SyncingData));

        relativeLayoutSetting = view.findViewById(R.id.rrSetting);
        circleimageview = view.findViewById(R.id.profile_imageviewprofiles);
        Glide.with(getActivity()).load(R.drawable.logoparking).into(circleimageview);

        relativeLayoutLogout = view.findViewById(R.id.rrLogout);

        relativeLayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                runSyncApi();


            }
        });

        syncSwitch = view.findViewById(R.id.switchautosync);
        modelDeviceSpecificInformation = SharedPreferenceManager.getmInstance(getContext()).getDeviceInformation();


        syncStatus = modelDeviceSpecificInformation.isSysncStatus();
        syncSwitch.setChecked(syncStatus);

        syncSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                modelDeviceSpecificInformation.setSysncStatus(syncSwitch.isChecked());

                SharedPreferenceManager.getmInstance(getContext()).save_deviceInformation(modelDeviceSpecificInformation);
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        profilename = view.findViewById(R.id.profile_name);
        textViewprofilenumber = view.findViewById(R.id.profile_username);
        textViewbranch = view.findViewById(R.id.profile_slug);

        txtbranchCode = view.findViewById(R.id.profile_branchCode);


        relativeLayoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RatesSlotsLocationList.class);
                startActivity(intent);
            }
        });
        name = SharedPreferenceManager.getmInstance(getContext()).getUser().getName();
        contact = SharedPreferenceManager.getmInstance(getContext()).getUser().getContact();
        branch = SharedPreferenceManager.getmInstance(getContext()).getDeviceInformation().getBranchName();
        branchCode = SharedPreferenceManager.getmInstance(getContext()).getDeviceInformation().getBranchCode();


        profilename.setText(name);
        textViewbranch.setText(branch);
        textViewprofilenumber.setText(contact);
        txtbranchCode.setText(branchCode);


        return view;


    }

    private void runSyncApi() {

        //todo:get unsyncedData from server and sync data

        DbHandler dbHandler = new DbHandler(getContext());

        String branch_name = modelDeviceSpecificInformation.getBranchName();
        List<ModelParkingData> modelParkingDataList = dbHandler.getUnsyncedData();

        if (modelParkingDataList.size() < 1) {
            showtoast(getString(R.string.notDataToSync), R.drawable.checked);
            progressDialog.cancel();
            SharedPreferenceManager.getmInstance(getContext()).login_user(false);
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else {
            ModelGenerateJsonArrayOutput modelGenerateJsonArrayOutput = new JsonConvertor(getContext()).generateJsonArray(modelParkingDataList);

            ArrayList<String> idAlist = modelGenerateJsonArrayOutput.getId();
            String[] idString = idAlist.toArray(new String[idAlist.size()]);

            JsonArray payload = modelGenerateJsonArrayOutput.getJsonArray();

            callSaveSuccessData = RetrofitClient.getmInstance().getretrofit(getContext()).saveDataInServer(branch_name, payload);
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
                            progressDialog.cancel();


                            SharedPreferenceManager.getmInstance(getContext()).login_user(false);
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            Log.d("Sync Update", "Syncing Success");



                        } else {
                            Log.d("Sync Update", "Syncing Failed");
                            progressDialog.cancel();

                        }
                    } else {
                        Log.d("Sync Update", "Syncing Failed");

                        progressDialog.cancel();

                    }
                }

                @Override
                public void onFailure(Call<ModelSucessSaveData> call, Throwable t) {
                    progressDialog.cancel();

                    Log.d("Sync Update", "Syncing Failed" + String.valueOf(t));


                }
            });
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
        View layout = layoutInflater.inflate(R.layout.toast_layout, (ViewGroup) view.findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.customtoast);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText(text);
        Glide.with(getContext()).load(image).into(toastImage);

        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }


}