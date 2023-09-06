package com.logispark.parkingmanagementlogispark.Sumni;

import android.app.Application;
import android.widget.Toast;

import com.logispark.parkingmanagementlogispark.IminPrinter.IminPrinterHelper;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    /**
     * Connect print service through interface library
     */
    private void init(){

//        IminPrinterHelper iminPrinterHelper = new IminPrinterHelper(getApplicationContext());
//        iminPrinterHelper.initPrinter();

//        SunmiPrintHelper.getInstance().initSunmiPrinterService(this);
    }
}
