package com.example.weatherapp.data.Services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class ForegroundService extends Service {
    private FusedLocationProviderClient fusedLocationProvider;
    private LocationRequest locationRequest;
    private ArrayList<Double> location;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(1,NotificationHelper.createNotification(getApplicationContext(), "qwerty", "Zabuza"));
            callPermissions();
        return START_STICKY;
    }



    public  void requestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==
                        PermissionChecker.PERMISSION_GRANTED ){
            fusedLocationProvider = new FusedLocationProviderClient(this);
            locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setFastestInterval(5000);
            locationRequest.setInterval(10000);
            fusedLocationProvider.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
//                    location.add(locationResult.getLastLocation().getLatitude());
//                    location.add(locationResult.getLastLocation().getLongitude());

                    Log.e("------", "lat: " + locationResult.getLastLocation().getLatitude()+
                            "lon: " + locationResult.getLastLocation().getLongitude());

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
}
