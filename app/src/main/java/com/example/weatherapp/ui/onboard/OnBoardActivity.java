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
import com.example.weatherapp.ui.main.MainActivity;
import com.google.android.material.tabs.TabLayout;

public class OnBoardActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private String[] txt;
    private int[] imgID;
    private TabLayout tabLayout;
    private Button button;
    private int postionScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        initList();
        initListener();
        arrays();
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
                if (postionScreen == 3){

                    MainActivity.start(OnBoardActivity.this);
                }else { mViewPager.setCurrentItem(postionScreen +1);
                }
            }
        });

    }

    public void arrays() {
        txt = new String[]{getResources().getString(R.string.text1),
                getResources().getString(R.string.text2),
                getResources().getString(R.string.text3),
                getResources().getString(R.string.text4)};
        imgID = new int[]{R.drawable.hi, R.drawable.update, R.drawable.delete,
                R.drawable.thank_you};

    }

    public void setupViewPager() {
        PagerAdapter adapter = new ViewPagerAdapter(OnBoardActivity.this, txt, imgID);
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
                case 3:
                    button.setText(R.string.txt_button2);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skip:
                MainActivity.start(this);
                finish();
                break;
        }
        return true;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, OnBoardActivity.class));
    }
}
