package com.logispark.parkingmanagementlogispark.utilites;

import android.content.Context;
import android.util.Log;
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

    public int getHoursPassed(ModelParkingData modelParkingData){

        try {
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String inTime = modelParkingData.getInTime();
            String endTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
            Date l1 = dtf.parse(inTime);
            Date l2 = dtf.parse(endTime);
            assert l2 != null;
            long diff = l2.getTime() - l1.getTime();
            int hours = (int) Math.ceil(diff / (1000 * 60 * 60));
            return hours;

        } catch (Exception e) {
            Toast.makeText(context, "Hours Between " + String.valueOf(e), Toast.LENGTH_SHORT).show();
            return -1;
        }

    }
    public String getDuration(ModelParkingData modelParkingData) {
        String endTime;
        try {
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
            String inTime = modelParkingData.getInTime();
            String outTime = modelParkingData.getOutTime();
            Log.d("durations outTime", outTime);

            if (outTime != null && !outTime.isEmpty()) {
                // If an outTime exists and is not empty, use it.
                endTime = outTime;
            } else {
                // Otherwise, use the current system time.
                endTime = dtf.format(new Date());
            }
            Date start = dtf.parse(inTime);
            Date end = dtf.parse(endTime);

            if (start == null || end == null) {
                return "Invalid time";
            }

            long diff = end.getTime() - start.getTime();

            int hours = (int) (diff / (1000 * 60 * 60));
            int minutes = (int) ((diff / (1000 * 60)) % 60);

            return hours + " hrs " + minutes + " mins";

        } catch (Exception e) {
            return "Error";
        }
    }
    public String getTotalCost(ModelParkingData modelParkingData) {
        int cost = modelParkingData.getRate();
        int is30MinActivation = modelParkingData.getIs30MinActivation();
        int halfHourCost = modelParkingData.getHalfHourCost();
        long chargeableMinutes = getChargeableMinutes(modelParkingData);

        if (chargeableMinutes < 0) {
            return "Error";
        }
        if (chargeableMinutes<60){
         return String.valueOf(cost);
        }
        if (is30MinActivation == 1) {
            double totalCost;
            // Convert minutes → hours & minutes
            int hoursPart = Math.toIntExact(chargeableMinutes / 60);
            long minutesPart = chargeableMinutes % 60;

            if (minutesPart <= 30) {
                totalCost =  halfHourCost + (hoursPart * cost);
            }else {
                totalCost = (hoursPart + 1) * cost;
            }

//            double totalCost = Math.ceil(chargeableMinutes / 30.0) * (cost / 2.0);
            return String.valueOf(totalCost);
        } else {
            int hours;
            hours = (int) Math.ceil(chargeableMinutes / 60.0);
            double totalCost = (double) hours * cost;
            return String.valueOf(totalCost);
        }
    }
    private long getChargeableMinutes(ModelParkingData modelParkingData) {
        try {
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
            String inTime = modelParkingData.getInTime();
            String endTime = dtf.format(new Date());

            Date start = dtf.parse(inTime);
            Date end = dtf.parse(endTime);

            if (start == null || end == null) {
                return -1;
            }

            long diff = end.getTime() - start.getTime();
            long totalMinutes = diff / (1000 * 60);
            long exceedingLimit = modelParkingData.getExceedingLimit();

            return Math.max(0, totalMinutes - exceedingLimit);
        } catch (Exception e) {
            return -1;
        }
    }


}
