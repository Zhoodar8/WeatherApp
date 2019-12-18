package com.example.weatherapp.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;

import butterknife.ButterKnife;

public abstract class BaseActivity  extends AppCompatActivity {

    protected abstract int getLayoutID();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        ButterKnife.bind(this);
    }

    protected void toast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }

    protected void replaceFragment(int containerID, Fragment fragment){
         getSupportFragmentManager()
                .beginTransaction()
                 .replace(containerID, fragment)
                 .commit();
    }
}

