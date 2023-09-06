//package com.logispark.parkingmanagementlogispark.fragment;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.hardware.Camera;
//import android.os.Build;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.RequiresApi;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//
//import android.os.Handler;
//import android.util.Log;
//import android.util.SparseArray;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.bumptech.glide.Glide;
//
//import com.google.android.gms.vision.CameraSource;
//import com.google.android.gms.vision.barcode.BarcodeDetector;
//import com.google.gson.Gson;
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
////import com.journeyapps.barcodescanner.BarcodeEncoder;
//import com.imin.scan.Result;
//import com.imin.scan.ScanUtils;
//import com.imin.scan.Symbol;
//import com.logispark.parkingmanagementlogispark.IminPrinter.IminPrinterHelper;
//import com.logispark.parkingmanagementlogispark.R;
////import com.logispark.parkingmanagementlogispark.Sumni.SunmiPrintHelper;
//import com.logispark.parkingmanagementlogispark.main.MainActivity;
//import com.logispark.parkingmanagementlogispark.main.QrActivity;
//import com.logispark.parkingmanagementlogispark.models.ModelActivateTable;
//import com.logispark.parkingmanagementlogispark.models.ModelDeviceSpecificInformation;
//import com.logispark.parkingmanagementlogispark.models.ModelEstimate;
//import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
//import com.logispark.parkingmanagementlogispark.models.ModelSucessSaveData;
//import com.logispark.parkingmanagementlogispark.models.ModelVehicle;
//import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;
//import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
//import com.logispark.parkingmanagementlogispark.utilites.JsonConvertor;
//import com.logispark.parkingmanagementlogispark.utilites.PrintPic;
//import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
//import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;
//import com.logispark.parkingmanagementlogispark.utilites.Sync.NetworkStateChecker;
//
//import org.json.JSONArray;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class QrFragment extends Fragment{
//    TextView tresult, tvscanButton;
//    ProgressDialog progressDialog;
//    private IminPrinterHelper iminPrinterHelper;
//    //    private CodeScanner mCodeScanner;
//    BarcodeDetector barcodeDetector;
//    private ModelDeviceSpecificInformation modelDeviceSpecificInformation;
//    private ProgressDialog dialog;
//    private ModelParkingData modelParkingData;
//    String result, apidata, datePrint, head, timeandRatePrint, durationPrint, totalcostPrint, checkInTimePrint;
//    DbHandler dbHandler;
//    private View view;
//    private int active = 0;
//    private Call<ModelSucessSaveData> callSaveSuccessData;
//    Call<ModelActivateTable> callDectivate;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        final Activity activity = getActivity();
//        getActivity().setTitle("QR Scanner");
//
//        view = inflater.inflate(R.layout.fragment_qr, container, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
//
//        dbHandler = new DbHandler(getContext());
//
//        iminPrinterHelper = new IminPrinterHelper(getContext(), getActivity());
//
////        init();
//        init(view);
//
//
//        modelDeviceSpecificInformation = SharedPreferenceManager.getmInstance(getContext()).getDeviceInformation();
//
//
//        return view;
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    private void init(View view) {
//
//        Intent intent = new Intent(getContext(), QrActivity.class);
//        getContext().startActivity(intent);
//        tresult = (TextView) this.view.findViewById(R.id.textviewresult);
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public void sendData(String result) {
//
//        modelParkingData = dbHandler.searchParkingDataFromToken(result);
//        if (modelParkingData.id == -1) {
//            Toast.makeText(getContext(), "Parking Data Not Found", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } else {
//            int days = -1;
//            try {
//                SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                String inTime = modelParkingData.getInTime();
//                String endTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
//                Date l1 = dtf.parse(inTime);
//                Date l2 = dtf.parse(endTime);
//                long diff = l1.getTime() - l2.getTime();
//                days = (int) Math.abs(diff / (1000 * 60 * 60 * 24));
//
//            } catch (Exception e) {
//                Toast.makeText(getContext(), "Days Between " + String.valueOf(e), Toast.LENGTH_SHORT).show();
//            }
//
//
//            if (days == -1) {
//                Toast.makeText(getContext(), "Error in calculation of time", Toast.LENGTH_SHORT).show();
//            } else if (days == 0) {
//
//                days = 1;
//
//            }
//            int cost = modelParkingData.getRate();
//            float total = (cost * days) - modelParkingData.getDiscount();
//            if (printReceipt(modelParkingData, days, cost, total)) {
//
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//            ;
//        }
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private boolean printReceipt(ModelParkingData modelParkingData, int timePassed, int cost, float total) {
//
//        ModelVehicleRate modelVehicle = dbHandler.searchByVechileId(modelParkingData.getVechileId());
//        String vecihleType = "Type : " + modelVehicle.getVehicleType();
//        String rate = "Rate : " + String.valueOf(modelParkingData.getRate()) + " /day";
//        String accommodation = "Accommodation : " + String.valueOf(modelParkingData.getAccommodation()) ;
//        String washing = "Washing : " + String.valueOf(modelParkingData.getWashing()) ;
//        String todaysDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
//        String vehicleNumber = "Vehicle Number : " + String.valueOf(modelParkingData.getVehicleNumber());
//        String duration = "Duration : " + Math.ceil(timePassed) + " Days";
//        String totalCost = "Total Cost : " + String.valueOf(total) + " Rs";
//        String checkInTime = "Check In : " + modelParkingData.getInTime();
//        String discount = "Discount : " + modelParkingData.getDiscount();
//        String printerIp = "192.168.1.101";
//
//        int slotId = modelParkingData.getSlot();
//
//        ModelEstimate modelEstimate = new ModelEstimate(todaysDate, String.valueOf(rate), totalCost, duration, checkInTime, discount, vecihleType, vehicleNumber,washing,accommodation);
//        modelParkingData.setActive(0);
//        modelParkingData.setOutTime(todaysDate);
//        modelParkingData.setDuration(timePassed);
//        modelParkingData.setAmount(total);
//
//        dialog = ProgressDialog.show(getContext(), "Connecting", "please wait...");
//        long successData = dbHandler.updateParkingData(modelParkingData);
//
//        if (successData == 1) {
//
////            boolean printResult = SunmiPrintHelper.getInstance().printEstimate(getContext(),modelEstimate);
//
//            boolean printResult = iminPrinterHelper.printEstimate(modelEstimate);
//
//            if (printResult) {
//
//                if (modelDeviceSpecificInformation.isSysncStatus() && NetworkStateChecker.isOnline(getContext())) {
//
//
//                    JsonConvertor jsonConvertor = new JsonConvertor();
//                    //Generation of JSON
//                    JsonArray payload = jsonConvertor.generateJson(modelParkingData);
//
//                    callSaveSuccessData = RetrofitClient.getmInstance().getretrofit(getContext()).saveDataInServer(modelDeviceSpecificInformation.getBranchName(), payload);
//                    callSaveSuccessData.enqueue(new Callback<ModelSucessSaveData>() {
//                        @Override
//                        public void onResponse(Call<ModelSucessSaveData> call, Response<ModelSucessSaveData> response) {
//
//                            if (response.body() != null) {
//
//
//                                ModelSucessSaveData modelSucessSaveData = response.body();
//                                if (modelSucessSaveData.getSuccess().equals("true")) {
//                                    if (slotId != -1) {
//                                        deactivateSlot(slotId);
//
//                                    } else {
//                                        dbHandler.updateSyncOnParkingData(modelParkingData.id);
//                                        showtoast(getString(R.string.checkOut), R.drawable.checked);
//
//                                    }
//                                } else {
////                                    showtoast(getString(R.string.syncFailed), R.drawable.error);
//                                }
//                            } else {
////                                showtoast(getString(R.string.unexpected_error), R.drawable.error);
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ModelSucessSaveData> call, Throwable t) {
//
//                            showtoast(getString(R.string.no_internet) + "Sending Data", R.drawable.nointernet);
//
//
//                        }
//                    });
//
//                } else {
//                    Log.d("Sysnc", "Network Error");
//                }
//            } else {
//                Toast.makeText(getContext(), "Something Went Wrong On Print Result", Toast.LENGTH_SHORT).show();
//            }
//
//
//        }
//
//
//        return true;
//
//
//    }
//
//
//    private void deactivateSlot(int slotId) {
//        callDectivate = RetrofitClient.getmInstance().getretrofit(getContext()).activateSlot(slotId);
//        callDectivate.enqueue(new Callback<ModelActivateTable>() {
//            @Override
//            public void onResponse(Call<ModelActivateTable> call, Response<ModelActivateTable> response) {
//                if (response.body() != null) {
//
//                    ModelActivateTable modelActivateTable = response.body();
//                    if (modelActivateTable.getSuccess() == 1) {
//
//                        dbHandler.updateSyncOnParkingData(modelParkingData.getId());
//                        Log.d("Deac", "Activated");
////                            progressBar.setVisibility(View.GONE);
//                        dialog.dismiss();
//                    }
//                } else {
//                    Toast.makeText(getContext(), "Error Activating Slot", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelActivateTable> call, Throwable t) {
//                dialog.dismiss();
//                Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//    }
//
////    private void init() {
////        SunmiPrintHelper.getInstance().initSunmiPrinterService(getContext());
////        SunmiPrintHelper.getInstance().controlLcd(1);
////        SunmiPrintHelper.getInstance().controlLcd(2);
////        SunmiPrintHelper.getInstance().controlLcd(4);
////
////    }
//
//
//    /**
//     * Custom Toast Generator
//     *
//     * @param text
//     * @param image
//     */
//    private void showtoast(String text, int image) {
//
//        LayoutInflater layoutInflater = getLayoutInflater();
//        View layout = layoutInflater.inflate(R.layout.toast_layout, (ViewGroup) view.findViewById(R.id.toast_root));
//
//        TextView toastText = layout.findViewById(R.id.customtoast);
//        ImageView toastImage = layout.findViewById(R.id.toast_image);
//
//        toastText.setText(text);
//        Glide.with(getContext()).load(image).into(toastImage);
//
//        Toast toast = new Toast(getContext());
//        toast.setGravity(Gravity.BOTTOM, 0, 10);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(layout);
//        toast.show();
//    }
//
//}