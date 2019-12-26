package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.weatherapp.base.MapBaseActivity;
import com.example.weatherapp.data.Location.FusedLocationProviderHelper;
import com.example.weatherapp.data.Services.ForegroundService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

import butterknife.BindView;

public class MapBoxActivity extends MapBaseActivity {
    @BindView(R.id.fab_btn)
    FloatingActionButton fab;
    private Boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFabListener();
    }

    public void initFabListener(){
            fab.setOnClickListener(v -> {
                if (!isActive) {
                    Intent intent = new Intent(this, ForegroundService.class);
                    startService(intent);
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop));
                    isActive = true;
                }else {
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    Intent intent = new Intent(this, ForegroundService.class);
                    stopService(intent);
                    isActive = false;
                }
            });
        }




}
