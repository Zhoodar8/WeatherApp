package com.example.weatherapp.ui.main;

import com.example.weatherapp.Lifecycles;
import com.example.weatherapp.data.Entity.CurrentWeather;

public interface MainContract {

    interface  View{
        void setViews(CurrentWeather weather);
        void toast(String msg);

    }

    interface Presenter extends Lifecycles<View> {
        void getWeatherForCoord(double latitude, double longitude);

    }
}
