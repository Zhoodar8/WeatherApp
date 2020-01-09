package com.example.weatherapp.map;

import android.content.Intent;
import android.os.Bundle;

import com.example.weatherapp.R;
import com.example.weatherapp.base.MapBaseActivity;
import com.example.weatherapp.data.Services.ForegroundService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;

public class MapBoxActivity extends MapBaseActivity {
    @BindView(R.id.fab_btn)
    FloatingActionButton fab;
    private Boolean isActive = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFabListener();
    }

    public void initFabListener() {
        fab.setOnClickListener(v -> {
            if (!isActive) {
                Intent Startintent = new Intent(MapBoxActivity.this, ForegroundService.class);
                Startintent.setAction(ForegroundService.STARTFOREGROUND_ACTION);
                startService(Startintent);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop));
                isActive = true;
            } else {
                Intent Stopintent = new Intent(MapBoxActivity.this, ForegroundService.class);
                Stopintent.setAction(ForegroundService.STOPFOREGROUND_ACTION);
                startService(Stopintent);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                isActive = false;
            }
        });

    }

}
