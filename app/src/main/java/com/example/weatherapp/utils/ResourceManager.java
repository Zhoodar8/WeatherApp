package com.example.weatherapp.utils;

import android.content.Context;

public class ResourceManager  {
    private Context context;

    public ResourceManager(Context context) {
        this.context=context;

    }

    public String getStringById(int id){
      return   context.getString(id);
    }
}
