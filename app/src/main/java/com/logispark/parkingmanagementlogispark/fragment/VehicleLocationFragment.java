package com.logispark.parkingmanagementlogispark.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.logispark.parkingmanagementlogispark.models.ModelLocations;
import com.logispark.parkingmanagementlogispark.models.ModelSlots;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.GridUtility;

import java.util.ArrayList;
import java.util.List;

public class VehicleLocationFragment extends Fragment {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Spinner spinnerSlotLocation;
    private DbHandler dbHandler;
    private List<ModelLocations> modelLocations;
    private ParkingSlotsAdapter parkingSlotsAdapter;
    private List<ModelSlots> modelSlotsList;
    private List<String>  locationNames = new ArrayList<>();
    private String selectedLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Activity activity = getActivity();
        activity.setTitle("Location List");

        View view = inflater.inflate(R.layout.fragment_vehicle_location, container, false);
        dbHandler = new DbHandler(getContext());
        recyclerView = view.findViewById(R.id.recycleViewLocationSlots);
        progressBar = view.findViewById(R.id.progressBarListSlots);
        spinnerSlotLocation = view.findViewById(R.id.spinnerListSlots);
        progressBar.setVisibility(View.VISIBLE);

        populateData();

        return view;

    }

    private void populateData() {

        modelLocations = dbHandler.getAllArea();

        for (ModelLocations modelLocations:modelLocations){

            locationNames.add(modelLocations.getName());

        }

        ArrayAdapter<String> a = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, locationNames);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSlotLocation.setAdapter(a);

        spinnerSlotLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                progressBar.setVisibility(View.VISIBLE);
                selectedLocation = String.valueOf(locationNames.get(position));
                filterlocation(selectedLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void filterlocation(String location) {

        modelSlotsList = dbHandler.getSlotsByLocation(location);

        if(modelSlotsList.size()>0){

            populateDataInRecycleView(modelSlotsList);

        }
        else{
            progressBar.setVisibility(View.GONE);

            Toast.makeText(getContext(), "Size is Null", Toast.LENGTH_SHORT).show();

            //todo:Give Error Message
        }



    }

    private void populateDataInRecycleView(List<ModelSlots> modelSlotsList) {
        int numberOfColumns = GridUtility.calculateNoOfColumns(getContext(),120);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),numberOfColumns));
        parkingSlotsAdapter = new ParkingSlotsAdapter(getContext(),modelSlotsList,getActivity());
        recyclerView.setAdapter(parkingSlotsAdapter);
        progressBar.setVisibility(View.GONE);

    }

}