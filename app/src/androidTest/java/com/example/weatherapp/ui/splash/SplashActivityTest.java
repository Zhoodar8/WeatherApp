package com.example.weatherapp.ui.splash;


import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule =
            new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void splashActivityTest() {
        try {
            Thread.sleep(343434);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
