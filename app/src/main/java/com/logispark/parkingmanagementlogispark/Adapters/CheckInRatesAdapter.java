package com.logispark.parkingmanagementlogispark.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.fragment.VechileCheckIn;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;

import java.util.List;

public class CheckInRatesAdapter extends RecyclerView.Adapter<CheckInRatesAdapter.CheckInRateViewHolder> {
    private Context context;
    private List<ModelVehicleRate> modelVehicleRateList;
    private Fragment parent;
    private int selectedPosition = -1;


    public CheckInRatesAdapter(Context context, List<ModelVehicleRate> modelVehicleRateList, Fragment parent) {
        this.context = context;
        this.modelVehicleRateList = modelVehicleRateList;
        this.parent = parent;
    }

    @NonNull
    @Override
    public CheckInRateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_rates_checkin, parent, false);
        return new CheckInRateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInRateViewHolder holder, int position) {

        String rateName = modelVehicleRateList.get(position).getVehicleType();

        holder.textViewRateListChekIn.setText(rateName);

        if (selectedPosition == position) {
            holder.cardViewRateListCheckIn.setCardBackgroundColor(Color.parseColor("#E0E0E0"));
        } else {
            holder.cardViewRateListCheckIn.setCardBackgroundColor(Color.WHITE);
        }


        holder.cardViewRateListCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return;
                }

                if (parent instanceof VechileCheckIn) {
                    ModelVehicleRate vehicleRate = modelVehicleRateList.get(adapterPosition);
                    ((VechileCheckIn) parent).getRate(vehicleRate.getRate(), vehicleRate.getDiscount(), vehicleRate.getVehicleType(), vehicleRate.getIsThirtyMinActivation(), vehicleRate.getExceedingLimit(), vehicleRate.getHalfHourCost());
                    selectedPosition = adapterPosition;
                    notifyDataSetChanged();
                } else {
                    Log.d("Rate Passing", "Error in Passing Rate");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelVehicleRateList.size();
    }

    public void clearSelection() {
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    public class CheckInRateViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewRateListCheckIn;
        TextView textViewRateListChekIn;

        public CheckInRateViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewRateListCheckIn = itemView.findViewById(R.id.cardViewRateCheckIn);
            textViewRateListChekIn = itemView.findViewById(R.id.textviewratenameCheckin);

        }
    }


}
