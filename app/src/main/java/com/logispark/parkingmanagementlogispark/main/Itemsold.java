//package com.logispark.parkingmanagementlogispark.main;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.app.DatePickerDialog;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.logispark.parkingmanagementlogispark.Adapters.SalesAdapter;
//import com.logispark.parkingmanagementlogispark.R;
//import com.logispark.parkingmanagementlogispark.models.ModelRecentBillsData;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//public class Itemsold extends AppCompatActivity {
//
//    private Button btnstartdate, btnenddate, btngo;
//    private RecyclerView recyclerViewsalesdata;
//    private SalesAdapter recentBillsAdapter;
//    private List<ModelRecentBillsData> modelRecentBillsList = new ArrayList<>();
//    private int year, year2, month, month2, i = 0, day, day2, rid;
//    private Calendar calendar, calendar2;
//    private String startdate, enddate,filename,filename2;
//    private RelativeLayout nodata;
//    private TextView tvtotal, tvtotaladditional,tvtotaldisount;
//    private int totals=0 ;
//    private  int totaladditionals = 0;
//    private  int totaldiscounts = 0;
//    private String total;
//    private List<ModelRecentBillsData> modelRecentBillsListdata = new ArrayList<>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_itemsold);
//
//        btnstartdate = findViewById(R.id.startdateitem);
//        btnenddate = findViewById(R.id.enddateitem);
//        btngo = findViewById(R.id.goitem);
//        recyclerViewsalesdata = findViewById(R.id.itemsoldhistory);
//
//        btngo.setVisibility(View.GONE);
//
//
//        modelRecentBillsListdata.add(new ModelRecentBillsData(12,1,40,"2022-10-12",0,2,3));
//        modelRecentBillsListdata.add(new ModelRecentBillsData(13,1,40,"2022-10-12",0,2,3));
//        modelRecentBillsListdata.add(new ModelRecentBillsData(14,1,45,"2022-10-12",0,2,3));
//        modelRecentBillsListdata.add(new ModelRecentBillsData(15,1,50,"2022-10-12",0,2,3));
//        modelRecentBillsListdata.add(new ModelRecentBillsData(16,1,52,"2022-10-12",0,2,3));
//        modelRecentBillsListdata.add(new ModelRecentBillsData(17,1,55,"2022-10-12",0,2,3));
//
////        filename = "TotalSales"+getCurrentdatefull()+".csv";
////        filename2 = "TotalSalesToday"+getCurrentdate()+".csv";
//        recyclerViewsalesdata.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recentBillsAdapter = new SalesAdapter(getApplicationContext(), modelRecentBillsListdata);
//        recyclerViewsalesdata.setAdapter(recentBillsAdapter);
//
//
//
//        btnstartdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendar = Calendar.getInstance();
//                year = calendar.get(Calendar.YEAR);
//                month = calendar.get(Calendar.MONTH);
//                day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//
//
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(Itemsold.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//
//                            String months,days;
//                            if(month < 10){
//                                months =  "0"+ (month+1);
//                            }
//                            else{
//                                months = String.valueOf(month);
//                            }
//                            if(dayOfMonth < 10){
//                                days = "0"+ dayOfMonth;
//                            }
//                            else{
//                                days = String.valueOf(dayOfMonth);
//                            }
//
//                            startdate = year + "-" + months + "-" + days;
//
//                            btnstartdate.setText(startdate);
//                            i = i + 1;
//                            if (i >= 2) {
//                                checkvisibility();
//                            }
//
//                        }
//                    }, year, month, day);
//                    datePickerDialog.show();
//
//                }
//
//            }
//        });
//
//        btnenddate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                calendar2 = Calendar.getInstance();
//                year2 = calendar2.get(Calendar.YEAR);
//                month2 = calendar2.get(Calendar.MONTH);
//                day2 = calendar2.get(Calendar.DAY_OF_MONTH);
//
//
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//
//
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(Itemsold.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                            String months,days;
//                            if(month < 10){
//                                months =  "0"+ (month+1);
//                            }
//                            else{
//                                months = String.valueOf(month);
//                            }
//                            if(dayOfMonth < 10){
//                                days = "0"+ dayOfMonth;
//                            }
//                            else{
//                                days = String.valueOf(dayOfMonth);
//                            }
//                            enddate = year + "-" + months + "-" + days;
//                            btnenddate.setText(enddate);
//                            i = i + 1;
//                            if (i >= 2) {
//                                checkvisibility();
//                            }
//
//                        }
//                    }, year, month, day2);
//                    datePickerDialog.show();
//
//                }
//
//            }
//        });
//
//    }
//
//    private void checkvisibility() {
//
//
//        if (!(startdate.equals(null) && startdate.equals("START DATE"))) {
//
//            if (!(enddate.equals(null) && enddate.equals("END DATE"))) {
//
//                btngo.setVisibility(View.VISIBLE);
//
//
//            }
//        }
//
//    }
//
//}