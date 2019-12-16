package com.example.weatherapp.ui.onboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.weatherapp.R;
import com.example.weatherapp.data.OnBoardData;
import com.example.weatherapp.ui.main.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
public class OnBoardActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Button button;
    private int postionScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        initList();
        initListener();
        setupViewPager();
    }
    public void initList() {
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        button = findViewById(R.id.btn_board);
        mViewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager, true);
    }
    public void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postionScreen == 3) {
                    MainActivity.start(OnBoardActivity.this);
                } else {
                    mViewPager.setCurrentItem(postionScreen + 1);
                }
            }
        });
    }
    private ArrayList<OnBoardData> getBoard() {
        ArrayList<OnBoardData> list = new ArrayList<>();
        list.add(new OnBoardData(getString(R.string.text1), R.drawable.pic1));
        list.add(new OnBoardData(getResources().getString(R.string.text2),R.drawable.pic2));
        list.add(new OnBoardData(getResources().getString(R.string.text3),R.drawable.pic3));
        list.add(new OnBoardData(getResources().getString(R.string.text4),R.drawable.pic4));
        return list;
    }
    public void setupViewPager() {
        PagerAdapter adapter = new ViewPagerAdapter(getBoard());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(vPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_onboard, menu);
        return true;
    }
    ViewPager.OnPageChangeListener vPager = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            postionScreen = position;
            switch (postionScreen) {
                case 0:
                case 1:
                case 2:
                    button.setText(R.string.txt_button);
                    break;
                case 3: button.setText(R.string.txt_button2);
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) { }
    };

    public void onClickSkip(MenuItem item) {
        MainActivity.start(this);
        finish();
    }
    public static void start(Context context) {
        context.startActivity(new Intent(context, OnBoardActivity.class)); }
}
