package com.logispark.parkingmanagementlogispark.utilites;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logispark.parkingmanagementlogispark.models.ModelDeviceSpecificInformation;
import com.logispark.parkingmanagementlogispark.models.ModelUser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


//This is used as local storage class to handle local storage.
public class SharedPreferenceManager {
    //Initializing and declaring variables
    private static final String Shared_Pref_Name = "new_shared_pref";
    private static SharedPreferenceManager mInstance;
    private static Context mcontext;
    public static final String KOT = "kot";


    //Getting a constructor.
    private SharedPreferenceManager(Context mcontext) {

        this.mcontext = mcontext;

    }

    public void save_deviceInformation(ModelDeviceSpecificInformation deviceInformation){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("branch",deviceInformation.getBranchName());
        editor.putString("uniqueId",deviceInformation.getUniqueId());
        editor.putString("lastUpdated",deviceInformation.getLastUpdated());
        editor.putBoolean("syncStatus",deviceInformation.isSysncStatus());
        editor.apply();
    }




    public void save_token_number(int tokenNumber){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("tokenNumber",tokenNumber);
        editor.apply();

    }

    public int get_token_number(){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("tokenNumber",0);
    }


    public ModelDeviceSpecificInformation getDeviceInformation() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        String branchName = sharedPreferences.getString("branch", null);
        String uniqueId = sharedPreferences.getString("uniqueId", null);
        String lastUpdated = sharedPreferences.getString("lastUpdated", null);
        String branchCode = sharedPreferences.getString("branchCode",null);
        boolean autoSyncStatus = sharedPreferences.getBoolean("syncStatus",false);

        return new ModelDeviceSpecificInformation(branchName, uniqueId,lastUpdated,branchCode,autoSyncStatus);
    }

    public void save_first_login(boolean first_login){

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstlogin",first_login);
        editor.apply();
    }

    public Boolean get_first_login(){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("firstlogin",true);
    }

    public void saveurl(String url) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("url", url);
        editor.apply();
    }

    public String geturl() {

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getString("url", null);

    }

    //Another Constructor
    public static synchronized SharedPreferenceManager getmInstance(Context mcontext) {
        if (mInstance == null) {
            mInstance = new SharedPreferenceManager(mcontext);
        }
        return mInstance;
    }


    //A method to save the user in shared preference
    public void saveuser(ModelUser user) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("id", user.getId());
        editor.putString("contact", user.getContact());
        editor.putString("uniqueDeviceId", user.getUniqueDeviceId());
        editor.putString("branch", user.getBranch());
        editor.putString("name", "Upaya");
        editor.putString("branchCode",user.getCode());

        editor.apply();
    }




    //method to save_login
    public void login_user(Boolean logged_in){

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("login_status",logged_in);
        editor.apply();


    }

    //method to check_login

    public Boolean checklogin_user(){
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name,Context.MODE_PRIVATE);
        return  sharedPreferences.getBoolean("login_status",false);
    }



    //method to check if the user is logged in or not.
    public boolean isLoggedIn() {

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

//    #method to retrive the data from shared preference.
    public ModelUser getUser() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        return new ModelUser(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("contact", null),
                sharedPreferences.getString("uniqueDeviceId", null),
                sharedPreferences.getString("branch", null),
                sharedPreferences.getString("name","default"),
                sharedPreferences.getString("branchCode",null)
        );


    }

    //method to clear the data in shared preference i.e log the user out.
    public void clear() {

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(Shared_Pref_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}

