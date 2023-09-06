package com.logispark.parkingmanagementlogispark.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;


import com.bumptech.glide.Glide;
import com.google.android.gms.vision.text.Line;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.imin.printerlib.IminPrintUtils;
//import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.logispark.parkingmanagementlogispark.Adapters.CheckInRatesAdapter;
import com.logispark.parkingmanagementlogispark.IminPrinter.IminPrinterHelper;
import com.logispark.parkingmanagementlogispark.R;
//import com.logispark.parkingmanagementlogispark.Sumni.SunmiPrintHelper;
import com.logispark.parkingmanagementlogispark.main.QrCode;
import com.logispark.parkingmanagementlogispark.main.RatesSlotsLocationList;
import com.logispark.parkingmanagementlogispark.main.VehicleRate;
import com.logispark.parkingmanagementlogispark.models.ModelActivateTable;
import com.logispark.parkingmanagementlogispark.models.ModelDeviceSpecificInformation;
import com.logispark.parkingmanagementlogispark.models.ModelDriver;
import com.logispark.parkingmanagementlogispark.models.ModelLocations;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelParkingSlip;
import com.logispark.parkingmanagementlogispark.models.ModelPrintTable;
import com.logispark.parkingmanagementlogispark.models.ModelService;
import com.logispark.parkingmanagementlogispark.models.ModelSlots;
import com.logispark.parkingmanagementlogispark.models.ModelSucessSaveData;
import com.logispark.parkingmanagementlogispark.models.ModelVehicle;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.PrintPic;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VechileCheckIn extends Fragment {

    View view;
    DbHandler dbHandler;
    private Button btnCheckIn;
    private long presult;
    private static Pattern phonePattern;
    private String selectedLocation, selectedSlot,rateName;
    private ModelPrintTable modelPrintTable;
    private int acomodationRate, washingRate, acomodationRateDb, washingRateDb;
    OutputStream mmOutputStream;
    private boolean chkAccommodation, chkWashing;
    private IminPrinterHelper iminPrinterHelper;
    private TextView txtAccomadation, txtWashing, txtDialogRate;
    private CheckBox checkBoxAccomadation, checkBoxWashing;
    private RecyclerView recyclerViewRates;
    private TextInputEditText edtCustomerName, edtNationality, edtCustomerPan, edtDriverLicenseNumber, edtVehicleNumber, edtPlaceOfOrigin, edtRate, edtContactNumber;
    private TextInputLayout textInputLayoutVechileNumber;
    private Spinner spinnerAreaCheckIn, spinnerSlotCheckIn;
    private ArrayList<String> areaList = new ArrayList<>();
    private int rate = 0, tokenNumber;
    private String customerName, customerPan, customerContactNumber, driverLicenseNumber, vehicleNumber, placeOfOrigin, output, output2, nationalityString;
    private ImageButton imageButtonTruck, imageButtonJeep, imageButtonScooter;
    private String vechileNumber, rateString, date, time, vehicleType;
    private List<ModelVehicleRate> vehicleRates;
    private int print_size = 8;
    private int active = 1, discount = 0;
    private int error_level = 3;
    private Dialog dialogSync;
    private String slotName = "NA";
    private String dateTime;
    private Call<ModelActivateTable> callModelActivateTable;
    private Call<ModelSucessSaveData> callSaveSuccessData;
    private int slotId = -1;
    private ProgressBar progressBar;
    private ModelDeviceSpecificInformation deviceSpecificInformation;
    private String customerCode;
    private LinearLayout layout;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        init();


        final Activity activity = getActivity();
        getActivity().setTitle("Vehicle Check In");


        phonePattern = Pattern.compile("0-9{10}");
        try {
            slotId = getArguments().getInt("slotID", -1);
            slotName = getArguments().getString("slotName");
            getActivity().getActionBar().setTitle("SLOT " + slotName);

        } catch (Exception e) {
            Log.e("SlotID", String.valueOf(e));
        }

        view = inflater.inflate(R.layout.fragment_vechile_check_in, container, false);
        dbHandler = new DbHandler(getContext());


        layout = view.findViewById(R.id.linearLayout);
        deviceSpecificInformation = SharedPreferenceManager.getmInstance(getContext()).getDeviceInformation();

        //Setting recycle view for checkin
        recyclerViewRates = view.findViewById(R.id.recycleViewRatesCheckin);
        vehicleRates = dbHandler.getAllVehicleRate();

        CheckInRatesAdapter checkInRatesAdapter = new CheckInRatesAdapter(getContext(), vehicleRates, VechileCheckIn.this);
        recyclerViewRates.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerViewRates.setAdapter(checkInRatesAdapter);

        progressBar = view.findViewById(R.id.progressBarActivate);
        progressBar.setVisibility(View.GONE);

        checkBoxWashing = view.findViewById(R.id.checkBoxWashing);
        checkBoxAccomadation = view.findViewById(R.id.checkBoxAccomodation);
        edtCustomerName = view.findViewById(R.id.editTextCustomerName);
        edtCustomerPan = view.findViewById(R.id.editTextPanNumber);
        edtDriverLicenseNumber = view.findViewById(R.id.editTextDrivingLicenseNumber);
        edtVehicleNumber = view.findViewById(R.id.editTextVechileNumber);
        edtPlaceOfOrigin = view.findViewById(R.id.editTextplaceOfOrigin);
        edtRate = view.findViewById(R.id.editTextRate);
        edtContactNumber = view.findViewById(R.id.editTextContactNumber);
        edtNationality = view.findViewById(R.id.editTextNationality);

        btnCheckIn = view.findViewById(R.id.buttonCheckIn);

        txtAccomadation = view.findViewById(R.id.textViewAccomodation);
        txtWashing = view.findViewById(R.id.textViewWashing);


        //Checking Synced or not
        dialogSync = new Dialog(getContext());

        String date2 = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());


        if (String.valueOf(deviceSpecificInformation.getLastUpdated()).equals(String.valueOf("null"))) {
            showdialog(dialogSync);

        } else if (!deviceSpecificInformation.getLastUpdated().substring(0, 10).equalsIgnoreCase(date2)) {

            showdialog(dialogSync);

        }


        List<ModelService> modelServices = dbHandler.getAllServiceRate();

        for (ModelService modelService : modelServices) {
            String serviceName = modelService.getService();

//            LinearLayout linearLayoutChkHolder = new LinearLayout(getContext());
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT,1f);
//            linearLayoutChkHolder.setLayoutParams(params);
//            linearLayoutChkHolder.setOrientation(LinearLayout.HORIZONTAL);
//
//
//            CheckBox checkBox = new CheckBox(getContext());
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
//            layoutParams.topMargin = 2;
//            layoutParams.bottomMargin = 2;
//            layoutParams.weight = 1;
//            checkBox.setText(serviceName);
//            checkBox.setTextSize(15);
//            checkBox.setPadding(5, 5, 5, 5);
//            checkBox.setLayoutParams(layoutParams);
//            linearLayoutChkHolder.addView(checkBox);
//            layout.addView(linearLayoutChkHolder);


            if (serviceName.trim().equalsIgnoreCase("accommodation")) {
                acomodationRateDb = Integer.parseInt(modelService.getRate());

            } else if (serviceName.trim().equalsIgnoreCase("washing")) {
                washingRateDb = Integer.parseInt(modelService.getRate());
            }

        }

        checkBoxAccomadation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chkAccommodation = b;

            }
        });

        checkBoxWashing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                chkWashing = b;

            }
        });
        txtWashing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_rate_list);
                txtDialogRate = dialog.findViewById(R.id.dialogRate);
                txtDialogRate.setText("Rate " + String.valueOf(acomodationRate));
                dialog.show();


            }
        });

        txtAccomadation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_rate_list);
                txtDialogRate = dialog.findViewById(R.id.dialogRate);
                txtDialogRate.setText("Rate " + String.valueOf(washingRate));
                dialog.show();


            }
        });

//        textInputLayoutVechileNumber = view.findViewById(R.id.inputVechileNumber);
//        textInputLayoutRate = view.findViewById(R.id.inputTextRate);
        spinnerAreaCheckIn = view.findViewById(R.id.spinnerAreaCheckIn);
        spinnerSlotCheckIn = view.findViewById(R.id.spinnerSlotCheckin);


//        populateLocationSpinner();

        disableEditText(edtRate);

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {


                confirminput();

//                progressBar.setVisibility(View.GONE);


            }
        });


//         Inflate the layout for this fragment
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
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        dialogSync.setCanceledOnTouchOutside(false);
        dialogSync.setCancelable(false);
        dialogSync.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void confirminput() {

        if (!validateNationality() | !validateVechileNumber() | !validateRate() | !validateCustomerName() | !validateDrivingLicenseNumber() | !validatePlaceOfOrigin() | !validateContactNumber()) {
            return;
        } else {
            saveInDatabase(vechileNumber, rate);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveInDatabase(String vechileNumber, int rate) {

        iminPrinterHelper = new IminPrinterHelper(getContext());
        iminPrinterHelper.initPrinter();

        tokenNumber = SharedPreferenceManager.getmInstance(getContext()).get_token_number();
        tokenNumber = tokenNumber + 1;

        acomodationRate = acomodationRateDb;
        washingRate = washingRateDb;
//        iminPrinterHelper.initPrinter();
        if (!chkAccommodation) {
            acomodationRate = 0;
        }

        if (!chkWashing) {
            washingRate = 0;

        }


        date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
        String uniqueId = generateTicketCode(deviceSpecificInformation);

        ModelVehicleRate modelVehicleRate = dbHandler.searchFromRateName(rateName);
        int vechileRateId = modelVehicleRate.getId();
        vehicleType = modelVehicleRate.getVehicleType();


        //Adding Driver Information To DataBase
        customerPan = edtCustomerPan.getText().toString();

        long driverId;
        ModelDriver modelDriver = new ModelDriver(-1, customerName, customerPan, customerContactNumber, driverLicenseNumber, placeOfOrigin, nationalityString, null);
        driverId = dbHandler.addDriver(modelDriver);
//        customerCode = generateCustomerCode(deviceSpecificInformation, driverId);
//        dbHandler.updateDriver(customerCode, driverId);

        ModelParkingData modelParkingData = new ModelParkingData(-1, rate, slotId, active, discount, 0, 0, tokenNumber, acomodationRate, washingRate, uniqueId, dateTime, "", vechileNumber, dateTime, selectedSlot, 0.0, vechileRateId, driverId);
         presult = dbHandler.addParkingData(modelParkingData);
        SharedPreferenceManager.getmInstance(getContext()).save_token_number(tokenNumber);



        if (deviceSpecificInformation.isSysncStatus()) {
            if (slotId != -1) {
                progressBar.setVisibility(View.VISIBLE);

                activateTable(slotId);

            }
        }

        if (presult == -1) {
        } else {

            printParkingSlip(vechileNumber, rate, uniqueId, date, time, selectedSlot, vehicleType);

        }


    }

    private String generateTicketCode(ModelDeviceSpecificInformation deviceSpecificInformation) {
        return deviceSpecificInformation.getBranchCode() + "-" + "SR" + "-" + tokenNumber;
//        return "BHA" + "-" + "SR" + "-" + tokenNumber;

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
        rateString = edtRate.getText().toString().trim();

        if (rateString.isEmpty()) {
            edtRate.setError("Required Vehicle Rate");
            return false;

        } else {
            edtRate.setError(null);
            rate = Integer.parseInt(rateString);
            return true;
        }

    }


    private boolean validateNationality() {
        nationalityString = edtNationality.getText().toString().trim();

        if (nationalityString.isEmpty()) {
            edtNationality.setError("Required Vehicle Rate");
            return false;

        } else {
            edtNationality.setError(null);
            return true;
        }

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

    private boolean validateContactNumber() {
        customerContactNumber = edtContactNumber.getText().toString().trim();
        if (customerContactNumber.isEmpty()) {
            edtContactNumber.setError("Required Contact Number");
            return false;

        } else {

            if (phonePattern.matcher(customerContactNumber).matches()) {
                edtContactNumber.setError("Please Enter Valid Number");
                return false;
            } else {
                edtContactNumber.setError(null);
                return true;
            }


        }

    }

    private boolean validateCustomerName() {
        customerName = edtCustomerName.getText().toString().trim();
        if (customerName.isEmpty()) {
            edtCustomerName.setError("Required Customer Name");
            return false;

        } else {
            edtCustomerName.setError(null);
            return true;
        }

    }

    private boolean validateDrivingLicenseNumber() {
        driverLicenseNumber = edtDriverLicenseNumber.getText().toString().trim();
        if (driverLicenseNumber.isEmpty()) {
            edtDriverLicenseNumber.setError("Required License Number");
            return false;

        } else {
            edtDriverLicenseNumber.setError(null);
            return true;
        }

    }

    private boolean validatePlaceOfOrigin() {
        placeOfOrigin = edtPlaceOfOrigin.getText().toString().trim();
        if (placeOfOrigin.isEmpty()) {
            edtPlaceOfOrigin.setError("Required Place Of Origin");
            return false;

        } else {
            edtPlaceOfOrigin.setError(null);
            return true;
        }

    }

    private void generateBillQR(String vechileNumber, int rate, String uniqueId) {

        Intent intent = new Intent(getActivity(), QrCode.class);
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        intent.putExtra("vechileNo", vechileNumber);
        intent.putExtra("uniqueId", uniqueId);
//        startActivity(intent);

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

        ModelParkingSlip modelParkingSlip = new ModelParkingSlip(vechileNumber, rate, uniqueId, date, time, selectedSlot, vehicleType, chkAccommodation, chkWashing);

        //todo:Remove This Code
        boolean success = iminPrinterHelper.printReceipt(modelParkingSlip,0);
        modelPrintTable = new ModelPrintTable((int) presult,0,1);
        dbHandler.addPrintTable(modelPrintTable);

        // Changes for Sunmi Printer
//        boolean success = SunmiPrintHelper.getInstance().printParkingSlip(modelParkingSlip);
//        SunmiPrintHelper.getInstance().feedPaper();

        if (success) {
            clean();
        } else {
            showtoast(getString(R.string.unexpected_error), R.drawable.error);
        }


    }


    private void clean() {
        progressBar.setVisibility(View.GONE);
        edtCustomerName.setText("");
        edtCustomerPan.setText("");
        edtDriverLicenseNumber.setText("");
        edtVehicleNumber.setText("");
        edtPlaceOfOrigin.setText("");
        edtContactNumber.setText("");
        edtRate.setText("");
        checkBoxAccomadation.setChecked(false);
        checkBoxWashing.setChecked(false);
        edtCustomerName.requestFocus();
        edtNationality.setText("");
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
    public void getRate(int rate, int d,String name) {

        discount = d;
        rateName = name;
        edtRate.setText(String.valueOf(rate));


    }


    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
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


    private void populateSlotSpinner(String area) {
        //Populate ArrayList Slots

        ArrayList<String> slotList = new ArrayList<>();
        slotList.add("");
        List<ModelSlots> modelSlotsList = dbHandler.getSlotsByLocation(area);
        for (ModelSlots m : modelSlotsList) {
            slotList.add(m.getName());
        }

        ArrayAdapter<String> a = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, slotList);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSlotCheckIn.setAdapter(a);

        spinnerSlotCheckIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedSlot = String.valueOf(slotList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void populateLocationSpinner() {
        List<ModelLocations> modelLocations = dbHandler.getAllArea();
        areaList.add("");
        for (ModelLocations m : modelLocations) {
            areaList.add(m.getName());
        }


        ArrayAdapter<String> a = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, areaList);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAreaCheckIn.setAdapter(a);

        spinnerAreaCheckIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedLocation = String.valueOf(areaList.get(position));
                populateSlotSpinner(selectedLocation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}