package com.example.weatherapp.data.Services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.Objects;


public class ForegroundService extends Service {
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback mLocationCallback ;
    private  boolean isSuscces ;
    private ArrayList<String> loctn = new ArrayList<>();
    private Double lat;
    private Double lon;
    private ArrayList<Location> loc = new ArrayList<>();
    public static final String STARTFOREGROUND_ACTION = "STARTFOREGROUND_ACTION";
    public static final  String STOPFOREGROUND_ACTION ="STOPFOREGROUND_ACTION";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Objects.equals(intent.getAction(), STARTFOREGROUND_ACTION)) {
                Log.e("ololololol", "ddd "+ intent.getAction());
                // your start service code
                startForeground(1,NotificationHelper.createNotification(getApplicationContext(), "qwerty", "Zabuza"));
                callPermissions();
            }
            else if (Objects.equals(intent.getAction(), STOPFOREGROUND_ACTION)){
                Log.e("ololololol", "sss "+ intent.getAction());
                //your end servce code

                stopForeground(true);
                stopSelf();
                stopLocationUpdate();
            }
        }
        return START_STICKY;
    }

    public  void requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==
                        PermissionChecker.PERMISSION_GRANTED ){
            mFusedLocationProviderClient = new FusedLocationProviderClient(this);
            locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setFastestInterval(5000);
            locationRequest.setInterval(10000);
            mLocationCallback = new LocationCallback();
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest,new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    lat=locationResult.getLastLocation().getLatitude();
                    lon=locationResult.getLastLocation().getLongitude();
                    String latitude = String.valueOf(lat);
                    String longitude = String.valueOf(lon);
                    loctn.add(latitude);
                    loctn.add(longitude);
                    loc.add(locationResult.getLastLocation());

//                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude
//                            + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    Log.e("------", "lat: " + locationResult.getLastLocation().getLatitude()+
                            "lon: " + locationResult.getLastLocation().getLongitude());

                 //   Log.e("---------", "array "+ loctn.size());

                }
            }, getMainLooper());
        }else callPermissions();

    }
    public  void callPermissions(){
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this/*context*/,
                permissions, null/*rationale*/,
                null/*options*/,
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        // do your task.
                        requestLocationUpdates();
                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        super.onDenied(context, deniedPermissions);
                        callPermissions();
                    }
                });
    }

    public void stopLocationUpdate(){
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        isSuscces = false;
    }
}

