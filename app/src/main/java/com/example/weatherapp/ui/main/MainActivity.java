package com.example.weatherapp.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.data.Entity.CurrentWeather;
import com.example.weatherapp.data.RetrofitBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView txtWeather, txtDATE,txtAirIndex,txtHumidity,txtSunriseTime,txtAirQuality,
    txtCityName,txtWindSpeed,txtCurrentWeather,txtWeatherToday,txtPressure,txtCloudiness,txtSunset;
    ImageView imgWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        fetchWeather();
        initListener();
    }
    public void initViews(){
        txtWeather =findViewById(R.id.txt_weather_now);
        txtDATE = findViewById(R.id.txt_date);
        imgWeather = findViewById(R.id.img_weather);
        txtCityName = findViewById(R.id.txt_city_name);
        txtCurrentWeather =findViewById(R.id.txt_current_weather);
        txtWindSpeed =findViewById(R.id.txt_wind_ms);
        txtSunriseTime =findViewById(R.id.txt_sunrise_time);
        txtHumidity = findViewById(R.id.txt_humidity_percent);
        txtWeatherToday = findViewById(R.id.txt_weather_today);
        txtPressure =findViewById(R.id.txt_pressure);
        txtCloudiness = findViewById(R.id.txt_cloudiness);
        txtSunset =findViewById(R.id.txt_sunset);
    }
    @SuppressLint("SimpleDateFormat")
    public void initListener(){
        txtDATE.setText(new SimpleDateFormat("dd MMMM yyyy").format(new Date()));
    }

    private void fetchWeather(){
        RetrofitBuilder.getService()
                .currentWeather("Bishkek",
                        getResources().getString(R.string.weather_key),"metric")
                        .enqueue(new Callback<CurrentWeather>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                                if (response.isSuccessful() && response.body() != null){
                                    txtWeather.setText(response.body().getMain().getTemp().toString()+"°");
                                    Glide.with(MainActivity.this).
                                            load("http://openweathermap.org/img/wn/"+response.body().
                                                    getWeather().get(0).
                                                    getIcon()+"@2x.png").
                                            into(imgWeather);
                                    txtCityName.setText( response.body().getName()+ " " +
                                            response.body().getSys().getCountry());
                                    txtCurrentWeather.setText(response.body().getWeather()
                                    .get(0).getDescription());
                                    txtWindSpeed.setText("SW "+response.body().
                                            getWind().getSpeed().toString()+ " m/s");
                                    txtSunriseTime.setText(
                                    new SimpleDateFormat("hh : mm", Locale.US).
                                            format(new Date(response.body().getSys().getSunrise()))
                                    );
                                    txtHumidity.setText(response.body().getMain()
                                            .getHumidity()
                                            .toString() +" %");
                                    txtWeatherToday.setText(response.body()
                                            .getMain()
                                            .getTempMax().toString()+"°");
                                    txtPressure.setText(response.body()
                                    .getMain().getPressure().toString() +"mb");
                                    txtCloudiness.setText(response.body()
                                    .getClouds().getAll().toString() + "%");
                                    txtSunset.setText(new SimpleDateFormat("hh : mm", Locale.US).
                                            format(new Date(response.body().getSys().getSunset())));
                                }
                            }
                            @Override
                            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();                            }
                        });
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
