package com.justiceleague.locus2;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.drive.internal.StringListResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.JsonObject;
import com.justiceleague.locus2.Modules.DirectionFinder;
import com.justiceleague.locus2.Modules.DirectionFinderListener;
import com.justiceleague.locus2.Modules.Route;
import com.justiceleague.locus2.rest.ApiClient;
import com.justiceleague.locus2.rest.ApiInterface;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends GPSServiceActivity implements OnMapReadyCallback, DirectionFinderListener {

    //12.97572182, 77.60267008

    private GoogleMap mMap;
    int delay = 1000; // delay for 1 sec.
    int period = 5000; // repeat every 5 sec.
    Location location;
    public static List<Marker> CurrentMarker = new ArrayList<>();

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        signIn();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng l = new LatLng(12.999, 77.000);
//        mMap.addMarker(new MarkerOptions().position(l).title("Your Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l,13.0f));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
        vonMapReady();
        LatLng l1 = new LatLng(12.99243182, 77.61554008);
        LatLng l2 = new LatLng(12.98773182, 77.61367008);
        LatLng l3 = new LatLng(12.96461182, 77.58194008);
        AddMarker(l1, "Swapnil");
        AddMarker(l2, "Gaurav");
        AddMarker(l3, "Vishal");
        covert(l1,l2);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    public void signIn(){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                getGPSLocation();
                //update();
                //recieveLocation();
            }
        }, delay, period);
    }

    public void update(){
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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

    public void vonMapReady(){
        int delay = 1000; // delay for 1 sec.
        int period = 5000; // repeat every 5 sec.
        double latitude;
        double longitude;
        if (isLocationAvailable()) {
            Location location = getLocation();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            LatLng l = new LatLng(latitude,longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l,13.0f));
            AddMarker(l,"YourLocation");
            AddCircle(l);
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                getGPSLocation();
            }
        }, delay, period);
    }


    public void AddMarker(LatLng l, String name){
        if (name.equals("YourLocation")) {
            CurrentMarker.add(mMap.addMarker(new MarkerOptions().position(l).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.me))));
        }
        else {
            CurrentMarker.add(mMap.addMarker(new MarkerOptions().position(l).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.man))));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(l));
        }
    }

    public void AddCircle(LatLng l){
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(l)
                .radius(800)
                .strokeColor(Color.RED)
                .fillColor(Color.CYAN));
    }

    public void covert(LatLng s, LatLng d){
        String origin="";
        String desti="";
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> call= apiService.getTopRatedMovies("json",s.latitude,s.longitude,18,1);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d("Data" , response.body().toString());
                //String origin=response.body().toString();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Data" , "Error");
            }
        });
        //loc(origin, desti);
    }

    public void loc(String origin, String desti){
        try {
            new DirectionFinder(this, origin, desti).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.me))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.man))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
