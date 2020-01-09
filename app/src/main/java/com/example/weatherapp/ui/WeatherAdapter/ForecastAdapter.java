package com.example.weatherapp.ui.WeatherAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.data.Entity.CurrentWeather;
import com.example.weatherapp.data.Entity.ForecastEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ForecastAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<CurrentWeather> list;
    public ForecastAdapter() {

    }

    public  void  updateWeather(List<CurrentWeather> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_main
        ,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position).getDt_txt(),
                list.get(position).getMain().getTempMax().toString()
                ,list.get(position).getMain().getTempMin().toString()
                ,list.get(position).getWeather().get(0).getIcon());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
