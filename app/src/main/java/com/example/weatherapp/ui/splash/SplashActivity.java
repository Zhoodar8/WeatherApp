package com.example.weatherapp.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

import com.example.weatherapp.R;
import com.example.weatherapp.data.PreferenceHelper;
import com.example.weatherapp.ui.main.MainActivity;
import com.example.weatherapp.ui.onboard.OnBoardActivity;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splaah);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                selectActivity();
            }
        }, 1_000);
        }

    private void selectActivity() {
        if (PreferenceHelper.getIsFirstLaunch()) {
            MainActivity.start(this);
        } else {
            PreferenceHelper.setIsFirstLaunch();
            OnBoardActivity.start(this);
        }
        finish();
    }
}
