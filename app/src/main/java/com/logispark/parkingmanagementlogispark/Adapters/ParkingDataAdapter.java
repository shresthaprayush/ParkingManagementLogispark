package com.logispark.parkingmanagementlogispark.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.logispark.parkingmanagementlogispark.IminPrinter.IminPrinterHelper;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.main.MainActivity;
import com.logispark.parkingmanagementlogispark.main.QrActivity;
import com.logispark.parkingmanagementlogispark.main.SearchToken;
import com.logispark.parkingmanagementlogispark.models.ModelDriver;
import com.logispark.parkingmanagementlogispark.models.ModelEstimate;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelParkingSlip;
import com.logispark.parkingmanagementlogispark.models.ModelPrintTable;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.TimePassedCalculator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParkingDataAdapter extends RecyclerView.Adapter<ParkingDataAdapter.ParkingDataViewHolder> {

    private Context context;
    private List<ModelParkingData> modelParkingDataList;
    private Activity activity;

    public ParkingDataAdapter(Context context, List<ModelParkingData> modelParkingDataList, Activity activity) {
        this.context = context;
        this.modelParkingDataList = modelParkingDataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ParkingDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.card_parking_data, parent, false);
        return new ParkingDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingDataViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String ticket, vehicleNumber, amount, date;
        ModelParkingData modelParkingData = modelParkingDataList.get(position);
        ticket = modelParkingData.getTicketCode();
        vehicleNumber = modelParkingData.getVehicleNumber();
        amount = String.valueOf(modelParkingData.getAmount());
        date = modelParkingData.getInTime();
        Dialog confirmationDialouge;
        confirmationDialouge = new Dialog(activity);
        confirmationDialouge.setContentView(R.layout.dialougeconfirmation);
        Button btnestimate = confirmationDialouge.findViewById(R.id.buttondialougelogoutyes);
        Button btntoken = confirmationDialouge.findViewById(R.id.buttondialougelogoutno);
        TextView confirmationText = confirmationDialouge.findViewById(R.id.textdetequestiondialougetitle);
        confirmationText.setText("Please choose what you want to reprint.");
        btnestimate.setText("Estimate");
        btntoken.setText("Token");

        DbHandler dbHandler = new DbHandler(context);
        //Printing Estimate

        ModelPrintTable modelPrintTable = dbHandler.searchPrintTableFromId(modelParkingData.id);

        holder.textViewDate.setText(date);
        holder.textViewTicket.setText(ticket);
        holder.textViewAmount.setText(amount);
        holder.textViewVehicleNumber.setText(vehicleNumber);

        holder.cardViewSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModelVehicleRate modelVehicle = dbHandler.searchByVechileId(modelParkingData.getVechileId());

                int days = -1;
                TimePassedCalculator timePassedCalculator = new TimePassedCalculator(context);
                days = timePassedCalculator.getDaysPassed(modelParkingData);
                if (days == -1) {
                    Toast.makeText(context, "Error in calculation of time", Toast.LENGTH_SHORT).show();
                } else if (days == 0) {

                    days = 1;

                }


                int cost = modelParkingData.getRate();
                int accommodation = modelParkingData.getAccommodation();
                int washing = modelParkingData.getWashing();
                String ticketNo = modelParkingData.getTicketCode();
                float subtotal = (cost * days);
                float discountAmount = subtotal * ((float) modelParkingData.getDiscount() / 100);
                subtotal = subtotal - discountAmount;
                float total = subtotal + washing + accommodation;

                String vecihleType = "Type : " + modelVehicle.getVehicleType();
                String rate = "Rate : " + String.valueOf(modelParkingData.getRate()) + " /day";
                String accommodationp = "Accommodation : Nrs. " + String.valueOf(modelParkingData.getAccommodation());
                String washingp = "Washing : Nrs. " + String.valueOf(modelParkingData.getWashing());
                String todaysDate = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
                String vehicleNumberP = "Vehicle Number : " + String.valueOf(modelParkingData.getVehicleNumber());
                String duration = "Duration : " + Math.ceil(days) + " Days";
                String totalCost = "Total Cost : Nrs. " + String.valueOf(total);
                String subTotal = "Sub Total : Nrs. " + String.valueOf(subtotal);
                String checkInTime = "Check In : " + modelParkingData.getInTime();
                String discount = "Discount : Nrs. " + String.valueOf(discountAmount);
                String ticketCode = "Ticket No. :" + String.valueOf(ticketNo);
                String printerIp = "192.168.1.101";
                int slotId = modelParkingData.getSlot();
                ModelEstimate modelEstimate = new ModelEstimate(todaysDate, String.valueOf(rate), totalCost, duration, checkInTime, discount, vecihleType, vehicleNumberP, washingp, accommodationp, subTotal, ticketCode);
//               String date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
//               String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
//               String dateTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());

                ModelVehicleRate modelVehicleRate = dbHandler.searchVehicleFromRate(modelParkingData.getRate());
                int vechileRateId = modelVehicleRate.getId();
                String vehicleTypes = modelVehicleRate.getVehicleType();
                boolean acc = false, was = false;
                if (modelParkingData.getAccommodation() != 0) {
                    acc = true;
                }
                if (modelParkingData.getWashing() != 0) {
                    was = true;
                }
                ModelParkingSlip modelParkingSlip = new ModelParkingSlip(modelParkingData.getVehicleNumber(),
                        modelParkingData.getRate(),
                        modelParkingData.getTicketCode(), modelParkingData.getInTime().substring(0, 10),
                        modelParkingData.getInTime().substring(11, 19),
                        null, vehicleTypes,
                        acc, was);

                int finalDays = days;
                btnestimate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        IminPrinterHelper iminPrinterHelper = new IminPrinterHelper(context);
                        iminPrinterHelper.initPrinter();

                        int estimateCount = 0;
                        if (modelPrintTable.getEstimateCount() != -1) {
                            estimateCount = modelPrintTable.getEstimateCount();
                        }

                        modelParkingData.setActive(0);
                        modelParkingData.setOutTime(todaysDate);
                        modelParkingData.setDuration(finalDays);
                        modelParkingData.setAmount(total);

                        //getting Driver Data
                        long driverId = modelParkingDataList.get(position).getDriverId();
                        Log.d("Invoice Count",String.valueOf(estimateCount));
                        boolean printResult = iminPrinterHelper.printEstimate(modelEstimate, estimateCount);

                        if (printResult) {

                            dbHandler.updatePrint(modelParkingData.getId(), 0, estimateCount + 1);

                            long successData = dbHandler.updateParkingData(modelParkingData);

                            if (successData == 1) {
                                Intent intent = new Intent(context, SearchToken.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);

                            }

                            confirmationDialouge.cancel();
                        } else {
                            Toast.makeText(context, "Failed Printing", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                btntoken.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IminPrinterHelper iminPrinterHelper = new IminPrinterHelper(context);
                        iminPrinterHelper.initPrinter();

                        int slipCount = 0;
                        if (modelPrintTable.getSlipCount() != -1) {
                            slipCount = modelPrintTable.getSlipCount();
                        }
                        boolean printResult = iminPrinterHelper.printReceipt(modelParkingSlip, slipCount);


                        if (printResult) {

                            dbHandler.updatePrint(modelParkingData.getId(), 1, slipCount + 1);

                            Intent intent = new Intent(context, SearchToken.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });
                confirmationDialouge.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelParkingDataList.size();
    }

    public class ParkingDataViewHolder extends RecyclerView.ViewHolder {

        private CardView cardViewSales;
        private TextView textViewTicket, textViewVehicleNumber, textViewAmount, textViewDate;

        public ParkingDataViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewSales = itemView.findViewById(R.id.cardViewParkingData);
            textViewDate = itemView.findViewById(R.id.date_textviewcardParkingData);
            textViewAmount = itemView.findViewById(R.id.amount_textviewcardParkingData);
            textViewVehicleNumber = itemView.findViewById(R.id.vechileNumber_textviewcardParkingData);
            textViewTicket = itemView.findViewById(R.id.ticket_textviewcardParkingData);
        }
    }
}
