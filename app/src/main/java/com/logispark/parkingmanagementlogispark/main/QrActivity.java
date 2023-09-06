package com.logispark.parkingmanagementlogispark.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
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

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonArray;
import com.imin.scan.CameraScan;
import com.imin.scan.CaptureActivity;
import com.imin.scan.DecodeConfig;
import com.imin.scan.DecodeFormatManager;
import com.imin.scan.Result;
import com.imin.scan.analyze.MultiFormatAnalyzer;
import com.imin.scan.config.ResolutionCameraConfig;
import com.logispark.parkingmanagementlogispark.Adapters.ViewPagerAdapter;
import com.logispark.parkingmanagementlogispark.IminPrinter.IminPrinterHelper;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.fragment.VehicleLocationFragment;
import com.logispark.parkingmanagementlogispark.fragment.VehicleRateFragment;
import com.logispark.parkingmanagementlogispark.models.ModelActivateTable;
import com.logispark.parkingmanagementlogispark.models.ModelDeviceSpecificInformation;
import com.logispark.parkingmanagementlogispark.models.ModelDriver;
import com.logispark.parkingmanagementlogispark.models.ModelEstimate;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelPrintTable;
import com.logispark.parkingmanagementlogispark.models.ModelSucessSaveData;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.JsonConvertor;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;
import com.logispark.parkingmanagementlogispark.utilites.Sync.NetworkStateChecker;
import com.logispark.parkingmanagementlogispark.utilites.TimePassedCalculator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrActivity extends CaptureActivity {
    StringBuilder sb = new StringBuilder();
    private Toast toast;
    private TextView textresult;
    public int decode_count = 0;
    TextView tresult, tvscanButton;
    ProgressDialog progressDialog;
    private IminPrinterHelper iminPrinterHelper;
    //    private CodeScanner mCodeScanner;
    BarcodeDetector barcodeDetector;
    private ModelDeviceSpecificInformation modelDeviceSpecificInformation;
    private ProgressDialog dialog;
    private ModelParkingData modelParkingData;
    String result, apidata, datePrint, head, timeandRatePrint, durationPrint, totalcostPrint, checkInTimePrint;
    DbHandler dbHandler;
    private View view;
    private int active = 0;
    private Call<ModelSucessSaveData> callSaveSuccessData;
    Call<ModelActivateTable> callDectivate;

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom_capture;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        dbHandler = new DbHandler(getApplicationContext());


//        init();

        modelDeviceSpecificInformation = SharedPreferenceManager.getmInstance(getApplicationContext()).getDeviceInformation();

    }


    @Override
    public void initCameraScan() {
        super.initCameraScan();
        textresult = findViewById(R.id.textresult);
        DecodeConfig decodeConfig = new DecodeConfig();
        decodeConfig.setHints(DecodeFormatManager.ALL_HINTS)
                .setSupportVerticalCode(true)
                .setSupportLuminanceInvert(true)
                .setAreaRectRatio(0.8f)
                .setFullAreaScan(false);

        getCameraScan().setPlayBeep(true)
                .setPlayRaw(R.raw.beep)
                .setVibrate(true)//设置是否震动，默认为false
//                    .setCameraConfig(new CameraConfig())//设置相机配置信息，CameraConfig可覆写options方法自定义配置
                .setCameraConfig(new ResolutionCameraConfig(QrActivity.this))//设置CameraConfig，可以根据自己的需求去自定义配置
                .setNeedAutoZoom(false)//二维码太小时可自动缩放，默认为false
                .setNeedTouchZoom(false)//支持多指触摸捏合缩放，默认为true
                .setDarkLightLux(45f)//设置光线足够暗的阈值（单位：lux），需要通过{@link #bindFlashlightView(View)}绑定手电筒才有效
                .setBrightLightLux(100f)//设置光线足够明亮的阈值（单位：lux），需要通过{@link #bindFlashlightView(View)}绑定手电筒才有效
                .bindFlashlightView(null)//绑定手电筒，绑定后可根据光线传感器，动态显示或隐藏手电筒按钮
                .setOnScanResultCallback(this)//设置扫码结果回调，需要自己处理或者需要连扫时，可设置回调，自己去处理相关逻辑
                .setAnalyzer(new MultiFormatAnalyzer(decodeConfig))//设置分析器,DecodeConfig可以配置一些解码时的配置信息，如果内置的不满足您的需求，你也可以自定义实现，
                .setAnalyzeImage(true);//设置是否分析图片，默认为true。如果设置为false，相当于关闭了扫码识别功能

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onScanResultCallback(Result result) {

        getCameraScan().stopCamera();
        sendData(result.toString());
        return true;
    }

    @Override
    public void onScanResultFailure() {
        textresult.setText(sb.toString());
        sb.delete(0, sb.length());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendData(String result) {

        iminPrinterHelper = new IminPrinterHelper(getApplicationContext());
        boolean printerSuccess = iminPrinterHelper.initPrinter();

        modelParkingData = dbHandler.searchParkingDataFromToken(result);
        if (modelParkingData.id == -1) {
            Toast.makeText(getApplicationContext(), "Parking Data Not Found", Toast.LENGTH_SHORT).show();
//            Toast.("Parking Data Not Found", R.drawable.error);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            int days = -1;
            TimePassedCalculator timePassedCalculator = new TimePassedCalculator(getApplicationContext());
            days = timePassedCalculator.getDaysPassed(modelParkingData);
            if (days == -1) {
                Toast.makeText(getApplicationContext(), "Error in calculation of time", Toast.LENGTH_SHORT).show();
            } else if (days == 0) {

                days = 1;

            }

            int cost = modelParkingData.getRate();
            int accommodation = modelParkingData.getAccommodation();
            int washing = modelParkingData.getWashing();
            String ticketCode = modelParkingData.getTicketCode();
            float subtotal = (cost * days);
            float discountAmount = subtotal * ((float) modelParkingData.getDiscount() / 100);
            subtotal = subtotal - discountAmount;
            float total = subtotal + washing + accommodation;
            if (printReceipt(modelParkingData, days, cost, total, subtotal, discountAmount, ticketCode)) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK & Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean printReceipt(ModelParkingData modelParkingData, int timePassed, int cost, float total, float subtotal, float discountAmount, String ticketCode) {

        ModelVehicleRate modelVehicle = dbHandler.searchByVechileId(modelParkingData.getVechileId());


        String vecihleType = "Type : " + modelVehicle.getVehicleType();
        String rate = "Rate : " + String.valueOf(modelParkingData.getRate()) + " /day";
        String accommodation = "Accommodation : Nrs. " + String.valueOf(modelParkingData.getAccommodation());
        String washing = "Washing : Nrs. " + String.valueOf(modelParkingData.getWashing());
        String todaysDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        String vehicleNumber = "Vehicle Number : " + String.valueOf(modelParkingData.getVehicleNumber());
        String duration = "Duration : " + Math.ceil(timePassed) + " Days";
        String totalCost = "Total Cost : Nrs. " + String.valueOf(total);
        String subTotal = "Sub Total : Nrs. " + String.valueOf(subtotal);
        String checkInTime = "Check In : " + modelParkingData.getInTime();
        String discount = "Discount : Nrs. " + String.valueOf(discountAmount);
        String ticketNo = "Ticket No. :" + String.valueOf(ticketCode);
        String printerIp = "192.168.1.101";

        int slotId = modelParkingData.getSlot();

        ModelEstimate modelEstimate = new ModelEstimate(todaysDate, String.valueOf(rate), totalCost, duration, checkInTime, discount, vecihleType, vehicleNumber, washing, accommodation, subTotal, ticketNo);
        modelParkingData.setActive(0);
        modelParkingData.setOutTime(todaysDate);
        modelParkingData.setDuration(timePassed);
        modelParkingData.setAmount(total);

        //getting Driver Data
        long driverId = modelParkingData.getDriverId();
        ModelDriver modelDriver = dbHandler.searchDriver(driverId);
        dialog = ProgressDialog.show(QrActivity.this, "Connecting", "please wait...");

//            boolean printResult = SunmiPrintHelper.getInstance().printEstimate(getContext(),modelEstimate);
        boolean printResult = iminPrinterHelper.printEstimate(modelEstimate,0);

        if (printResult) {

            ModelPrintTable modelPrintTable = new ModelPrintTable((int) modelParkingData.getId(),1,0);
            dbHandler.addPrintTable(modelPrintTable);

            long successData = dbHandler.updateParkingData(modelParkingData);

            if (successData == 1) {

                if (modelDeviceSpecificInformation.isSysncStatus() && NetworkStateChecker.isOnline(getApplicationContext())) {
                    JsonConvertor jsonConvertor = new JsonConvertor(getApplicationContext());
                    //Generation of JSON
                    JsonArray payload = jsonConvertor.generateJson(modelParkingData, modelDriver);
                    callSaveSuccessData = RetrofitClient.getmInstance().getretrofit(getApplicationContext()).saveDataInServer(modelDeviceSpecificInformation.getBranchName(), payload);
                    callSaveSuccessData.enqueue(new Callback<ModelSucessSaveData>() {
                        @Override
                        public void onResponse(Call<ModelSucessSaveData> call, Response<ModelSucessSaveData> response) {

                            if (response.body() != null) {


                                ModelSucessSaveData modelSucessSaveData = response.body();
                                if (modelSucessSaveData.getSuccess().equals("true")) {
                                    if (slotId != -1) {
                                        deactivateSlot(slotId);

                                    } else {
                                        dbHandler.updateSyncOnParkingData(modelParkingData.id);
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Sync Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Unexpected Error", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ModelSucessSaveData> call, Throwable t) {

//                            showtoast(getString(R.string.no_internet) + "Sending Data", R.drawable.nointernet);


                        }
                    });

                } else {
                    Log.d("Sysnc", "Network Error");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Something Went Wrong On Print Result", Toast.LENGTH_SHORT).show();
            }


        }


        return true;


    }


    private void deactivateSlot(int slotId) {
        callDectivate = RetrofitClient.getmInstance().getretrofit(getApplicationContext()).activateSlot(slotId);
        callDectivate.enqueue(new Callback<ModelActivateTable>() {
            @Override
            public void onResponse(Call<ModelActivateTable> call, Response<ModelActivateTable> response) {
                if (response.body() != null) {

                    ModelActivateTable modelActivateTable = response.body();
                    if (modelActivateTable.getSuccess() == 1) {

                        dbHandler.updateSyncOnParkingData(modelParkingData.getId());
                        Log.d("Deac", "Activated");
//                            progressBar.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error Activating Slot", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ModelActivateTable> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_SHORT).show();


            }
        });
    }

//    private void init() {
//        SunmiPrintHelper.getInstance().initSunmiPrinterService(getContext());
//        SunmiPrintHelper.getInstance().controlLcd(1);
//        SunmiPrintHelper.getInstance().controlLcd(2);
//        SunmiPrintHelper.getInstance().controlLcd(4);
//
//    }


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
        inflater.inflate(R.menu.menusearch, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (id == android.R.id.home) {

            Intent intent = new Intent(QrActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.navigation_search) {

            Intent intent = new Intent(QrActivity.this, SearchToken.class);
            startActivity(intent);

        }


        return super.onOptionsItemSelected(item);
    }


}
