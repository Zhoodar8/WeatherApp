package com.example.weatherapp.data;

import com.example.weatherapp.data.Entity.CurrentWeather;
import com.example.weatherapp.data.Entity.ForecastEntity;
import com.mapbox.mapboxsdk.geometry.LatLng;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.weatherapp.data.ApiEnpoints.CURRENT_WEATHER;
import static com.example.weatherapp.data.ApiEnpoints.FORECAST_WEATHER;

public interface WeatherService {

    @GET(CURRENT_WEATHER)
    Call<CurrentWeather> currentWeather(@Query("lat") Double lat,
                                        @Query("lon") Double lon,
                                        @Query("appid") String key,
                                        @Query("units") String metric);

    @GET(FORECAST_WEATHER)
    Call<ForecastEntity> forecastWeather(@Query("lat") Double lat,
                                         @Query("lon") Double lon,
                                         @Query("appid") String key,
                                         @Query("units") String metric);

//    @GET(CURRENT_WEATHER)
//    Call<CurrentWeather> crntWeather(@Query("q") String city,
//                                      @Query("metric") String metric,
//                                      @Query("appid") String appID);

//    @GET(FORECAST_WEATHER)
//    Call<ForecastEntity> frcstWeather(@Query("q") String city,
//                                      @Query("metric") String metric,
//                                      @Query("appid") String appID);
}



