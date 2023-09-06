package com.logispark.parkingmanagementlogispark.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.logispark.parkingmanagementlogispark.Adapters.ParkingDataAdapter;
//import com.logispark.parkingmanagementlogispark.Adapters.SalesAdapter;
import com.logispark.parkingmanagementlogispark.R;
import com.logispark.parkingmanagementlogispark.models.ModelParkingData;
import com.logispark.parkingmanagementlogispark.utilites.DbHandler;

import java.util.List;

public class SearchToken extends AppCompatActivity {

    private RecyclerView recyclerViewSearchToken;
    private Button searchButton,buttonTryAgain;
    private String searchValue;
    private RelativeLayout relativeLayoutError;
    private ImageView imageViewError;
    private TextView textViewError;
    private DbHandler dbHandler;
    private List<ModelParkingData> modelParkingDataList;
    private ParkingDataAdapter parkingDataAdapter;
    private EditText editTextSearchValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_token);

        recyclerViewSearchToken = findViewById(R.id.recycleViewSearchData);
        searchButton = findViewById(R.id.buttonSearch);
        editTextSearchValue = findViewById(R.id.editTextSearch);

        relativeLayoutError = findViewById(R.id.errorrelativelayout);
        imageViewError = findViewById(R.id.imageviewerrors);
        textViewError = findViewById(R.id.textviewerror);
        buttonTryAgain = findViewById(R.id.buttonerror);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        relativeLayoutError.setVisibility(View.GONE);
        Glide.with(getApplicationContext()).load(R.drawable.errorimage).into(imageViewError);
        textViewError.setText(R.string.notoken);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInput();
            }
        });



    }

    private void confirmInput() {
        relativeLayoutError.setVisibility(View.GONE);

        if(!validateSearch()){
            return;
        }
        else{
            getData(searchValue);
        }
    }

    private void getData(String searchValue) {
        dbHandler = new DbHandler(getApplicationContext());
        modelParkingDataList = dbHandler.searchParkingDataFromVehicleNumber(searchValue);

        if(modelParkingDataList.size()<=0){
            relativeLayoutError.setVisibility(View.VISIBLE);
            buttonTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmInput();
                }
            });
        }
        else{
            parkingDataAdapter = new ParkingDataAdapter(getApplicationContext(),modelParkingDataList,SearchToken.this);
            recyclerViewSearchToken.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerViewSearchToken.setAdapter(parkingDataAdapter);

        }


    }

    private boolean validateSearch(){
        searchValue = editTextSearchValue.getText().toString().trim();
        if(searchValue.isEmpty()){
            editTextSearchValue.setError("Search Value Cannot Be Empty");
            return false;

        }
        else{
            return  true;
        }

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if(id == android.R.id.home){

            Intent intent = new Intent(SearchToken.this,QrActivity.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }
}