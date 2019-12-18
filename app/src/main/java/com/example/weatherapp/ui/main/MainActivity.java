package com.example.weatherapp.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.data.Entity.CurrentWeather;
import com.example.weatherapp.data.Entity.ForecastEntity;
import com.example.weatherapp.data.RetrofitBuilder;
import com.example.weatherapp.base.BaseActivity;
import com.example.weatherapp.ui.WeatherAdapter.ForecastAdapter;
import com.example.weatherapp.utils.DateParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weatherapp.BuildConfig.API_KEY;
import static com.example.weatherapp.utils.DateParser.getCityData;
import static com.example.weatherapp.utils.DateParser.getData;

public class MainActivity extends BaseActivity {

    @BindView(R.id.txt_weather_now)
    TextView txtWeather;
    @BindView(R.id.txt_date)
     TextView txtDATE;
    @BindView(R.id.txt_current_weather)
    TextView  txtCurrentWeather;
    @BindView(R.id.txt_humidity_percent)
    TextView  txtHumidity;
    @BindView(R.id.img_weather)
    ImageView  imgWeather;
    @BindView(R.id.txt_city_name)
    TextView  txtCityName;
    @BindView(R.id.txt_wind_ms)
    TextView  txtWindSpeed;
    @BindView(R.id.txt_sunrise_time)
    TextView  txtSunriseTime;
    @BindView(R.id.txt_weather_today)
    TextView  txtWeatherToday;
    @BindView(R.id.txt_pressure)
    TextView  txtPressure;
    @BindView(R.id.txt_cloudiness)
    TextView  txtCloudiness;
    @BindView(R.id.txt_sunset)
    TextView  txtSunset;
    @BindView(R.id.recycler_weather)
    RecyclerView mRecyclerView;
    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchWeather();
        fetchForecastWeather();
    }

    private void getForecast(List<CurrentWeather> list){
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(manager);
        ForecastAdapter adapter = new ForecastAdapter(list);
        mRecyclerView.setAdapter(adapter);
    }

    private void fetchWeather(){
        RetrofitBuilder.getService()
                .currentWeather("Bishkek",
                        API_KEY,"metric")
                        .enqueue(new Callback<CurrentWeather>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                                if (response.isSuccessful() && response.body() != null){
                                   fillViews(response);
                                }
                            }
                            @Override
                            public void onFailure(Call<CurrentWeather> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();                            }
                        });
    }

    private void fetchForecastWeather(){
        RetrofitBuilder.getService()
                .forecastWeather("Bishkek",
                        API_KEY,"metric")
                .enqueue(new Callback<ForecastEntity>() {
                    @Override
                    public void onResponse(Call<ForecastEntity> call, Response<ForecastEntity> response) {
                        if (response.isSuccessful() && response.body() !=null){
                              getForecast(response.body().getList());
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastEntity> call, Throwable t) {
                             toast(t.getLocalizedMessage());
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void fillViews(Response<CurrentWeather> response){
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
                getData(response
                                .body()
                                .getSys()
                                .getSunrise()));
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
        txtSunset.setText(getData(
                response.body().getSys().getSunset()));
        txtDATE.setText(getCityData());
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
