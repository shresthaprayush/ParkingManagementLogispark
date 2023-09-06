//package com.logispark.parkingmanagementlogispark.main;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.material.textfield.TextInputLayout;
//import com.logispark.parkingmanagementlogispark.R;
//import com.logispark.parkingmanagementlogispark.models.ModelUser;
//import com.logispark.parkingmanagementlogispark.models.ModelVehicle;
//import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
//
//public class RegisterUser extends AppCompatActivity {
//
////    private TextView vehicleTypeTextView;
////    private DbHandler dbHandler;
////    private TextInputLayout textInputLayoutFullNameSignUp,textInputLayoutVehicleNumberSignUp,textInputLayoutContactNumberSignUp;
////    private ImageView imageViewJeepSignUp, imageViewCarSignUp,imageViewScooterSignUp;
////    private Button buttonSignUp;
////    private String vechileNumber,customerName,vehicleType,contactNumber;
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_register_user);
////
////        dbHandler = new DbHandler(getApplicationContext());
////        vehicleTypeTextView = findViewById(R.id.vehicleTypeSignUp);
////        textInputLayoutVehicleNumberSignUp = findViewById(R.id.textInputVechileNumberSignUp);
////        textInputLayoutContactNumberSignUp = findViewById(R.id.textinputcontactsignup);
////        textInputLayoutFullNameSignUp = findViewById(R.id.textinputnamesignup);
////        imageViewCarSignUp = findViewById(R.id.imageViewCarSignUp);
////        imageViewJeepSignUp = findViewById(R.id.imageViewTruckSignUp);
////        imageViewScooterSignUp = findViewById(R.id.imageViewBikeSignUp);
////        buttonSignUp = findViewById(R.id.buttonSignupforsignup);
////
////        buttonSignUp.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                confirmInput();
////            }
////        });
////
////        imageViewCarSignUp.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                vehicleType = "CAR";
////                vehicleTypeTextView.setText(vehicleType);
////            }
////        });
////
////        imageViewScooterSignUp.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                vehicleType = "SCOOTER";
////                vehicleTypeTextView.setText(vehicleType);
////
////            }
////        });
////
////        imageViewJeepSignUp.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                vehicleType = "JEEP";
////                vehicleTypeTextView.setText(vehicleType);
////
////            }
////        });
////
////    }
////
////    private void confirmInput() {
////        if(!validateFullName() || !validateContactNumber()|| !validateVechileNumber() ||!validateVechileType()){
////            return;
////        }else{
////            saveInDb(vehicleType,vechileNumber,customerName,contactNumber);
////        }
////    }
////
////    private void saveInDb(String vehicleType, String vechileNumber, String customerName, String contactNumber) {
////
////        ModelUser modelUser = new ModelUser(-1,customerName,contactNumber);
////        long result = dbHandler.addUser(modelUser);
////        Toast.makeText(getApplicationContext(), String.valueOf(result), Toast.LENGTH_SHORT).show();
////
////        if(result==-1){
////            Toast.makeText(getApplicationContext(), "Error Inserting User", Toast.LENGTH_SHORT).show();
////        }else{
////            ModelVehicle modelVehicle = new ModelVehicle(-1,vechileNumber,vehicleType,result);
////            long vechile_id = dbHandler.addVehicle(modelVehicle);
////            Toast.makeText(getApplicationContext(), "Vechile ID"+String.valueOf(vechile_id), Toast.LENGTH_SHORT).show();
////        }
////
////    }
////
////    private boolean validateVechileType() {
////        vehicleType = vehicleTypeTextView.getText().toString().trim();
////        if (vehicleType.isEmpty()) {
////            Toast.makeText(getApplicationContext(), "Please Select Vehicle Type", Toast.LENGTH_SHORT).show();
////            return false;
////
////        } else {
////            return true;
////        }
////    }
////
////
////    private boolean validateVechileNumber() {
////        vechileNumber = textInputLayoutVehicleNumberSignUp.getEditText().getText().toString().trim();
////        if (vechileNumber.isEmpty()) {
////            textInputLayoutVehicleNumberSignUp.getEditText().setError("Required Vehicle Number");
////            return false;
////
////        } else {
////            textInputLayoutVehicleNumberSignUp.getEditText().setError(null);
////            return true;
////        }
////    }
////
////    private boolean validateFullName() {
////         customerName = textInputLayoutFullNameSignUp.getEditText().getText().toString().trim();
////        if (customerName.isEmpty()) {
////            textInputLayoutFullNameSignUp.getEditText().setError("Required Full Number");
////            return false;
////
////        } else {
////            textInputLayoutFullNameSignUp.getEditText().setError(null);
////            return true;
////        }
////    }
////
////    private boolean validateContactNumber() {
////        contactNumber = textInputLayoutContactNumberSignUp.getEditText().getText().toString().trim();
////        if (contactNumber.isEmpty()) {
////            textInputLayoutContactNumberSignUp.getEditText().setError("Required Contact Number");
////            return false;
////
////        } else {
////            textInputLayoutContactNumberSignUp.getEditText().setError(null);
////            return true;
////        }
////
////    }
////}