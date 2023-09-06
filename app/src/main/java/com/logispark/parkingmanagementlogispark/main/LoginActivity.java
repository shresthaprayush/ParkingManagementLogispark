package com.logispark.parkingmanagementlogispark.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.models.ModelDeviceSpecificInformation;
import com.logispark.parkingmanagementlogispark.models.ModelSuccessLogin;
import com.logispark.parkingmanagementlogispark.models.ModelUser;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;
import com.logispark.parkingmanagementlogispark.utilites.RetrofitClient;
import com.logispark.parkingmanagementlogispark.utilites.SharedPreferenceManager;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private String uniqueDeviceId, branch = "";
    private ProgressDialog progressDialog;

    private ImageView imageViewLogin;
    private ModelUser modelUser;
    private DbHandler dbHandler;
    private TextInputLayout textInputLayoutPasswordLogin, textInputLayoutContactNumberLogin;
    private Button buttonLogin;

    private Call<ModelSuccessLogin> callLogin;
    ModelDeviceSpecificInformation modelDeviceSpecificInformation;
    private String inputPassword, inputContactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Logging In");

        imageViewLogin = findViewById(R.id.imageviewLogin);

        modelDeviceSpecificInformation = SharedPreferenceManager.getmInstance(getApplicationContext()).getDeviceInformation();
        textInputLayoutContactNumberLogin = findViewById(R.id.textinputcontactLogin);
        textInputLayoutPasswordLogin = findViewById(R.id.textinputpasswordLogin);
        buttonLogin = findViewById(R.id.buttonSignupforsignup);

        if (modelDeviceSpecificInformation.getUniqueId() == null) {
            uniqueDeviceId = generateUniqueId();
        } else {
            uniqueDeviceId = modelDeviceSpecificInformation.getUniqueId();
        }

        Glide.with(getApplicationContext()).load(R.drawable.logowhite).into(imageViewLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                confirmInput();


            }
        });

    }

    /**
     * Function to generate uniqueID from time
     *
     * @return string value of unique id
     */
    public String generateUniqueId() {
        Date date = new Date();
        long timeinMili = date.getTime();
        uniqueDeviceId = "Device" + String.valueOf(timeinMili);
        return uniqueDeviceId;

    }



    /**
     * Function used to check inputs makes use of validateInput() and validatePassword()
     *
     * @return the control if not validate
     */
    private void confirmInput() {
        if (!validatePassword() || !validateContactNumber()) {
            return;
        } else {

            validateInServer(inputContactNumber, inputPassword, uniqueDeviceId);
        }
    }


    /**
     * Function to validate password use with confirmInput()
     *
     * @return true if validated false if not
     */
    private boolean validatePassword() {
        inputPassword = textInputLayoutPasswordLogin.getEditText().getText().toString().trim();
        if (inputPassword.isEmpty()) {
            textInputLayoutPasswordLogin.getEditText().setError("Required Password");
            return false;

        } else {
            textInputLayoutPasswordLogin.getEditText().setError(null);
            return true;
        }
    }

    /**
     * Function to validate contact use with method confirmInput()
     *
     * @return true if validated false if not
     */
    private boolean validateContactNumber() {
        inputContactNumber = textInputLayoutContactNumberLogin.getEditText().getText().toString().trim();
        if (inputContactNumber.isEmpty()) {
            textInputLayoutContactNumberLogin.getEditText().setError("Required Contact Number");
            return false;

        } else {
            textInputLayoutContactNumberLogin.getEditText().setError(null);
            return true;
        }

    }

    /**
     * Send data to server for validation Api calling
     *
     * @param inputContactNumber
     * @param inputPassword
     * @param uniqueDeviceId
     */
    private void validateInServer(String inputContactNumber, String inputPassword, String uniqueDeviceId) {

        callLogin = RetrofitClient.getmInstance().getretrofit(getApplicationContext()).loginUser(inputContactNumber, inputPassword, uniqueDeviceId);

        callLogin.enqueue(new Callback<ModelSuccessLogin>() {
            @Override
            public void onResponse(Call<ModelSuccessLogin> call, Response<ModelSuccessLogin> response) {
                if (response.body() == null) {
                    Log.d("Response", String.valueOf(response.body()));
                    showtoast(getString(R.string.unexpected_error),R.drawable.error);
                }
                else{

                    ModelSuccessLogin modelSuccessLogin = response.body();

                    if(!modelSuccessLogin.getSuccess().equals("error")){
                        modelUser = modelSuccessLogin.getData();
                        branch = modelUser.getBranch();
                        String branchCode = modelUser.getCode();

                        ModelUser savedUser = new ModelUser(-1,modelUser.getContact(),uniqueDeviceId,modelUser.getBranch(),modelUser.getName(),modelUser.getCode());
                        SharedPreferenceManager.getmInstance(getApplicationContext()).saveuser(savedUser);
                        ModelDeviceSpecificInformation modelDeviceSpecificInformation = new ModelDeviceSpecificInformation(branch,uniqueDeviceId,null,branchCode,false);
                        SharedPreferenceManager.getmInstance(getApplicationContext()).save_deviceInformation(modelDeviceSpecificInformation);
                        SharedPreferenceManager.getmInstance(getApplicationContext()).login_user(true);

                        progressDialog.cancel();

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else{
                        showtoast(getString(R.string.InvalidUserId),R.drawable.error);
                        progressDialog.cancel();

                    }


                }
            }

            @Override
            public void onFailure(Call<ModelSuccessLogin> call, Throwable t) {
                showtoast(getString(R.string.no_internet),R.drawable.nointernet);

            }
        });



    }


    /**
     * Custom Toast Generator
     * @param text
     * @param image
     */
    private void showtoast(String text, int image) {

        LayoutInflater layoutInflater = getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_root));

        TextView toastText = layout.findViewById(R.id.customtoast);
        ImageView toastImage = layout.findViewById(R.id.toast_image);

        toastText.setText(text);
        Glide.with(getApplicationContext()).load(image).into(toastImage);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();


        if (SharedPreferenceManager.getmInstance(this).checklogin_user()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }

}