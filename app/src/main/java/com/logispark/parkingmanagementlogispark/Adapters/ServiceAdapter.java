package com.logispark.parkingmanagementlogispark.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.models.ModelService;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceAdapterViewHolder> {

    private Context context;
    private List<ModelService> modelServiceList;


    public ServiceAdapter(Context context, List<ModelService> modelServiceList) {
        this.context = context;
        this.modelServiceList = modelServiceList;
    }



    @NonNull
    @Override
    public ServiceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.card_rate, parent, false);
        return new ServiceAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapterViewHolder holder, int position) {

        String serviceName = modelServiceList.get(position).getService();
        String rate = modelServiceList.get(position).getRate();

        holder.textViewServiceType.setText(serviceName);
        holder.textViewVehicleRate.setText(rate);
    }

    @Override
    public int getItemCount() {
        return modelServiceList.size();
    }

    public class ServiceAdapterViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewServiceRate;
        TextView textViewServiceType, textViewVehicleRate;

        public ServiceAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            cardViewServiceRate = itemView.findViewById(R.id.cardViewVehicleRate);
            textViewServiceType = itemView.findViewById(R.id.vehicleNameCardVehicleRate);
            textViewVehicleRate = itemView.findViewById(R.id.vehicleRateCardVehicleRate);
        }
    }
}
