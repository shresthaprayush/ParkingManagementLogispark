package com.logispark.parkingmanagementlogispark.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.fragment.VechileCheckIn;
import com.logispark.parkingmanagementlogispark.main.MainActivity;
import com.logispark.parkingmanagementlogispark.models.ModelVehicleRate;

import org.w3c.dom.Text;

import java.util.List;

public class CheckInRatesAdapter extends RecyclerView.Adapter<CheckInRatesAdapter.CheckInRateViewHolder> {
    private Context context;
    private List<ModelVehicleRate> modelVehicleRateList;
    private Fragment parent;

    public CheckInRatesAdapter(Context context, List<ModelVehicleRate> modelVehicleRateList, Fragment parent) {
        this.context = context;
        this.modelVehicleRateList = modelVehicleRateList;
        this.parent = parent;
    }

    @NonNull
    @Override
    public CheckInRateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.card_rates_checkin,parent,false);
        return new CheckInRateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckInRateViewHolder holder, int position) {

        String rateName = modelVehicleRateList.get(position).getVehicleType();
        int rate = modelVehicleRateList.get(position).getRate();

        int discount = modelVehicleRateList.get(position).getDiscount();

        holder.textViewRateListChekIn.setText(rateName);

        holder.cardViewRateListCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(parent instanceof VechileCheckIn){

                    ((VechileCheckIn) parent).getRate(rate,discount,rateName);

                }
                else{

                    Log.d("Rate Passing","Error in Passing Rate");
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return modelVehicleRateList.size();
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
