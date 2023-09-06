package com.logispark.parkingmanagementlogispark.IminPrinter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.imin.printerlib.IminPrintUtils;
import com.logispark.parkingmanagementlogispark.models.ModelEstimate;
import com.logispark.parkingmanagementlogispark.models.ModelParkingSlip;

public class IminPrinterHelper {

    private IminPrintUtils mIminPrintUtils;
    private Context context;


    public IminPrinterHelper(Context context) {
        this.context = context;
    }


    public boolean initPrinter() {

        try{
            if (mIminPrintUtils == null) {
                mIminPrintUtils = IminPrintUtils.getInstance(context);


            }
            mIminPrintUtils.resetDevice();
            mIminPrintUtils.initPrinter(IminPrintUtils.PrintConnectType.USB);

            return true;
        }

        catch (Exception e){
            return false;
        }

    }

    public boolean printReceipt(ModelParkingSlip modelParkingSlip,int slipCount) {
        try {

            mIminPrintUtils.setTextStyle(Typeface.BOLD);
            mIminPrintUtils.setTextSize(35);
            mIminPrintUtils.setAlignment(1);
            String companyName = "Upaya Parking System";
            mIminPrintUtils.printText(companyName + "\n");

            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText("------------------------------------------------\n");

            String title = "Parking Receipt\n";
            mIminPrintUtils.setAlignment(1);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(title);

            if(slipCount!=0){
                String copy = "Copy No #" + String.valueOf(slipCount);
                mIminPrintUtils.setAlignment(1);
                mIminPrintUtils.setTextSize(24);
                mIminPrintUtils.printText(copy);
            }


            //todo : Try to change QR
            mIminPrintUtils.setQrCodeSize(10);
            mIminPrintUtils.printQrCode(modelParkingSlip.getUniqueId(),1);
            mIminPrintUtils.printAndFeedPaper(15);

            mIminPrintUtils.setAlignment(1);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(modelParkingSlip.getUniqueId() + "\n");



            mIminPrintUtils.setAlignment(1);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText("Vehicle Number: " + modelParkingSlip.getVechileNumber() + "\n");

            mIminPrintUtils.setAlignment(1);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText("Vehicle Type: " + modelParkingSlip.getVehicleType() + "\n");


            if(modelParkingSlip.getSlotName() != null){
                mIminPrintUtils.setAlignment(1);
                mIminPrintUtils.setTextSize(28);
                mIminPrintUtils.printText("Slot Name : " + modelParkingSlip.getSlotName() + "\n");
            }

            mIminPrintUtils.setAlignment(1);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText("Rate : " + modelParkingSlip.getRate() + "\n");


            mIminPrintUtils.setAlignment(1);
            mIminPrintUtils.setTextSize(28);
            String washing = "-";
            String accommodation = "-";

            if(modelParkingSlip.isAccommodation() && modelParkingSlip.isWashing()){
                mIminPrintUtils.setAlignment(1);
                mIminPrintUtils.setTextSize(28);
                 washing = "-";
                 accommodation = "-";
                if(modelParkingSlip.isAccommodation()){

                    accommodation = "A";
                }

                if(modelParkingSlip.isWashing()){
                    washing = "W";

                }

                String additionalText = "Additional : " + washing + " " + accommodation;
                mIminPrintUtils.printText(additionalText + "\n");

            }
            else if(modelParkingSlip.isAccommodation()){
                accommodation = "A";
                String additionalText = "Additional : " + accommodation;
                mIminPrintUtils.printText(additionalText + "\n");
            }
            else if(modelParkingSlip.isWashing()){
                washing = "W";
                String additionalText = "Additional : " + washing;
                mIminPrintUtils.printText(additionalText + "\n");
            }


            mIminPrintUtils.setAlignment(1);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText("Check In Date : " + modelParkingSlip.getDate() + "\n");


            mIminPrintUtils.setAlignment(1);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText("Check In Time : " + modelParkingSlip.getTime() + "\n");


            mIminPrintUtils.printText("------------------------------------------------\n");
            mIminPrintUtils.printAndFeedPaper(80);

            return  true;

        } catch (Exception e) {

            Log.e("Exeception In Printing", String.valueOf(e));
            return false;
        }
    }


    public boolean printEstimate(ModelEstimate modelEstimate, int estimateCount) {
        try {

            mIminPrintUtils.setTextStyle(Typeface.BOLD);
            mIminPrintUtils.setTextSize(35);
            mIminPrintUtils.setAlignment(1);
            String title = "Tax Invoice";
            mIminPrintUtils.printText(title + "\n\n");

            if(estimateCount!=0){
                String copy = "Copy No #" + String.valueOf(estimateCount);
                mIminPrintUtils.setAlignment(1);
                mIminPrintUtils.setTextSize(24);
                mIminPrintUtils.printText(copy);
            }


            mIminPrintUtils.setTextStyle(Typeface.BOLD);
            mIminPrintUtils.setTextSize(35);
            mIminPrintUtils.setAlignment(1);
            String companyName = "Upaya Parking System";
            mIminPrintUtils.printText(companyName+ "\n\n");


            mIminPrintUtils.setTextSize(25);
            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.printText("Date : " + modelEstimate.getDate() + "\n");

            mIminPrintUtils.setTextSize(25);
            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.printText(modelEstimate.getUniqueId() + "\n");

            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText("------------------------------------------------\n");

            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(modelEstimate.getVehicleNumber() + "\n");

            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(modelEstimate.getCheckInTime() + "\n");

            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(modelEstimate.getRate() + "\n");

            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(modelEstimate.getDuration() + "\n");

            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(modelEstimate.getDiscount() + "\n");


            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(modelEstimate.getSubTotal() + "\n");

            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(modelEstimate.getAccommodation() + "\n");

            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.printText(modelEstimate.getWashing() + "\n");

            mIminPrintUtils.printText("------------------------------------------------\n");

            mIminPrintUtils.setTextStyle(Typeface.BOLD);
            mIminPrintUtils.setTextSize(35);
            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.printText(modelEstimate.getTotalCost() + "\n");


            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.printText("------------------------------------------------\n");

            mIminPrintUtils.setTextSize(20);
            mIminPrintUtils.setAlignment(1);
            mIminPrintUtils.printText("Powered By : Nepvent.com\n");

            mIminPrintUtils.setTextSize(28);
            mIminPrintUtils.setAlignment(0);
            mIminPrintUtils.printText("------------------------------------------------\n");


            mIminPrintUtils.printAndFeedPaper(80);

            return true;

        } catch (Exception e) {

            Log.e("Exception In Printing", String.valueOf(e));
            return false;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void printColumns() {

        String[] strings3 = new String[]{"Test", "Description Description Description@48", "192.00"};
        int[] colsWidthArr3 = new int[]{2, 6, 2};
        int[] colsAlign3 = new int[]{0, 0, 2};
        int[] colsSize3 = new int[]{26, 26, 26};
        mIminPrintUtils.printColumnsText(strings3, colsWidthArr3,
                colsAlign3, colsSize3);


        mIminPrintUtils.printAndFeedPaper(100);

    }

    public void printQrCode() {

        mIminPrintUtils.printQrCode("123456", 0);
        //中
        mIminPrintUtils.printQrCode("123456", 1);
        //右
        mIminPrintUtils.printQrCode("123456", 2);
        mIminPrintUtils.printAndFeedPaper(100);

    }
}
