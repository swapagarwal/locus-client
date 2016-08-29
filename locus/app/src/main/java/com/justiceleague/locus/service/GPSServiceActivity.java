package com.justiceleague.locus.service;

/**
 * Created by vishal.kumar1 on 19/07/16.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by chippada.bhargav on 19/07/16.
 */
public class GPSServiceActivity extends FragmentActivity {
    private static final String CLASSNAME = "GPSServiceActivity";
    private final static Logger LOGGER = Logger.getLogger(CLASSNAME);

    LocationManager locationManager;

    private static final int INITIAL_REQUEST = 1337;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;

    private boolean isLocationAvailable = false;
    private Location location; // location

    private GPSTracker gpsTracker;

    @Override
    protected void onResume() {
        super.onResume();
        setUpGPSService();
    }

    private void setUpGPSService() {
        if (locationManager == null) {
            locationManager = (LocationManager) getBaseContext().getSystemService(LOCATION_SERVICE);
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            LOGGER.log(Level.SEVERE, "Location permissions not granted!");
            isLocationAvailable = false;
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST);
            return;
        }

        // getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            LOGGER.log(Level.SEVERE, "Location settings not on!");
            isLocationAvailable = false;
            /*
            Toast.makeText(getApplicationContext(), "You need to switch on GPS", Toast.LENGTH_LONG);
            Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            onGPS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(onGPS);
            */
            showSettingsAlert();
            return;
        } else {
            LOGGER.info("GPSTracker created");
            gpsTracker = new GPSTracker(getBaseContext());
            isLocationAvailable = true;
        }
        return;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                break;
        }
    }

    protected boolean isLocationAvailable() {
        if (!isLocationAvailable) setUpGPSService();
        if (gpsTracker == null || gpsTracker.getLocation() == null) return false;
        return isLocationAvailable;
    }

    // Call this method ONLY after checking if location is available
    protected Location getLocation() {
        return gpsTracker.getLocation();
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                onGPS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(onGPS);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                isLocationAvailable = false;
            }
        });
//        alertDialog.show();
    }
}