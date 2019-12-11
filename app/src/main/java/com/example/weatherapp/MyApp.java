package com.example.weatherapp;

import android.app.Application;

import com.example.weatherapp.data.PreferenceHelper;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceHelper.init(this);
    }
}
