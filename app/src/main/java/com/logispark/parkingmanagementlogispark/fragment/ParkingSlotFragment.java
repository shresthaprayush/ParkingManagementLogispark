package com.logispark.parkingmanagementlogispark.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.logispark.parkingmanagementlogispark.Adapters.ParkingSlotsAdapter;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.models.ModelData;
import com.logispark.parkingmanagementlogispark.models.ModelLocationSuccess;
import com.logispark.parkingmanagementlogispark.models.ModelLocations;
import com.logispark.parkingmanagementlogispark.models.ModelSlotSuccessData;
import com.logispark.parkingmanagementlogispark.models.ModelSlots;
import com.logispark.parkingmanagementlogispark.models.ModelSlotsSucess;
import com.logispark.parkingmanagementlogispark.utilites.GridUtility;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParkingSlotFragment extends Fragment {

    private RecyclerView recyclerView;
    private Spinner spinner;
    private List<ModelSlots> modelSlotsList;
    private List<ModelLocations> modelLocations;
    private ModelSlotsSucess modelSlotsSucess;
    private String location;
    private Toast toast;
    private int i = 0;
    private ProgressBar progressBar;
    private Call<ModelSlotsSucess> callTableApi;

    private List<String> s = new ArrayList<>();
    private List<Integer> t = new ArrayList<>();
    private String branchName = "";
    private Call<ModelLocationSuccess> call;
    private ParkingSlotsAdapter parkingSlotsAdapter;
    private Spinner spinnerSlotLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_parking_slot, container, false);
        recyclerView = view.findViewById(R.id.recycleViewSlots);

        final Activity activity = getActivity();
        getActivity().setTitle("Parking Slots");

        toast = Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);

        branchName = SharedPreferenceManager.getmInstance(getContext()).getDeviceInformation().getBranchName();

        progressBar = view.findViewById(R.id.progressbarLoccation);
        spinnerSlotLocation = view.findViewById(R.id.spinnerSlotLocation);
        getData();



        return view;
    }

    private void getData() {

        call  = RetrofitClient.getmInstance().getretrofit(getContext()).getParkingLocations(branchName);
        call.enqueue(new Callback<ModelLocationSuccess>() {
            @Override
            public void onResponse(Call<ModelLocationSuccess> call, Response<ModelLocationSuccess> response) {
                if(response.body()!= null){

                    ModelLocationSuccess modelLocationSuccess = response.body();
                    ModelData modelData = modelLocationSuccess.getData();

                    modelLocations = modelData.getModelLocationsList();

                    if(modelLocations.size()!=0){

                        s.clear();
                        t.clear();
                        for (i = 0; i < modelLocations.size(); i++) {


                                s.add(modelLocations.get(i).getName());
                                t.add(modelLocations.get(i).getId());


                        }

                        ArrayAdapter<String> a = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, s);
                        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSlotLocation.setAdapter(a);

                        spinnerSlotLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                location = String.valueOf(position + 1);
                                filterlocation(position, location);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    else{
                        toast.setText("No Data Found");
                        toast.show();
                    };

                }
                else{
                    toast.setText("Error");
                    toast.show();

                }
            }

            @Override
            public void onFailure(Call<ModelLocationSuccess> call, Throwable t) {
                toast.setText("No Internet");
                toast.show();

                Log.e("Retrofit",String.valueOf(t));

            }
        });

    }

    private void filterlocation(int position, String location) {


        int location_id = t.get(position);
        progressBar.setVisibility(View.VISIBLE);
        calltableapi(location_id);

    }

    private void calltableapi(int location_id) {

        callTableApi = RetrofitClient.getmInstance().getretrofit(getContext()).getParkingSlots(location_id);
        callTableApi.enqueue(new Callback<ModelSlotsSucess>() {
            @Override
            public void onResponse(Call<ModelSlotsSucess> call, Response<ModelSlotsSucess> response) {
                if(response.body()!=null){

                    ModelSlotsSucess modelSlotsSucess = response.body();
                    if(modelSlotsSucess.getSuccess().equals("true")){
                        modelSlotsList = modelSlotsSucess.getData();
                        if(modelSlotsList.size()!=0){

                            int numberofColumns = GridUtility.calculateNoOfColumns(getContext(), 120);


                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),numberofColumns));
                            parkingSlotsAdapter = new ParkingSlotsAdapter(getContext(), modelSlotsList, getActivity());
                            recyclerView.setAdapter(parkingSlotsAdapter);
                            progressBar.setVisibility(View.GONE);

                        }
                        else{

                            progressBar.setVisibility(View.GONE);

                        }
                    }
                    else{
                        toast.setText("Empty Data");
                        toast.show();
                        progressBar.setVisibility(View.GONE);

                    }

                }
                else{
                    toast.setText("No Internet");
                    toast.show();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ModelSlotsSucess> call, Throwable t) {
                toast.setText("No Internet");
                toast.show();
                progressBar.setVisibility(View.GONE);

                Log.e("Retrofit",String.valueOf(t));
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        call.cancel();
        toast.cancel();

    }

    @Override
    public void onPause() {
        super.onPause();
        call.cancel();
        toast.cancel();

    }
}