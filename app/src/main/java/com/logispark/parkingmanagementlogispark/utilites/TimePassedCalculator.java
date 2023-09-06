package com.logispark.parkingmanagementlogispark.utilites;

import android.content.Context;
import android.widget.Toast;

import com.logispark.parkingmanagementlogispark.models.ModelParkingData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimePassedCalculator {
    private Context context;

    public TimePassedCalculator(Context context) {
        this.context = context;
    }

    public int getDaysPassed(ModelParkingData modelParkingData){

        try {
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String inTime = modelParkingData.getInTime();
            String endTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
            Date l1 = dtf.parse(inTime);
            Date l2 = dtf.parse(endTime);
            long diff = l1.getTime() - l2.getTime();
            int days = (int) Math.abs(diff / (1000 * 60 * 60 * 24));
            return days;

        } catch (Exception e) {
            Toast.makeText(context, "Days Between " + String.valueOf(e), Toast.LENGTH_SHORT).show();
            return -1;
        }

    }
}
