package com.logispark.parkingmanagementlogispark.utilites;

import android.content.Context;
import android.util.DisplayMetrics;

public class GridUtility {

    public static int calculateNoOfColumns(Context context,float columwidth){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columwidth + 0.5);
        return noOfColumns;

    }

}
