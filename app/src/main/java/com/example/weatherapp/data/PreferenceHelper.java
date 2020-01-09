package com.example.weatherapp.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.mapbox.mapboxsdk.geometry.LatLng;

import static android.content.Context.MODE_PRIVATE;


public class PreferenceHelper {

    private static SharedPreferences preferences;
    private static final String IS_FIRST_LAUNCH = "isFirstLaunch";
    private static final String NAME_PREFS = "NAME_PREFS";
    private static  final String LAT = "lat";
    private static  final String LON = "lon";


    public static void init(Context context) {
        preferences = context.getSharedPreferences(NAME_PREFS, MODE_PRIVATE);
    }

    public static void setIsFirstLaunch() {
        preferences.edit().putBoolean(IS_FIRST_LAUNCH, true).apply();
    }

    public static boolean getIsFirstLaunch() {

        return preferences.getBoolean(IS_FIRST_LAUNCH, false);
    }

    public static void saveState(Double lat, Double lon){
        preferences.edit().putLong(LAT,Double.doubleToLongBits(lat)).apply();
        preferences.edit().putLong(LON,Double.doubleToLongBits(lon)).apply();
    }
    public static LatLng getState(){
        double lat = Double.longBitsToDouble(preferences.getLong(LAT, 0));
        double lon = Double.longBitsToDouble(preferences.getLong(LON, 0));
        return new LatLng(lat, lon);
    }

    public static void clear(){
      preferences.edit().clear().apply();
    }
}













