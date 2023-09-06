package com.logispark.parkingmanagementlogispark.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelRecentBills;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;

import java.util.List;

public class RecentBillsAdapter extends RecyclerView.Adapter<RecentBillsAdapter.RecentbillsViewHolder> {
    private Context context;
    private List<ModelParkingData> modelParkingDataList;
    private Dialog dialog;
    private Activity activity;

    public RecentBillsAdapter(Context context, List<ModelParkingData> modelParkingDataList, Activity activity) {
        this.context = context;
        this.modelParkingDataList = modelParkingDataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecentbillsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.cardrecentbills, parent, false);
        return new RecentbillsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentbillsViewHolder holder, int position) {
        holder.textViewsno.setText(String.valueOf(position + 1));
        int id;
        String token;
        int sync;
        String vehicleNumber, checkInTime, checkOut, vehicleType, vehicleRate, duration, discount, subTotal, accommodation, washing,total;


        DbHandler dbHandler = new DbHandler(context);
        vehicleType = dbHandler.getVehicleRate(modelParkingDataList.get(position).getVechileId()).getVehicleType();

        sync = modelParkingDataList.get(position).getSync();

        String vehicleId = String.valueOf(modelParkingDataList.get(position).getVechileId());
        String vehicleTypeName = dbHandler.getVehicleRate(modelParkingDataList.get(position).getVechileId()).getVehicleType();
        id = modelParkingDataList.get(position).getId();
        token = modelParkingDataList.get(position).getTicketCode();
        vehicleNumber = "Vehicle Number : " + modelParkingDataList.get(position).getVehicleNumber();
        checkInTime = "Check In : " + modelParkingDataList.get(position).getInTime();
        checkOut = "Check Out : " + modelParkingDataList.get(position).getOutTime();
        vehicleRate = String.valueOf(modelParkingDataList.get(position).getRate());
        duration = String.valueOf(modelParkingDataList.get(position).getDuration()) + " day(s)";
        discount = String.valueOf(modelParkingDataList.get(position).getDiscount()) + " %";
        subTotal = String.valueOf((modelParkingDataList.get(position).getRate() * modelParkingDataList.get(position).getDuration()) - (modelParkingDataList.get(position).getDiscount()));
        accommodation = String.valueOf(modelParkingDataList.get(position).getAccommodation());
        washing = String.valueOf(modelParkingDataList.get(position).getWashing());
        total = String.valueOf(modelParkingDataList.get(position).getAmount());

        holder.textViewtoken.setText(String.valueOf(token));
        holder.textViewamount.setText(String.valueOf(modelParkingDataList.get(position).getAmount()));

        if (sync == 0) {
            Glide.with(context).load(R.drawable.sync).into(holder.imageViewsyncstatus);

        } else {
            Glide.with(context).load(R.drawable.checked).into(holder.imageViewsyncstatus);
        }
        holder.cardViewrecentbills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(activity);
                dialog.setContentView(R.layout.dialougeitemdetails);
                RelativeLayout btnClose = dialog.findViewById(R.id.closeitemdialouge);
                TextView txtVehicleNumber = dialog.findViewById(R.id.textviewVechileNumber);
                TextView txtCheckInTime = dialog.findViewById(R.id.texviewiteminTimedialouge);
                TextView txtCheckOut = dialog.findViewById(R.id.texviewcheckOutdatedialouge);
                TextView txtVehicleType = dialog.findViewById(R.id.textViewVechileType);
                TextView textViewRate = dialog.findViewById(R.id.textviewrate);
                TextView textViewDuration = dialog.findViewById(R.id.textViewVechileDuration);
                TextView textViewDiscount = dialog.findViewById(R.id.textViewVechileDiscount);
                TextView textViewSubTotal = dialog.findViewById(R.id.textViewVechileSubTotal);
                TextView textViewAccommodation = dialog.findViewById(R.id.textViewVechileAccommodation);
                TextView textViewWashing = dialog.findViewById(R.id.textViewVechileWashing);
                TextView textViewGrandTotal = dialog.findViewById(R.id.textViewVechilGrandTotal);

                textViewRate.setText(vehicleRate);
                txtCheckOut.setText(checkOut);
                txtVehicleType.setText(vehicleTypeName);
                txtCheckInTime.setText(checkInTime);
                txtVehicleNumber.setText(vehicleNumber);
                txtVehicleType.setText(vehicleType);
                textViewDuration.setText(duration);
                textViewDiscount.setText(discount);
                textViewSubTotal.setText(subTotal);
                textViewAccommodation.setText(accommodation);
                textViewWashing.setText(washing);
                textViewGrandTotal.setText(total);

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelParkingDataList.size();
    }

    public class RecentbillsViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewrecentbills;
        ImageView imageViewsyncstatus;
        TextView textViewtoken, textViewamount, textViewsno;

        public RecentbillsViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewsyncstatus = itemView.findViewById(R.id.sync_textviewcard);
            cardViewrecentbills = itemView.findViewById(R.id.cardviewrecentbills);
            textViewtoken = itemView.findViewById(R.id.token_textviewcard);
            textViewamount = itemView.findViewById(R.id.amount_textviewcard);
            textViewsno = itemView.findViewById(R.id.sn_textviewcard);


        }
    }


}
