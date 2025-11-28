package com.logispark.parkingmanagementlogispark.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.logispark.parkingmanagementlogispark.Adapters.CheckInRatesAdapter;
import com.logispark.parkingmanagementlogispark.IminPrinter.IminPrinterHelper;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.Sumni.SunmiPrintHelper;
import com.logispark.parkingmanagementlogispark.main.RatesSlotsLocationList;
import com.logispark.parkingmanagementlogispark.models.ModelActivateTable;
import com.logispark.parkingmanagementlogispark.models.ModelDeviceSpecificInformation;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelParkingSlip;
import com.logispark.parkingmanagementlogispark.models.ModelPrintTable;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VechileCheckIn extends Fragment {

    View view;
    DbHandler dbHandler;
    private Button btnCheckIn;
    private long presult;
    private String rateName;
    private ModelPrintTable modelPrintTable;
    private IminPrinterHelper iminPrinterHelper;
    private RecyclerView recyclerViewRates;
    private TextInputEditText edtVehicleNumber;
    private int rate = 0, tokenNumber, exceedingLimit = 0, halfHourCost = 0;
    private String vechileNumber, date, time, vehicleType;
    private List<ModelVehicleRate> vehicleRates;
    private int active = 1, discount = 0, is30MinActivation = 0;
    private Dialog dialogSync;
    private String slotName = "NA";
    private String dateTime;
    private Call<ModelActivateTable> callModelActivateTable;
    private int slotId = -1;
    private ProgressBar progressBar;
    private ModelDeviceSpecificInformation deviceSpecificInformation;
    private CheckInRatesAdapter checkInRatesAdapter;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Activity activity = getActivity();
        getActivity().setTitle("Vehicle Check In");


        try {
            slotId = getArguments().getInt("slotID", -1);
            slotName = getArguments().getString("slotName");
            getActivity().getActionBar().setTitle("SLOT " + slotName);

        } catch (Exception e) {
            Log.e("SlotID", String.valueOf(e));
        }

        view = inflater.inflate(R.layout.fragment_vechile_check_in, container, false);
        dbHandler = new DbHandler(getContext());


        deviceSpecificInformation = SharedPreferenceManager.getmInstance(getContext()).getDeviceInformation();

        //Setting recycle view for checkin
        recyclerViewRates = view.findViewById(R.id.recycleViewRatesCheckin);
        vehicleRates = dbHandler.getAllVehicleRate();

        checkInRatesAdapter = new CheckInRatesAdapter(getContext(), vehicleRates, VechileCheckIn.this);
        recyclerViewRates.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerViewRates.setAdapter(checkInRatesAdapter);

        progressBar = view.findViewById(R.id.progressBarActivate);
        progressBar.setVisibility(View.GONE);
        edtVehicleNumber = view.findViewById(R.id.editTextVechileNumber);
        btnCheckIn = view.findViewById(R.id.buttonCheckIn);


        //Checking Synced or not
        dialogSync = new Dialog(getContext());
        String date2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        if (String.valueOf(deviceSpecificInformation.getLastUpdated()).equals(String.valueOf("null"))) {
            showdialog(dialogSync);
        } else if (!deviceSpecificInformation.getLastUpdated().substring(0, 10).equalsIgnoreCase(date2)) {
            showdialog(dialogSync);
        }

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                confirminput();
            }
        });
        return view;
    }

    private void showdialog(Dialog dialogSync) {

        dialogSync.setContentView(R.layout.dialougeconfirmation);
        TextView txtViewMessageDialog = dialogSync.findViewById(R.id.textdetequestiondialougetitle);
        Button buttonYes = dialogSync.findViewById(R.id.buttondialougelogoutyes);
        buttonYes.setText("Okay");
        Button buttonNo = dialogSync.findViewById(R.id.buttondialougelogoutno);
        txtViewMessageDialog.setText(R.string.SyncRateText);
        buttonNo.setVisibility(View.GONE);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), RatesSlotsLocationList.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogSync.setCanceledOnTouchOutside(false);
        dialogSync.setCancelable(false);
        dialogSync.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void confirminput() {

        if (!validateVechileNumber() | !validateRate()) {
            return;
        } else {

            saveInDatabase(vechileNumber, rate);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveInDatabase(String vechileNumber, int rate) {

//        iminPrinterHelper = new IminPrinterHelper(getContext());
//        iminPrinterHelper.initPrinter();

        tokenNumber = SharedPreferenceManager.getmInstance(getContext()).get_token_number();
        tokenNumber = tokenNumber + 1;

        date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String uniqueId = generateTicketCode(deviceSpecificInformation);

        ModelVehicleRate modelVehicleRate = dbHandler.searchFromRateName(rateName);
        int vechileRateId = modelVehicleRate.getId();
        vehicleType = modelVehicleRate.getVehicleType();

        ModelParkingData modelParkingData = new ModelParkingData(-1, rate, slotId, active, discount, 0, 0, tokenNumber, 0, 0, uniqueId, dateTime, "", vechileNumber, dateTime, slotName, 0.0, vechileRateId, 0, is30MinActivation, exceedingLimit, halfHourCost);
        presult = dbHandler.addParkingData(modelParkingData);
        SharedPreferenceManager.getmInstance(getContext()).save_token_number(tokenNumber);



        if (deviceSpecificInformation.isSysncStatus()) {
            if (slotId != -1) {
                progressBar.setVisibility(View.VISIBLE);

                activateTable(slotId);

            }
        }

        if (presult != -1) {
            printParkingSlip(vechileNumber, rate, uniqueId, date, time, slotName, vehicleType);
        } else {
            clean();
        }


    }

    private String generateTicketCode(ModelDeviceSpecificInformation deviceSpecificInformation) {
        return deviceSpecificInformation.getBranchCode() + "-" + "SR" + "-" + tokenNumber;
    }


    /**
     * Method to activate table
     *
     * @param slotId
     */
    private void activateTable(int slotId) {

        callModelActivateTable = RetrofitClient.getmInstance().getretrofit(getContext()).activateSlot(slotId);
        callModelActivateTable.enqueue(new Callback<ModelActivateTable>() {
            @Override
            public void onResponse(Call<ModelActivateTable> call, Response<ModelActivateTable> response) {
                if (response.body() != null) {

                    ModelActivateTable modelActivateTable = response.body();
                    if (modelActivateTable.getSuccess() == 1) {
                        progressBar.setVisibility(View.GONE);

                    }
                } else {
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ModelActivateTable> call, Throwable t) {
                progressBar.setVisibility(View.GONE);


            }
        });
    }

    private boolean validateRate() {
        if (rateName == null || rateName.isEmpty()) {
            showtoast("Please select a vehicle type", R.drawable.error);
            return false;
        }
        return true;

    }

    private boolean validateVechileNumber() {
        vechileNumber = edtVehicleNumber.getText().toString().trim();
        if (vechileNumber.isEmpty()) {
            edtVehicleNumber.setError("Required Vehicle Number");
            return false;

        } else {
            edtVehicleNumber.setError(null);
            return true;
        }

    }


    /**
     * @param vechileNumber
     * @param rate
     * @param uniqueId
     * @param date
     * @param time
     * @param selectedSlot
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void printParkingSlip(String vechileNumber, int rate, String uniqueId, String date, String time, String selectedSlot, String vehicleType) {

        ModelParkingSlip modelParkingSlip = new ModelParkingSlip(vechileNumber, rate, uniqueId, date, time, selectedSlot, vehicleType, false, false);

//        boolean success = iminPrinterHelper.printReceipt(modelParkingSlip,0);
//        modelPrintTable = new ModelPrintTable((int) presult,0,1);
//        dbHandler.addPrintTable(modelPrintTable);

        // Changes for Sunmi Printer
        /// todo uncomment for printer
        boolean success = SunmiPrintHelper.getInstance().printParkingSlip(modelParkingSlip);
        SunmiPrintHelper.getInstance().feedPaper();

        if (success) {
            clean();
        } else {
            showtoast(getString(R.string.unexpected_error), R.drawable.error);
        }


    }

    private void clean() {
        progressBar.setVisibility(View.GONE);
        edtVehicleNumber.setText("");
        edtVehicleNumber.requestFocus();
        rate = 0;
        rateName = null;
        if (checkInRatesAdapter != null) {
            checkInRatesAdapter.clearSelection();
        }
        showtoast(getString(R.string.success), R.drawable.checked);
    }

//    private void init() {
//        SunmiPrintHelper.getInstance().initSunmiPrinterService(getContext());
//        SunmiPrintHelper.getInstance().controlLcd(1);
//        SunmiPrintHelper.getInstance().controlLcd(2);
//        SunmiPrintHelper.getInstance().controlLcd(4);
//
//    }

    /**
     * Function called from adapter to set rate
     *
     * @param rate
     */
    public void getRate(int rate, int d,String name, int is30MinActivation, int exceedingLimit, int halfHourCost) {

        discount = d;
        rateName = name;
        this.rate = rate;
        this.is30MinActivation = is30MinActivation;
        this.exceedingLimit = exceedingLimit;
        this.halfHourCost = halfHourCost;

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
