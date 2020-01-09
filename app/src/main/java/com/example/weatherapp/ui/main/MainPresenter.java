package com.example.weatherapp.ui.main;

import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.data.Entity.CurrentWeather;
import com.example.weatherapp.data.RetrofitBuilder;
import com.example.weatherapp.utils.ResourceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.weatherapp.BuildConfig.API_KEY;
import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private ResourceManager manager;


    public MainPresenter(ResourceManager manager) {
        this.manager = manager;
    }

    @Override
    public void bind(MainContract.View view) {
        this.mView = view;

    }

    private boolean isViewAttached() {
        return mView != null;
    }

    @Override
    public void unBind() {
        mView = null;

    }

    @Override
    public void getWeatherForCoord(double latitude, double longitude) {
        RetrofitBuilder.getService()
                .currentWeather(latitude, longitude, API_KEY, manager.getStringById(R.string.metric))
                .enqueue(new Callback<CurrentWeather>() {
                    @Override
                    public void onResponse(Call<CurrentWeather> call, Response<CurrentWeather> response) {
                        if (isViewAttached()) mView.setViews(response.body());

                    }

                    @Override
                    public void onFailure(Call<CurrentWeather> call, Throwable t) {
                        if (isViewAttached()) mView.toast(t.getLocalizedMessage());
                    }
                });
    }
}
