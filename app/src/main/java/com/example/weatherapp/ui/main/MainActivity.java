package com.example.weatherapp.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.map.MapBoxActivity;
import com.example.weatherapp.R;
import com.example.weatherapp.data.Entity.CurrentWeather;
import com.example.weatherapp.data.Entity.ForecastEntity;
import com.example.weatherapp.data.RetrofitBuilder;
import com.example.weatherapp.base.BaseActivity;
import com.example.weatherapp.ui.WeatherAdapter.ForecastAdapter;
import com.example.weatherapp.utils.ResourceManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weatherapp.BuildConfig.API_KEY;
import static com.example.weatherapp.utils.DateParser.getCityData;
import static com.example.weatherapp.utils.DateParser.getData;

public class MainActivity extends BaseActivity implements MainContract.View {
    @BindView(R.id.txt_weather_now)
    TextView txtWeather;
    @BindView(R.id.txt_date)
    TextView txtDATE;
    @BindView(R.id.txt_current_weather)
    TextView txtCurrentWeather;
    @BindView(R.id.txt_humidity_percent)
    TextView txtHumidity;
    @BindView(R.id.img_weather)
    ImageView imgWeather;
    @BindView(R.id.txt_city_name)
    TextView txtCityName;
    @BindView(R.id.txt_wind_ms)
    TextView txtWindSpeed;
    @BindView(R.id.txt_sunrise_time)
    TextView txtSunriseTime;
    @BindView(R.id.txt_weather_today)
    TextView txtWeatherToday;
    @BindView(R.id.txt_pressure)
    TextView txtPressure;
    @BindView(R.id.txt_cloudiness)
    TextView txtCloudiness;
    @BindView(R.id.txt_sunset)
    TextView txtSunset;
    @BindView(R.id.img_location)
    ImageView img_Location;
    @BindView(R.id.recycler_weather)
    RecyclerView mRecyclerView;
    private MainContract.Presenter mPresenter;
    //   private Double lat;
//    private Double lon;
//  private LatLng point;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(new ResourceManager(this));
        mPresenter.bind(this);
        initListener();
        callPermissions();
        getCurrentCoordinate();
        //  fetchForecastWeather();
        //  fetchWeather();
//        getLatLon();
    }
// intent take latlon
//    private void getLatLon() {
//            lat = getIntent().getDoubleExtra("lat", 0.0);
//            lon = getIntent().getDoubleExtra("lon", 0.0);
//            fetchWeather();
//            fetchForecastWeather();
//    }

    private void initListener() {
        img_Location.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MapBoxActivity.class)));
    }

    private void getForecast(List<CurrentWeather> list) {
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(manager);
        ForecastAdapter adapter = new ForecastAdapter();
        mRecyclerView.setAdapter(adapter);
        adapter.updateWeather(list);
    }

//    private void fetchWeather() {
//        RetrofitBuilder.getService()
//                .crntWeather("Bishkek",
//                        API_KEY, getString(R.string.metric))
//                .enqueue(new Callback<CurrentWeather>() {
//                    @SuppressLint("SetTextI18n")
//                    @Override
//                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
//                        if (response.isSuccessful() && response.body() != null) {
//                            setViews(response);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
//                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//    }

//    private void fetchForecastWeather() {
//        RetrofitBuilder.getService()
//                .frcstWeather("Bishkek,
//                        API_KEY, getString(R.string.metric))
//                .enqueue(new Callback<ForecastEntity>() {
//                    @Override
//                    public void onResponse(Call<ForecastEntity> call, Response<ForecastEntity> response) {
//                        if (response.isSuccessful() && response.body() != null) {
//                            getForecast(response.body().getList());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ForecastEntity> call, Throwable t) {
//                        toast(t.getLocalizedMessage());
//                    }
//                });
//    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setViews(CurrentWeather weather) {
        txtWeather.setText(getString(R.string.celsius_degree, weather.getMain().getTemp().toString()));
        Glide.with(MainActivity.this).
                load("http://openweathermap.org/img/wn/" + weather.
                        getWeather().get(0).
                        getIcon() + "@2x.png").
                into(imgWeather);
        txtCityName.setText(weather.getName() + " " +
                weather.getSys().getCountry());
        txtCurrentWeather.setText(weather.getWeather()
                .get(0).getDescription());
        txtWindSpeed.setText(getString(R.string.wind_speed, weather.
                getWind().getSpeed().toString()));
        txtSunriseTime.setText(
                getData(weather
                        .getSys()
                        .getSunrise()));
        txtHumidity.setText(getString(R.string.percent, weather.getMain()
                .getHumidity()
                .toString()));
        txtWeatherToday.setText(getString(R.string.celsius_degree, weather
                .getMain()
                .getTempMax().toString()));
        txtPressure.setText(getString(R.string.pressure_sign, weather
                .getMain().getPressure().toString()));
        txtCloudiness.setText(getString(R.string.percent, weather
                .getClouds().getAll().toString()));
        txtSunset.setText(getData(
                weather.getSys().getSunset()));
        txtDATE.setText(getCityData());
    }

    @Override
    public void toast(String msg) {

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    private void getWeatherForCoord(double lat, double lon) {
        RetrofitBuilder.getService()
                .currentWeather(lat, lon, getString(R.string.metric), API_KEY)
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            setViews(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getForeCastWeatherForCoord(double lat, double lon) {
        RetrofitBuilder.getService()
                .forecastWeather(lat, lon,API_KEY , getString(R.string.metric))
                .enqueue(new Callback<ForecastEntity>() {
                    @Override
                    public void onResponse(Call<ForecastEntity> call, Response<ForecastEntity> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            getForecast(response.body().getList());
                        }
                    }

                    @Override
                    public void onFailure(Call<ForecastEntity> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getCurrentCoordinate() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PermissionChecker.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PermissionChecker.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProvider = new FusedLocationProviderClient(this);
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            fusedLocationProvider.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Log.e("-------", "lat: " + locationResult.getLastLocation().getLatitude() +
                            " lon: " + locationResult.getLastLocation().getLongitude() + "  ");
                    double latitude = locationResult.getLastLocation().getLatitude();
                    double longitude = locationResult.getLastLocation().getLongitude();
                    mPresenter.getWeatherForCoord(latitude, longitude);
                  //  getWeatherForCoord(latitude, longitude);
                    getForeCastWeatherForCoord(latitude, longitude);
                }
            }, getMainLooper());
        }
    }

    public void callPermissions() {
        Permissions.check(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION}, getString(R.string.location),
                new Permissions.Options().setSettingsDialogTitle(getString(R.string.attention)).setRationaleDialogTitle(getString(R.string.location_access)),
                new PermissionHandler() {
                    @Override
                    public void onGranted() {
                        getCurrentCoordinate();
                    }

                    @Override
                    public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                        super.onDenied(context, deniedPermissions);
                        callPermissions();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unBind();
    }
}
