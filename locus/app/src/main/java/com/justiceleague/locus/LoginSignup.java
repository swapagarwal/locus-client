package com.justiceleague.locus;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.justiceleague.locus.LoginActivity;
import com.justiceleague.locus.R;
import com.justiceleague.locus.SignUpActivity;
import com.justiceleague.locus.model.Loc;
import com.justiceleague.locus.rest.ApiClient;
import com.justiceleague.locus.rest.ApiInterface;
import com.justiceleague.locus.service.GPSServiceActivity;
import com.justiceleague.locus.service.GPSTracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginSignup extends GPSServiceActivity {

    int delay = 1000; // delay for 1 sec.
    int period = 5000; // repeat every 5 sec.
    private final String TAG ="GPS";
    TextView sender;
    TextView recieve;
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        sender=(TextView) findViewById(R.id.sender);
        recieve=(TextView)findViewById(R.id.reciever);
    }



    public void signIn(View view){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                getGPSLocation();
                sendLocation();
                //recieveLocation();
            }
        }, delay, period);
    }


    private void getGPSLocation() {
        //Log.d("GetGPSLocation Started" ,TAG);
        Map<String, String> jsonParams = new HashMap<String, String>();
        if (isLocationAvailable()) {
           // Log.d("Location Is available", TAG);
            location = getLocation();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            //Log.d(" " + latitude, TAG);
            //Log.d(" " + longitude, TAG);
        }
        else {
            //Log.d("gps not enabled",TAG);
            showSettingsAlert();
        }
        //Log.d("OnGetLocation Ended" ,TAG);
    }

    public void signUp(View view){
        Intent intent=new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void sendLocation(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call= apiService.send(3, location.getLatitude(), location.getLongitude());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("Send", response.raw().toString());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void recieveLocation(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Loc> call= apiService.recieve(3);
        call.enqueue(new Callback<Loc>() {
            @Override
            public void onResponse(Call<Loc> call, Response<Loc> response) {
                Log.d("Data", "" +  response.body().getLat() );
            }

            @Override
            public void onFailure(Call<Loc> call, Throwable t) {

            }
        });
    }
}
