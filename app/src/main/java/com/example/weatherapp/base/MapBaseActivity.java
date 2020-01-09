package com.example.weatherapp.base;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.weatherapp.R;
import com.example.weatherapp.ui.main.MainActivity;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import static com.example.weatherapp.BuildConfig.MAPBOX_KEY;

public abstract class MapBaseActivity extends BaseActivity implements OnMapReadyCallback, MapboxMap.OnMapLongClickListener {

    protected MapView mapView;
    protected MapboxMap map;
    protected AlertDialog.Builder adBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Mapbox.getInstance(this, MAPBOX_KEY);
        super.onCreate(savedInstanceState);
        initView();
        initMap();
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_map_box;
    }

    protected void initMap() {
        mapView.getMapAsync(this);

    }

    protected void initView() {
        mapView = findViewById(R.id.map_view);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.MAPBOX_STREETS, style -> map = mapboxMap);
        mapboxMap.addOnMapLongClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public boolean onMapLongClick(@NonNull LatLng point) {
        adBuilder = new AlertDialog.Builder(MapBaseActivity.this);
        adBuilder.setTitle(R.string.set_Title);
        adBuilder.setMessage(R.string.set_Message);
        adBuilder.setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());
        adBuilder.setPositiveButton(R.string.yes, (dialog, which) -> {
            Intent intent = new Intent(MapBaseActivity.this, MainActivity.class);
            intent.putExtra("lat", point.getLatitude());
            intent.putExtra("lon", point.getLongitude());
            //     PreferenceHelper.saveState(point.getLatitude(), point.getLongitude());
            startActivity(intent);
        });
        adBuilder.create().show();
        return true;
    }

}
