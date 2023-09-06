//package com.logispark.parkingmanagementlogispark.Adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.logispark.parkingmanagementlogispark.R;
//import com.logispark.parkingmanagementlogispark.models.ModelRecentBillsData;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder> {
//    private Context context;
//    private List<ModelRecentBillsData> modelRecentBills;
//
//    public SalesAdapter(Context context, List<ModelRecentBillsData> modelRecentBills) {
//        this.context = context;
//        this.modelRecentBills = modelRecentBills;
//    }
//
//    @NonNull
//    @Override
//    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        view = layoutInflater.inflate(R.layout.cardsales,parent,false);
//        return new SalesViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SalesViewHolder holder, int position) {
//
//        holder.textViewsno.setText(String.valueOf(position+1));
//        int id,token;
//        id = modelRecentBills.get(position).getTokenid();
//        token = modelRecentBills.get(position).getToken();
//        holder.textViewtoken.setText(String.valueOf(modelRecentBills.get(position).getToken()));
//        holder.textViewamount.setText(String.valueOf(modelRecentBills.get(position).getAmount()));
//        holder.textViewdate.setText(String.valueOf(modelRecentBills.get(position).getDate()));
//
//        holder.cardViewsales.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(context, OrderDetailsActivity.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                intent.putExtra("id",id);
////                intent.putExtra("token",token);
////                context.startActivity(intent);
//            }
//        });
//
//
//    }
//
//
//    public int getItemCount() {
//        return modelRecentBills.size();
//    }
//
//    public class SalesViewHolder extends RecyclerView.ViewHolder {
//        CardView cardViewsales;
//        TextView textViewtoken,textViewamount,textViewsno,textViewdate,textViewadditional,textViewdiscount;
//        public SalesViewHolder(@NonNull View itemView) {
//            super(itemView);
//            cardViewsales = itemView.findViewById(R.id.cardviewsales);
//            textViewtoken = itemView.findViewById(R.id.token_textviewcardsales);
//            textViewamount = itemView.findViewById(R.id.amount_textviewcardsales);
//            textViewsno = itemView.findViewById(R.id.sn_textviewcardsales);
//            textViewdate = itemView.findViewById(R.id.date_textviewcardsaels);
//
//        }
//    }
//
//    public void updatelist(ArrayList<ModelRecentBillsData> newlist) {
//        //This function is used to update the array on the search.
//        modelRecentBills = new ArrayList<>();
//        modelRecentBills.addAll(newlist);
//        notifyDataSetChanged();
//    }
//
//}
