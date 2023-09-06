package com.logispark.parkingmanagementlogispark.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.logispark.parkingmanagementlogispark.Adapters.RecentBillsAdapter;
import com.logispark.parkingmanagementlogispark.R;
//import com.logispark.parkingmanagementlogispark.main.Itemsold;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.models.ModelRecentBills;
import com.logispark.parkingmanagementlogispark.models.ModelSalesStats;
import com.logispark.parkingmanagementlogispark.utilites.CsvWriter;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DataFragment extends Fragment {

    private TextView todaysalesamounttv, todaysbillissuedtv, todaysitemsoldtv, textViewdialougedelete, txtitemnsoldclick;
    private String currentdate, filename;
    Dialog dialog;
    private DbHandler dbHandler;
    private Button btnyes, btnno, btnSendData;
    private RelativeLayout relativeLayoutbills, relativLayoutTotalsales, relativeLayouttotalitems, nodatamainrv;
    private int count, itemsold, totalitemssold;
    private RecyclerView recyclerViewrecentbills;
    private List<ModelParkingData> modelParkingDataList;
    private RecentBillsAdapter recentBillsAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        dbHandler = new DbHandler(getContext());
        final Activity activity = getActivity();
        getActivity().setTitle("Sales Data");

        todaysalesamounttv = view.findViewById(R.id.todaysalesamount);
        todaysbillissuedtv = view.findViewById(R.id.todaysbillissued);
        relativLayoutTotalsales = view.findViewById(R.id.reelativelayouttotalsales);
        relativeLayoutbills = view.findViewById(R.id.reelativelayouttotalbills);
        btnSendData = view.findViewById(R.id.buttonSendData);

        dbHandler = new DbHandler(getContext());

        currentdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        filename = "Sales_Data" + currentdate + ".csv";


        List<ModelParkingData> modelParkingData = dbHandler.getUnsyncedData();
        int unsyncedDatacount = modelParkingData.size();

//            if(unsyncedDatacount == 0){
//                btnSendData.setEnabled(false);
//            }

        btnSendData.setVisibility(View.GONE);
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                exportsales();
            }
        });

        modelParkingDataList = dbHandler.getTodaysAllSalesData();
//            relativeLayoutbills.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getContext(), Itemsold.class);
//                    getActivity().startActivity(intent);
//                }
//            });

        ModelSalesStats modelSalesStats = dbHandler.getSalesData();

        todaysalesamounttv.setText(String.valueOf(modelSalesStats.getTotalAmount()));
        todaysbillissuedtv.setText(String.valueOf(modelSalesStats.getTokens()));


        recyclerViewrecentbills = view.findViewById(R.id.recycleviewrecentbills);

        if (modelParkingDataList.size() != 0) {

            recyclerViewrecentbills.setLayoutManager(new LinearLayoutManager(getContext()));
            recentBillsAdapter = new RecentBillsAdapter(getContext(), modelParkingDataList,getActivity());
            recyclerViewrecentbills.setAdapter(recentBillsAdapter);
        } else {

        }


        return view;

    }


    private void exportsales() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            new ExportDatabaseCSVTask2().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {

            new ExportDatabaseCSVTask2().execute();
        }
    }

    public class ExportDatabaseCSVTask2 extends AsyncTask<String, Void, Boolean> {

        private final ProgressDialog dialog = new ProgressDialog(getContext());
        DbHandler dbhelper;

        @Override
        protected void onPreExecute() {
//            this.dialog.setMessage("Exporting database...");
//            this.dialog.show();
            dbhelper = new DbHandler(getContext());
        }

        protected Boolean doInBackground(final String... args) {

            File exportDir = new File(Environment.getExternalStorageDirectory(), "/upaya/");

            if (!exportDir.exists()) {


                exportDir.mkdirs();

            }

            File file = new File(exportDir, filename);

            try {
                file.createNewFile();
                CsvWriter csvWrite = new CsvWriter(new FileWriter(file));
                Cursor curCSV = dbhelper.getTodaysAllSalesDataCursor();
                csvWrite.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    String arrStr[] = null;
                    String[] mySecondStringArray = new String[curCSV.getColumnNames().length];
                    for (int i = 0; i < curCSV.getColumnNames().length; i++) {
                        mySecondStringArray[i] = curCSV.getString(i);
                    }
                    csvWrite.writeNext(mySecondStringArray);
                }
                csvWrite.close();
                curCSV.close();
                return true;
            } catch (IOException e) {
                Log.d("ExceptionInUpload", String.valueOf(e));
                return false;
            }
        }

        protected void onPostExecute(final Boolean success) {
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//            }
            if (success) {
                Toast.makeText(getContext(), "Export successful!", Toast.LENGTH_SHORT).show();
                ShareGif2();
            } else {
                Toast.makeText(getContext(), "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ShareGif2() {
        
        try{
            String[] addresses = {"prayushshrestha89@gmail.com", "pshrestha.logispark@gmail.com"};
            String text = "Hi, \n\n Here is the sales data for today! \n\n Regards, \n\n Upaya Parking App";
            Context context = getContext();
            File exportDir = new File(Environment.getExternalStorageDirectory(), "/upaya/");
            String fileName = filename;
            File filelocation = new File(exportDir, fileName);
            Uri uri = Uri.fromFile(filelocation);
            Intent fileintent = new Intent(Intent.ACTION_SEND);
            fileintent.setType("text/csv");
            String subject = "Upaya | Sales Data " + currentdate;
            fileintent.putExtra(Intent.EXTRA_SUBJECT, subject);
            fileintent.putExtra(Intent.EXTRA_EMAIL, addresses);
            fileintent.putExtra(Intent.EXTRA_TEXT, text);
            fileintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileintent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(fileintent, "Send Mail"));
        }
        
        catch (Exception e){

            Toast.makeText(getContext(), "Can", Toast.LENGTH_SHORT).show();
            
        }
       


    }


}