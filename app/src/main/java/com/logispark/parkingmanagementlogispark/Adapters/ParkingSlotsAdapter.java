package com.logispark.parkingmanagementlogispark.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.fragment.VechileCheckIn;
import com.logispark.parkingmanagementlogispark.main.MainActivity;
import com.logispark.parkingmanagementlogispark.models.ModelSlots;

import java.util.List;

public class ParkingSlotsAdapter extends RecyclerView.Adapter<ParkingSlotsAdapter.ParkingSlotViewHolder> {

    private Context context;
    private List<ModelSlots> modelSlotsList;
    private Activity parents;
    private Dialog dialog;
private View view;
    Fragment fragment;


    public ParkingSlotsAdapter(Context context, List<ModelSlots> modelSlotsList, Activity parents) {
        this.context = context;
        this.modelSlotsList = modelSlotsList;
        this.parents = parents;
    }

    @NonNull
    @Override
    public ParkingSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d("CardView", "Started View");

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.carparkingslots, parent, false);
        return new ParkingSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingSlotViewHolder holder, int position) {

        int slotId = modelSlotsList.get(position).getId();
        String slotName = modelSlotsList.get(position).getName();
        int active = modelSlotsList.get(position).getActive();
        Log.d("CardView", "Binding View View");

        Log.d("CardView", slotName);
        holder.textView.setText(slotName);

        if(parents instanceof MainActivity){

            if (active == 1) {

                holder.relativeLayoutSlot.setBackgroundResource(R.drawable.button_reject);
//            holder.relativeLayoutSlot.setBackgroundColor(R.drawable.button_reject);
//            holder.textView.setTextColor(Color.parseColor("#ff3d71"));


                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = new Dialog(parents);
                        RelativeLayout btnClose = dialog.findViewById(R.id.closeitemdialouge);

                        dialog.setContentView(R.layout.dialougeitemdetails);
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    btnClose.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.cancel();
//                        }
//                    });
                        dialog.show();

                    }//

                });
//            //todo: Link Database to show vehicle Detaisl
//            dialog = new Dialog(parents);
//            dialog.setContentView(R.layout.dialougeitemdetails);

            } else if (active == 0) {
                holder.relativeLayoutSlot.setBackgroundResource(R.drawable.button_accept);
//            holder.textView.setTextColor(Color.parseColor("#ff3d71"));
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        fragment = new VechileCheckIn();
                        Bundle bundle = new Bundle();
                        bundle.putInt("slotID",slotId);
                        bundle.putString("slotName",slotName);
                        fragment.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentcontainer, fragment).addToBackStack(null).commit();
                    }
                });
//
            } else {
                Log.e("ParkingAdapter", "Wrong Data");
            }
        }



    }

    @Override
    public int getItemCount() {
        return modelSlotsList.size();
    }

    public class ParkingSlotViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textView;
        RelativeLayout relativeLayoutSlot;

        public ParkingSlotViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewSlots);
            textView = itemView.findViewById(R.id.textviewslotname);
            relativeLayoutSlot = itemView.findViewById(R.id.relativecolorchange);

        }
    }
}
