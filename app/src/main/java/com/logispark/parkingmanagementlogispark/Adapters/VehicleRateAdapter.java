package com.logispark.parkingmanagementlogispark.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;

import java.util.List;

public class VehicleRateAdapter extends RecyclerView.Adapter<VehicleRateAdapter.VechileRateViewHolder> {
    private Context context;
    private List<ModelVehicleRate> modelVehicleRateList;

    public VehicleRateAdapter(Context context, List<ModelVehicleRate> modelVehicleRateList) {
        this.context = context;
        this.modelVehicleRateList = modelVehicleRateList;
    }

    @NonNull
    @Override
    public VechileRateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.card_rate, parent, false);
        return new VechileRateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VechileRateViewHolder holder, int position) {

        String vehicleType = modelVehicleRateList.get(position).getVehicleType();
        int rate = modelVehicleRateList.get(position).getRate();
        int syncStatus = modelVehicleRateList.get(position).getSyncStatus();
        int discount = modelVehicleRateList.get(position).getDiscount();
        String discountS = discount+" %";



        holder.textViewVehicleType.setText(vehicleType);
        holder.textViewVehicleRate.setText(String.valueOf(rate));
        holder.textViewDiscount.setVisibility(View.GONE);

        if(discount!=0){
            holder.cardViewVehicleRate.setBackgroundResource(R.drawable.button_accept);
            holder.textViewDiscount.setVisibility(View.VISIBLE);
            holder.textViewDiscount.setText(discountS);

//            holder.cardViewVehicleRate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Discount  % : "+String.valueOf(discount), Toast.LENGTH_SHORT).show();
//                }
//            });
        }

    }

    @Override
    public int getItemCount() {
        return modelVehicleRateList.size();
    }

    public class VechileRateViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewVehicleRate;
        TextView textViewVehicleType, textViewVehicleRate,textViewDiscount;

        public VechileRateViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewVehicleRate = itemView.findViewById(R.id.cardViewVehicleRate);
            textViewVehicleType = itemView.findViewById(R.id.vehicleNameCardVehicleRate);
            textViewVehicleRate = itemView.findViewById(R.id.vehicleRateCardVehicleRate);
            textViewDiscount = itemView.findViewById(R.id.txtDiscount);
        }
    }
}
