package com.logispark.parkingmanagementlogispark.utilites;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static String formatTime(String time24) {
        try {
            SimpleDateFormat sdf24 = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
            Date dateObj = sdf24.parse(time24);
            return sdf12.format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
            return time24; // fallback to 24-hour format if parsing fails
        }
    }
}
