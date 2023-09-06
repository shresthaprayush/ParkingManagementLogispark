package com.logispark.parkingmanagementlogispark.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.logispark.parkingmanagementlogispark.Adapters.ServiceAdapter;
import com.logispark.parkingmanagementlogispark.Adapters.VehicleRateAdapter;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.models.ModelService;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleSuccess;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;

public class ServiceRateFragment extends Fragment {

    private ProgressDialog progressDialog;
    private RecyclerView recyclerViewRates;
    private ServiceAdapter serviceAdapter;
    private DbHandler dbHandler;
    private String branchName;
    private View view;
    private List<ModelService> modelServiceList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final Activity activity = getActivity();
        activity.setTitle("Service List");

        view = inflater.inflate(R.layout.fragment_service_rate, container, false);
        branchName = SharedPreferenceManager.getmInstance(getContext()).getDeviceInformation().getBranchName();
        recyclerViewRates = view.findViewById(R.id.recycleViewServiceRateFragment);

        progressDialog = new ProgressDialog(getContext());
        dbHandler = new DbHandler(getContext());
        modelServiceList = dbHandler.getAllServiceRate();

        if(modelServiceList.size() == 0){

            showtoast(getString(R.string.NoData),R.drawable.error);
        }
        else {
            serviceAdapter = new ServiceAdapter(getContext(),modelServiceList);
            recyclerViewRates.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerViewRates.setAdapter(serviceAdapter);
        }

        return view;


    }

    /**
     * Custom Toast Generator
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