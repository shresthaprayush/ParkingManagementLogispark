package com.logispark.parkingmanagementlogispark.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
//import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.utilites.PrintPic;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class QrCode extends AppCompatActivity {

    OutputStream mmOutputStream;
    private Button btnPrintQRCode;
    private ImageView imageViewQr;
    private TextView textViewDate,textViewVechile;
    String date,time,vechileNumber,uniqueId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        imageViewQr = findViewById(R.id.imageViewQR);
        textViewDate = findViewById(R.id.textViewDate);
        textViewVechile = findViewById(R.id.textViewVechile);

        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        vechileNumber = getIntent().getStringExtra("vechileNo");
        uniqueId = getIntent().getStringExtra("uniqueId");
        btnPrintQRCode = findViewById(R.id.printQrCode);

        textViewDate.setText("In Time : "+date+" | "+time);
        textViewVechile.setText("Vechile Number :"+vechileNumber);


        MultiFormatWriter writer = new MultiFormatWriter();

        BitMatrix matrix = null;
        try {
            matrix = writer.encode(uniqueId, BarcodeFormat.QR_CODE,400,400);
        } catch (WriterException e) {
            e.printStackTrace();
        }
//        BarcodeEncoder encoder = new BarcodeEncoder();
//        Bitmap bitmap = encoder.createBitmap(matrix);
//        imageViewQr.setImageBitmap(bitmap);

        btnPrintQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Print QR Code","Printing QR CODE");
                Log.d("Print QR Code","On Try");


                final Handler handler = new Handler();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.d("Print QR Code","On Run");
                            Socket sock = new Socket("192.168.1.101", 9100);
                            mmOutputStream = sock.getOutputStream();
                            PrintWriter oStream = new PrintWriter(sock.getOutputStream());
                            PrintPic printPic1 = PrintPic.getInstance();
//                            printPic1.init(bitmap);
                            byte[] bitmapdata2 = printPic1.printDraw();
                            mmOutputStream.write(bitmapdata2);
                            oStream.write("DASdaSDads");
                            oStream.println("\n\n\n");
                            oStream.close();
                            sock.close();
                            Log.d("Print QR Code","Closed");




                        } catch (final IOException e) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
//                            handleException(e);

                                }
                            });
                        }


                    }
                }).start();


            }
        });


    }

    public void printQrCode(Bitmap qRBit) {
        try {
            PrintPic printPic1 = PrintPic.getInstance();
            printPic1.init(qRBit);
            byte[] bitmapdata2 = printPic1.printDraw();
            mmOutputStream.write(bitmapdata2);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}




