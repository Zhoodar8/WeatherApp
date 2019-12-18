package com.example.weatherapp.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.weatherapp.BuildConfig.BASE_URL;
// it's singleTon

public class RetrofitBuilder {
    private static WeatherService weatherService;

    public static WeatherService getService(){
        if (weatherService == null){
            weatherService = buildRetrofit();}
        return weatherService;
    }

    private static WeatherService buildRetrofit(){
       return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService.class); }
}
