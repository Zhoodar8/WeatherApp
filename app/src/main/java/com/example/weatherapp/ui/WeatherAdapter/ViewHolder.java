package com.example.weatherapp.ui.WeatherAdapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.R;
import com.example.weatherapp.utils.DateParser;

import java.text.ParseException;
import java.util.Date;

public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView txt_days;
    private TextView txt_days_weather_min;
    private TextView txt_days_weather_max;
    private ImageView img_Forecast;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        txt_days = itemView.findViewById(R.id.txt_days);
        txt_days_weather_min = itemView.findViewById(R.id.txt_days_weather_min);
        txt_days_weather_max = itemView.findViewById(R.id.txt_days_weather_max);
        img_Forecast = itemView.findViewById(R.id.img_forecast);
    }

    public void onBind(String dt, String max, String min,String img ) {
        try {
            txt_days.setText(DateParser.forCastDate(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txt_days_weather_min.setText(max);
        txt_days_weather_max.setText(min);
        Glide.with(itemView)
                .load("http://openweathermap.org/img/wn/" + img + "@2x.png")
                .into(img_Forecast);}
}
